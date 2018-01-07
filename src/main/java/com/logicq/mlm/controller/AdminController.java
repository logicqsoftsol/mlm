package com.logicq.mlm.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logicq.mlm.common.factory.LoginFactory;
import com.logicq.mlm.common.helper.PropertyHelper;
import com.logicq.mlm.common.helper.TransactionHelper;
import com.logicq.mlm.common.helper.WalletAmountCalculator;
import com.logicq.mlm.model.admin.FeeSetup;
import com.logicq.mlm.model.admin.NetWorkTask;
import com.logicq.mlm.model.admin.TaskDetails;
import com.logicq.mlm.model.admin.TransactionDetails;
import com.logicq.mlm.model.login.Login;
import com.logicq.mlm.model.profile.NetWorkDetails;
import com.logicq.mlm.model.profile.NetworkInfo;
import com.logicq.mlm.model.profile.UserProfile;
import com.logicq.mlm.model.profile.WalletDetails;
import com.logicq.mlm.model.wallet.WalletStatement;
import com.logicq.mlm.model.workflow.WorkFlow;
import com.logicq.mlm.service.networkdetails.INetworkDetailsService;
import com.logicq.mlm.service.networkdetails.INetworkTaskService;
import com.logicq.mlm.service.user.IUserService;
import com.logicq.mlm.service.wallet.IFeeSetupService;
import com.logicq.mlm.service.wallet.ITransactionDetailsService;
import com.logicq.mlm.service.wallet.IWalletDetailsService;
import com.logicq.mlm.service.wallet.IWalletStmntService;
import com.logicq.mlm.service.workflow.IWorkFlowService;
import com.logicq.mlm.vo.EncashVO;
import com.logicq.mlm.vo.LoginVO;
import com.logicq.mlm.vo.PaymentVO;
import com.logicq.mlm.vo.StatusVO;
import com.logicq.mlm.vo.TxnRollBackVO;
import com.logicq.mlm.vo.UserDetailsVO;
import com.logicq.mlm.vo.WalletStmntVO;

@RestController
@RequestMapping("api/admin")
public class AdminController {

	@Autowired
	IWorkFlowService workflowservice;

	@Autowired
	IWalletStmntService walletservice;

	@Autowired
	INetworkDetailsService netWorkDetailsService;
	
	@Autowired
	INetworkTaskService networktaskservice;
	
	@Autowired
	IUserService userservice;
	
	@Autowired
	IWalletDetailsService walletDetailsService;
	
	@Autowired
	IFeeSetupService feeSetupService;

	@Autowired
	ITransactionDetailsService transactionDetailsService;
	
	@Autowired
	TransactionHelper transactionHelper;
	
	ObjectMapper objectmapper = new ObjectMapper();

	@RequestMapping(value = "/updateAdminTask", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WalletStmntVO> updateAdminTaskDetails(@RequestBody TaskDetails taskdetails) throws Exception {
		WalletStmntVO walletStmntVO = null;
		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof LoginVO) {
				WorkFlow workflow = workflowservice.getWorkFlowAccordingToWorkId(taskdetails.getTaskid());
				workflow.setStatus(Boolean.TRUE);
				workflow.setMessage(workflow.getWorktype()+" done sucessfully");
				workflowservice.updateWorkFlow(workflow);				
				if ("ENCASH_REQUEST".equals(workflow.getWorktype()) && workflow.getStatus()) {
					EncashVO encashvo = taskdetails.getEncashvo();//objectmapper.convertValue(new String(workflow.getWorktype()), EncashVO.class);
					encashvo.setRefrencenumber(taskdetails.getEncashvo().getRefrencenumber());
					encashvo.setEncashtype(taskdetails.getEncashvo().getEncashtype());
					// encashvo.setEncashamount(taskdetails.getEncashvo().getEncashamount());
					workflow.setWorkparameter(objectmapper.writeValueAsBytes(encashvo));
					List<WorkFlow> workflowlist = workflowservice
							.getWorkFlowForUserAccordingToWorkType("ENCASH_REQUEST", workflow.getAssignedto());
					if (!workflowlist.isEmpty()) {
						UserProfile userprofile = userservice
								.fetchUserAccordingToProfileId(Long.valueOf(workflow.getProfileid()));
						if (userprofile.getWalletdetails().isIsactive()) {
							WalletStatement walletstmnt= walletservice.fetchWalletStmnt(userprofile.getWalletdetails().getWalletid());
							walletstmnt = WalletAmountCalculator.calculateCurrentBalanceAfterEncashed(walletstmnt,
									taskdetails.getEncashvo().getEncashamount());
							walletservice.updateWalletStmnt(walletstmnt);
							
							TransactionDetails txnDetails = WalletAmountCalculator.populateTransactionDetails(
									encashvo, userprofile.getWalletdetails().getWalletid(), userprofile.getFirstname(),
									userprofile.getLastname());
							txnDetails.setTxntype("ENCASH");
							transactionDetailsService.save(txnDetails);
							walletStmntVO=WalletAmountCalculator.populateWalletStmnt(walletstmnt);
						}
						WorkFlow userworkflow = workflowlist.get(0);
						userworkflow.setStatus(Boolean.TRUE);
						userworkflow.setMessage("Encash Done by " + taskdetails.getEncashvo().getEncashtype()
								+ "with  ref no " + taskdetails.getEncashvo().getRefrencenumber());
						workflowservice.updateWorkFlow(userworkflow);
					}
				}
				if ("ADMIN_VERIFICATION".equals(workflow.getWorktype()) && workflow.getStatus()) {
					// need to add this logic for each parent id
					UserProfile userprofile = userservice
							.fetchUserAccordingToProfileId(Long.valueOf(workflow.getProfileid()));
					userprofile.getWalletdetails().setIsactive(Boolean.TRUE);
					userprofile.getWalletdetails().setWalletactivedate(new Date());
					walletDetailsService.updateWalletDetails(userprofile.getWalletdetails());
					
					NetWorkDetails networkinfo = objectmapper
							.readValue(userprofile.getNetworkinfo().getNetworkjson(), NetWorkDetails.class);
					NetworkInfo parentNetworkInfo = netWorkDetailsService
							.getNetworkDetails(userprofile.getNetworkinfo().getParentmemberid());
					
					NetWorkDetails parentNetworkdetails = PropertyHelper.convertJsonToNetworkInfo(parentNetworkInfo);
					networkinfo.setCategory("LEVEL1");
					networkinfo.setTitle("LEVEL1");
					if (null != parentNetworkdetails && null != parentNetworkdetails.getChildren()) {
						parentNetworkdetails.getChildren().add(networkinfo);
					}else{
						parentNetworkdetails.setChildren(new ArrayList<NetWorkDetails>());
						parentNetworkdetails.getChildren().add(networkinfo);
					}
					
					parentNetworkInfo.setNetworkjson(PropertyHelper.convertNetworkInfoToJson(parentNetworkdetails).getBytes());
					netWorkDetailsService.updateNetworkDetails(parentNetworkInfo);
		

					//Create Network Task
					NetWorkTask networktask=new NetWorkTask();
					networktask.setMemberid(userprofile.getNetworkinfo().getMemberid());
					networktask.setParentid(userprofile.getNetworkinfo().getParentmemberid());
					networktask.setTaskcreationdate(new Date());
					networktask.setTaskStatus(Boolean.FALSE);
					networktaskservice.createNetworkTask(networktask);
				}
				if ("ADD_MONEY_TO_WALLET".equals(workflow.getWorktype()) && workflow.getStatus()) {
					PaymentVO payemtdetails = objectmapper.readValue(workflow.getWorkparameter(), PaymentVO.class);
					
					UserProfile debituserprofile = userservice.fetchUserAccordingToUserName("ADMIN");
					WalletDetails debitwalletDetails = debituserprofile.getWalletdetails();
					WalletStatement debitwalletStatment = walletservice
							.fetchWalletStmnt(debitwalletDetails.getWalletid());
				
					UserProfile userprofile = userservice.fetchUserAccordingToUserName(payemtdetails.getUsername());
					WalletDetails walletDetails = userprofile.getWalletdetails();
					WalletStatement walletStatment = walletservice
							.fetchWalletStmnt(walletDetails.getWalletid());
					
					TransactionDetails debittxnDetails = WalletAmountCalculator.populateTransactionDetails(
							payemtdetails, debitwalletDetails.getWalletid(), userprofile.getFirstname(),
							userprofile.getLastname());
					debittxnDetails.setTxntype("SEND");
					
					TransactionDetails txnDetails = WalletAmountCalculator.populateTransactionDetails(
							payemtdetails, walletDetails.getWalletid(), debituserprofile.getFirstname(),
							debituserprofile.getLastname());
					txnDetails.setTxntype("ADD");
					
					transactionHelper.debitFromWallet(debitwalletStatment, payemtdetails.getAmount(),debittxnDetails);	
					transactionHelper.creditToWallet(walletStatment, payemtdetails.getAmount(), txnDetails);
				}
				

			}
		}
		return new ResponseEntity<WalletStmntVO>(walletStmntVO, HttpStatus.OK);
	}

	@RequestMapping(value = "/getTaskListDetails", method = RequestMethod.GET)
	public ResponseEntity<UserDetailsVO> getTaskListDetails() throws Exception {
		UserDetailsVO userdetailsvo = new UserDetailsVO();
		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof LoginVO) {
				LoginVO login = (LoginVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				UserProfile userdetails = new UserProfile();
				userdetails.setLogindetails(LoginFactory.createLoginDetails(login));
				String authorityname = null;
				List<GrantedAuthority> authorities = (List<GrantedAuthority>) login.getAuthorities();
				if (null != authorities && !authorities.isEmpty()) {
					authorityname = authorities.get(0).getAuthority();
				}
				if (authorityname.equals("ADMIN")) {
					List<WorkFlow> workflowlist = workflowservice.getPendingWorkFlowForAdmin(authorityname);
					List<TaskDetails> tasklist = new ArrayList<TaskDetails>();
					for (WorkFlow workflow : workflowlist) {
						TaskDetails task = new TaskDetails();
						task.setPriority("HIGH");
						task.setTaskassigneddate(workflow.getCreatetime());
						task.setTaskfor(workflow.getCreatedby());
						task.setTaskname(workflow.getWorktype());
						task.setMessage(workflow.getMessage());
						if (!workflow.getStatus()) {
							task.setTaskstatus("Pending");
						}
						task.setTasktype(workflow.getWorktype());
						task.setTaskid(workflow.getWorkid());
						tasklist.add(task);
					}
					userdetailsvo.setTasklist(tasklist);
				}
			}
		}
		return new ResponseEntity<UserDetailsVO>(userdetailsvo, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/addMoneyToWallet", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WalletStmntVO> addMoneyToWallet(@RequestBody PaymentVO paymentDetails) throws Exception {
		WalletStmntVO walletStatmentVO =null ;
		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof LoginVO) {
				LoginVO login = (LoginVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (null != login) {
					if (null != paymentDetails.getAmount()) {
						int comparevalue = paymentDetails.getAmount().compareTo(BigDecimal.ZERO);
						if (comparevalue == 1) {
							UserProfile userprofile = userservice.fetchUserAccordingToUserName(login.getUsername());
							if (!"ADMIN".equals(login.getUsername())) {
								paymentDetails.setUsername(login.getUsername());
								WorkFlow addMoneytowallet = new WorkFlow();
								addMoneytowallet.setAssignedto("ADMIN");
								addMoneytowallet.setMessage(login.getUsername()+" request to add INR "+paymentDetails.getAmount()+" money to wallet ");
								addMoneytowallet.setCreatedby(login.getUsername());
								addMoneytowallet.setCreatetime(new Date());
								addMoneytowallet.setWorktype("ADD_MONEY_TO_WALLET");
								addMoneytowallet.setStatus(false);
								addMoneytowallet.setWorkparameter(objectmapper.writeValueAsBytes( paymentDetails));
								addMoneytowallet.setProfileid(String.valueOf(userprofile.getId()));
								workflowservice.createWorkFlowForValidation(addMoneytowallet);
							} else {
								WalletDetails walletDetails = userprofile.getWalletdetails();
								WalletStatement walletStatment = walletservice
										.fetchWalletStmnt(walletDetails.getWalletid());
								TransactionDetails txnDetails = WalletAmountCalculator.populateTransactionDetails(
										paymentDetails, walletDetails.getWalletid(), login.getFirstname(),
										login.getLastname());
								txnDetails.setTxntype("ADD");
								transactionHelper.creditToWallet(walletStatment, paymentDetails.getAmount(), txnDetails);
								walletStatmentVO = WalletAmountCalculator.populateWalletStmnt(walletStatment);
							}
						}
					}
				}

			}
		}
		return new ResponseEntity<WalletStmntVO>(walletStatmentVO, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/sendMoney", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WalletStmntVO> sendMoney(@RequestBody PaymentVO paymentDetails) throws Exception {
		WalletStmntVO walletStatmentVO = null;
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof LoginVO) {
			LoginVO login = (LoginVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (null != login && null != paymentDetails) {
				int comparevalue = paymentDetails.getAmount().compareTo(BigDecimal.ZERO);
				if (comparevalue==1) {
					UserProfile debituserprofile = userservice.fetchUserAccordingToUserName(login.getUsername());
					WalletDetails debitwalletDetails = debituserprofile.getWalletdetails();
					WalletStatement debitwalletStatment = walletservice
							.fetchWalletStmnt(debitwalletDetails.getWalletid());
					WalletStatement creditwalletStatment = walletservice
							.fetchWalletStmntFromWalletNumber(paymentDetails.getPayewalletnumber());
					UserProfile credituserprofile =creditwalletStatment.getWallet().getUserprofile();
					
					TransactionDetails debittxnDetails = WalletAmountCalculator.populateTransactionDetails(
							paymentDetails, debitwalletDetails.getWalletid(), credituserprofile.getFirstname(),
							credituserprofile.getLastname());
					debittxnDetails.setTxntype("SEND");
					transactionHelper.debitFromWallet(debitwalletStatment, paymentDetails.getAmount(), debittxnDetails);     
					
					TransactionDetails credittxnDetails = WalletAmountCalculator.populateTransactionDetails(
							paymentDetails, creditwalletStatment.getWalletid(), debituserprofile.getFirstname(),
							debituserprofile.getLastname());
					credittxnDetails.setTxntype("RECIVED");
					transactionHelper.creditToWallet(creditwalletStatment, paymentDetails.getAmount(), credittxnDetails);
					walletStatmentVO=WalletAmountCalculator.populateWalletStmnt(debitwalletStatment);
				}
			}
		}
		return new ResponseEntity<WalletStmntVO>(walletStatmentVO, HttpStatus.OK);

	}
	
	
	@RequestMapping(value = "/reloadWalletDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WalletStmntVO> reloadWallet() throws Exception {
		WalletStmntVO walletStatmentVO = null;
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof LoginVO) {
			LoginVO login = (LoginVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserProfile userprofile = userservice.fetchUserAccordingToUserName(login.getUsername());
			WalletDetails walletDetails = userprofile.getWalletdetails();
			WalletStatement walletStatment = walletservice.fetchWalletStmnt(walletDetails.getWalletid());
			walletStatmentVO=WalletAmountCalculator.populateWalletStmnt(walletStatment);
		}
		return new ResponseEntity<WalletStmntVO>(walletStatmentVO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getTransactionDetailsForTxnType/{txntype}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TransactionDetails>> getTransactionDetailsAccordingToType(@PathVariable String txntype) throws Exception {
		List<TransactionDetails> transactionDetails=new ArrayList<>();
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof LoginVO) {
			LoginVO login = (LoginVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		transactionDetails = transactionDetailsService
					.getTransactionDetails(login.getUsername(),txntype);
		}
		return new ResponseEntity<List<TransactionDetails>>(transactionDetails, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getTransactionDetails/{txnrefnumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TxnRollBackVO> getTransactionDetails(@PathVariable String txnrefnumber) throws Exception {
		TxnRollBackVO transactionDetailsVO = new TxnRollBackVO();
		//System.out.println(txnrefnumber);
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof LoginVO) {
			LoginVO login = (LoginVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (StringUtils.isEmpty(txnrefnumber) || "null".equals(txnrefnumber) || "undefined".equals(txnrefnumber)) {
				List<TransactionDetails> transactionDetails = transactionDetailsService
						.getTransactionDetails(login.getUsername());
				transactionDetailsVO.setTransactionDetails(transactionDetails);
			} else {
				if ("ADMIN".equals(login.getUsername())) {
					List<TransactionDetails> transactionlist = transactionDetailsService
							.getTransactionDetailsAccordingToRefrenceNumber(txnrefnumber);
					for (TransactionDetails txnDetails : transactionlist) {
						if (txnDetails.getTxntype().equals("RECIVED")) {
							WalletDetails walletDetails = walletDetailsService.getWalletDetails(txnDetails.getWalletid());
							UserProfile userProfile = walletDetails.getUserprofile();
							WalletStatement payeewalletStatment = walletservice
									.fetchWalletStmnt(txnDetails.getWalletid());
							transactionDetailsVO.setPayeeCurrentBalance(payeewalletStatment.getCurrentbalance());
							transactionDetailsVO.setPayeeFirstName(userProfile.getFirstname());
							transactionDetailsVO.setPayeeLastName(userProfile.getLastname());
							transactionDetailsVO.setPayeeGpmIdNo(walletDetails.getWalletnumber());
							transactionDetailsVO.setRollbackAmount(txnDetails.getAmount());
							transactionDetailsVO.setTxnDate(txnDetails.getTxndate());
							transactionDetailsVO.setTxnRefrenceNumber(txnDetails.getRefrenceno());
							transactionDetailsVO.setTxnDescription(txnDetails.getDescription());
						}
						if (txnDetails.getTxntype().equals("SEND")) {
							WalletDetails	walletDetails = walletDetailsService.getWalletDetails(txnDetails.getWalletid());
							UserProfile userProfile = walletDetails.getUserprofile();
							transactionDetailsVO.setCrediotrLastName(userProfile.getLastname());
							transactionDetailsVO.setCreditorFirstName(userProfile.getFirstname());
							transactionDetailsVO.setCreditorGpmIdNo(walletDetails.getWalletnumber());

						}
					}
				}
			}
		}
		return new ResponseEntity<TxnRollBackVO>(transactionDetailsVO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/rollBackTransactionDetails/{txnRefrenceNumber}/{reasone}",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WalletStmntVO> rollBackTransaction(@PathVariable String txnRefrenceNumber,@PathVariable String reasone) throws Exception {
		WalletStmntVO walletStment=null;
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof LoginVO) {
			LoginVO login = (LoginVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();			
		TxnRollBackVO txnRollback=new TxnRollBackVO();
		txnRollback.setTxnRefrenceNumber(txnRefrenceNumber);
		txnRollback.setRollbackReasone(reasone);
			if ("ADMIN".equals(login.getUsername())) {
				List<TransactionDetails> transactionlist = transactionDetailsService
						.getTransactionDetailsAccordingToRefrenceNumber(txnRollback.getTxnRefrenceNumber());
				 WalletStatement debitwalletStatment=null;
				 WalletStatement creditwalletStatment=null;
				 UserProfile debituserprofile=null;
				 UserProfile credituserprofile=null;
				for (TransactionDetails txnDetails : transactionlist) {
					WalletDetails walletDetails = walletDetailsService.getWalletDetails(txnDetails.getWalletid());
					if (txnDetails.getTxntype().equals("RECIVED")) {
						debituserprofile = walletDetails.getUserprofile();
						debitwalletStatment = walletservice.fetchWalletStmnt(txnDetails.getWalletid());
					
					}
					if (txnDetails.getTxntype().equals("SEND")) {
						credituserprofile = walletDetails.getUserprofile();
						creditwalletStatment = walletservice.fetchWalletStmnt(txnDetails.getWalletid());
						
					}
					txnRollback.setRollbackAmount(txnDetails.getAmount());
				}
				
				if(null!=creditwalletStatment && null!=debitwalletStatment){
				TransactionDetails debittxnDetails = WalletAmountCalculator.populateTransactionDetails(
						txnRollback, debitwalletStatment.getWalletid(), credituserprofile.getFirstname(),
						credituserprofile.getLastname());
				debittxnDetails.setTxntype("ROLLBACK_DEBIT");
				transactionHelper.debitFromWallet(debitwalletStatment, debittxnDetails.getAmount(),
						debittxnDetails);
				
				TransactionDetails credittxnDetails = WalletAmountCalculator.populateTransactionDetails(
						txnRollback, debitwalletStatment.getWalletid(), debituserprofile.getFirstname(),
						debituserprofile.getLastname());
				credittxnDetails.setTxntype("ROLLBACK_CREDIT");
				transactionHelper.creditToWallet(creditwalletStatment, credittxnDetails.getAmount(),
						credittxnDetails);
				transactionHelper.deleteTransactionList(transactionlist);
				}
			}

		}
		return new ResponseEntity<WalletStmntVO>(walletStment, HttpStatus.OK);
	}
	
}
