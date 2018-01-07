package com.logicq.mlm.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logicq.mlm.common.factory.LoginFactory;
import com.logicq.mlm.common.helper.PropertyHelper;
import com.logicq.mlm.common.helper.WalletAmountCalculator;
import com.logicq.mlm.common.helper.sms.MessageHelper;
import com.logicq.mlm.common.helper.sms.SMSHelper;
import com.logicq.mlm.common.vendor.sms.SMSVendor;
import com.logicq.mlm.model.admin.TransactionDetails;
import com.logicq.mlm.model.login.Login;
import com.logicq.mlm.model.message.EmailDetails;
import com.logicq.mlm.model.performance.UserPerformance;
import com.logicq.mlm.model.profile.NetWorkDetails;
import com.logicq.mlm.model.profile.NetworkInfo;
import com.logicq.mlm.model.profile.UserDocument;
import com.logicq.mlm.model.profile.UserProfile;
import com.logicq.mlm.model.profile.WalletDetails;
import com.logicq.mlm.model.sms.SMSDetails;
import com.logicq.mlm.model.wallet.WalletStatement;
import com.logicq.mlm.model.workflow.WorkFlow;
import com.logicq.mlm.service.login.ILoginService;
import com.logicq.mlm.service.messaging.IEmailService;
import com.logicq.mlm.service.networkdetails.INetworkDetailsService;
import com.logicq.mlm.service.performance.IUserNetworkPerformanceService;
import com.logicq.mlm.service.performance.IUserPerformanceService;
import com.logicq.mlm.service.user.IDocumentUploadService;
import com.logicq.mlm.service.user.IUserService;
import com.logicq.mlm.service.wallet.ITransactionDetailsService;
import com.logicq.mlm.service.wallet.IWalletDetailsService;
import com.logicq.mlm.service.wallet.IWalletStmntService;
import com.logicq.mlm.service.workflow.IWorkFlowService;
import com.logicq.mlm.vo.EncashVO;
import com.logicq.mlm.vo.LoginVO;
import com.logicq.mlm.vo.NetworkCountVO;
import com.logicq.mlm.vo.PasswordVO;
import com.logicq.mlm.vo.StatusVO;
import com.logicq.mlm.vo.TxnRollBackVO;
import com.logicq.mlm.vo.UserDetailsVO;
import com.logicq.mlm.vo.WalletStmntVO;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	IUserService userservice;

	@Autowired
	IWalletStmntService walletStatementservice;

	@Autowired
	IUserPerformanceService userperformanceservice;

	@Autowired
	IWorkFlowService workflowservice;

	@Autowired
	IEmailService emailservice;

	@Autowired
	INetworkDetailsService networkservice;

	@Autowired
	IDocumentUploadService documentUploadService;

	@Autowired
	ILoginService loginService;

	@Autowired
	ServletContext context;
	
	@Autowired
	IWalletDetailsService walletDetailsService;
	
	@Autowired
	IWalletStmntService walletservice;


	@Autowired
	ITransactionDetailsService transactionDetailsService;
	
	@Autowired
	IUserNetworkPerformanceService userNetworkPerformance;

	private final static SMSVendor smsvendor = SMSVendor.getInstance();
	ObjectMapper objectmapper = new ObjectMapper();

	@RequestMapping(value = "/getUserBasicDetails/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDetailsVO> fetchUserDetails(@PathVariable String username) throws Exception {
		UserDetailsVO userdetails = new UserDetailsVO();
		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			UserProfile	userprofile = new UserProfile();
			userprofile.setLogindetails(new Login());
			userprofile.getLogindetails().setUsername(username);
			userprofile = userservice.fetchUser(userprofile);
			userdetails.setUserprofile(userprofile);
			UserDocument userdoc=documentUploadService.getDocumentsAccordingToUserName(username);
			userdetails.setDocument(userdoc);
		}
		return new ResponseEntity<UserDetailsVO>(userdetails, HttpStatus.OK);
	}

	@RequestMapping(value = "/saveUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StatusVO> saveUserDetails(@RequestBody UserDetailsVO userdetailvo) throws Exception {
		StatusVO statusVO = new StatusVO();
		if (null != userdetailvo.getUserprofile()) {
			// update newtork json
			userdetailvo.getUserprofile().getNetworkinfo().setIsUpdate(Boolean.FALSE);
			//This is hard code as user add under his level
			userdetailvo.getNetworkjson().setTitle("LEVEL0");
			userdetailvo.getNetworkjson().setCategory("LEVEL0");
			userdetailvo.getNetworkjson().setChildren(null);
			String networkJson = objectmapper.writeValueAsString(userdetailvo.getNetworkjson());
			userdetailvo.getUserprofile().getNetworkinfo().setNetworkjson(networkJson.getBytes());
			// save user details
			userservice.saveUser(userdetailvo.getUserprofile());
			if (null != userdetailvo.getDocument()) {
				documentUploadService.updateDocumentDetails(userdetailvo.getDocument().getDocumentID(),
						userdetailvo.getUserprofile());
			} else {
				UserDocument userDocument = new UserDocument();
				userDocument.setMobileNumber(userdetailvo.getUserprofile().getLogindetails().getMobilenumber());
				userDocument.setUsername(userdetailvo.getUserprofile().getLogindetails().getUsername());
				userDocument.setProfileID(userdetailvo.getUserprofile().getId());
				userDocument.setServiceType("profile");
				userDocument.setName("dummyuser.jpg");
				userDocument.setUploadDate(new Date());
				// hard code dummy user if not upload image
//				userDocument.setDocumentPath(
//						"http://127.0.0.1:8090/mlmlogicq/assets/images/uploadImage/ADMIN/dummyuser.jpg");
				 userDocument.setDocumentPath("http://http://getpay.co.in/assets/images/dummyuser.jpg");
				documentUploadService.saveDocumentDetails(userDocument);
			}
			// workflow details
			List<WorkFlow> workflowlist = prepareWorkFlowDetails(userdetailvo);
			workflowservice.createWorkFlowForListValidation(workflowlist);
			if (!workflowlist.isEmpty()) {
				for (WorkFlow workflow : workflowlist) {
					if (!StringUtils.isEmpty(userdetailvo.getUserprofile().getLogindetails().getEmail())) {
					//	EmailDetails emailmessage = prepareEmail(workflow, userdetailvo);
						//emailservice.sendEmail(emailmessage);
					}
				}
			}
			final UserDetailsVO userdet=userdetailvo; 
			ExecutorService executorService = Executors.newFixedThreadPool(1);
			executorService.execute(new Runnable() {
			    public void run() {
					// send SMS to user and admin
					 prepapreSMSDetailsAndSendSMS(userdet);
					// update parent JSON
			    }
			});
			executorService.shutdown();
		}
		return new ResponseEntity<StatusVO>(userdetailvo, HttpStatus.OK);
	}

	private void prepapreSMSDetailsAndSendSMS(UserDetailsVO userdetailvo) {
		SMSDetails usersmsdetails = new SMSDetails();
		usersmsdetails.setMessage(MessageHelper.generateUserRegistartionMessage(
				userdetailvo.getUserprofile().getLogindetails().getUsername(),
				userdetailvo.getUserprofile().getLogindetails().getPassword(),
				userdetailvo.getUserprofile().getFirstname(), userdetailvo.getUserprofile().getLastname()));
		usersmsdetails.setMobilenumber(userdetailvo.getUserprofile().getLogindetails().getMobilenumber());
		usersmsdetails.setMsgreasone("SMS For Registartion");
		SMSHelper.sendSMS(usersmsdetails);

		SMSDetails adminsmsdetails = new SMSDetails();
		adminsmsdetails.setMessage(MessageHelper.generateUserRegistartionMessageForAdmin(
				userdetailvo.getUserprofile().getLogindetails().getMobilenumber(),
				userdetailvo.getUserprofile().getLogindetails().getEmail()));
		adminsmsdetails.setMobilenumber(smsvendor.getMobilenumber());
		adminsmsdetails.setMsgreasone("Notify Admin For new user Added");
		SMSHelper.sendSMS(adminsmsdetails);
	}

	private EmailDetails prepareEmail(WorkFlow workflow, UserDetailsVO userdetailvo) {
		EmailDetails emailMsg = new EmailDetails();
		emailMsg.setSendfrom(smsvendor.getAdminEmail());
		emailMsg.setSendto(userdetailvo.getUserprofile().getLogindetails().getEmail());
		emailMsg.setSubject(workflow.getWorktype());
		emailMsg.setText(workflow.getMessage());
		return emailMsg;
	}

	private List<WorkFlow> prepareWorkFlowDetails(UserDetailsVO userdetailvo) {
		List<WorkFlow> workflowdetails = new ArrayList<>();
		if (!userdetailvo.isMobilenoVerified()) {
			WorkFlow workflowmobile = new WorkFlow();
			workflowmobile.setAssignedto(userdetailvo.getUserprofile().getLogindetails().getUsername());
			workflowmobile.setMessage("Mobile Verification Sucess");
			workflowmobile.setCreatedby(userdetailvo.getUserprofile().getLogindetails().getUsername());
			workflowmobile.setCreatetime(new Date());
			workflowmobile.setWorktype("MOBILE_VERIFICATION");
			workflowmobile.setStatus(true);
			workflowmobile.setProfileid(String.valueOf(userdetailvo.getUserprofile().getId()));
			workflowdetails.add(workflowmobile);
		}
		if (!userdetailvo.isEmailVerified()) {
			WorkFlow workflowemail = new WorkFlow();
			workflowemail.setAssignedto(userdetailvo.getUserprofile().getLogindetails().getUsername());
			workflowemail.setMessage("EMail Verification Sucess");
			workflowemail.setCreatedby(userdetailvo.getUserprofile().getLogindetails().getUsername());
			workflowemail.setCreatetime(new Date());
			workflowemail.setWorktype("EMAIL_VERIFICATION");
			workflowemail.setStatus(true);
			workflowemail.setProfileid(String.valueOf(userdetailvo.getUserprofile().getId()));
			workflowdetails.add(workflowemail);
		}
		if (!userdetailvo.isAdminVerified()) {
			WorkFlow workflowadmin = new WorkFlow();
			workflowadmin.setAssignedto("ADMIN");
			workflowadmin.setMessage("ADMIN Verification Pending");
			workflowadmin.setCreatedby(userdetailvo.getUserprofile().getLogindetails().getUsername());
			workflowadmin.setCreatetime(new Date());
			workflowadmin.setWorktype("ADMIN_VERIFICATION");
			workflowadmin.setStatus(false);
			workflowadmin.setProfileid(String.valueOf(userdetailvo.getUserprofile().getId()));
			workflowdetails.add(workflowadmin);
		}
		return workflowdetails;
	}

	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserProfile> deleteUserDetails(@RequestBody UserProfile userdetails) throws Exception {
		if (null != userdetails) {
			userservice.deleteUser(userdetails);
		}
		return new ResponseEntity<UserProfile>(userdetails, HttpStatus.OK);
	}

	@RequestMapping(value = "/editUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserProfile> editUserDetails(@RequestBody UserProfile userdetails) throws Exception {
		if (null != userdetails) {
			userservice.updateUser(userdetails);
		}
		return new ResponseEntity<UserProfile>(userdetails, HttpStatus.OK);
	}

	@RequestMapping(value = "/fetchUserProfileDetails/{networkid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDetailsVO> fetchUserProfileDetails(@PathVariable String networkid) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		UserDetailsVO userdetailsvo = new UserDetailsVO();
		UserProfile userprofile = new UserProfile();
		WalletStatement walletStatement = new WalletStatement();
		UserPerformance userperformance = new UserPerformance();
		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof LoginVO) {
				LoginVO login = (LoginVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				userprofile = userservice.fetchUserAccordingToUserName(login.getUsername());
				userdetailsvo.setUserprofile(userprofile);
				NetworkInfo networkinfo = networkservice.getNetworkDetails(userprofile.getLogindetails().getUsername());
				NetWorkDetails networkdetails = mapper.readValue(new String(networkinfo.getNetworkjson()),
						NetWorkDetails.class);
				userdetailsvo.setNetworkjson(networkdetails);
				userdetailsvo.setUserperformance(userperformance);
				String authorityname = null;
				List<GrantedAuthority> authorities = (List<GrantedAuthority>) login.getAuthorities();
				if (null != authorities && !authorities.isEmpty()) {
					authorityname = authorities.get(0).getAuthority();
				}
				if (login.getUsername().equals(networkid) || authorityname.equals("ADMIN")) {
					walletStatement.setWallet(userprofile.getWalletdetails());
					walletStatement.setWalletid(userprofile.getWalletdetails().getWalletid());
					walletStatement = walletStatementservice.fetchWalletStmnt(walletStatement);
					userdetailsvo.setWalletStatement(walletStatement);
					//userperformanceservice.fetchUserPerformanceAccordingToAggregation(userperformance);
				} else {

					userdetailsvo.setWalletStatement(walletStatement);
					userdetailsvo.setUserperformance(userperformance);
				}
				List<WorkFlow> workflowlist = workflowservice.getPendingWorkFlowForUser(networkid,
						String.valueOf(userprofile.getId()));
				for (WorkFlow work : workflowlist) {
					if (work.getWorktype().equalsIgnoreCase("MOBILE_VERIFICATION")) {
						userdetailsvo.setMobilenoVerified(work.getStatus());
					}
					if (work.getWorktype().equalsIgnoreCase("EMAIL_VERIFICATION")) {
						userdetailsvo.setEmailVerified(work.getStatus());
					}
					if (work.getWorktype().equalsIgnoreCase("ADMIN_VERIFICATION")) {
						userdetailsvo.setAdminVerified(work.getStatus());
					}

				}
			}
		}
		return new ResponseEntity<UserDetailsVO>(userdetailsvo, HttpStatus.OK);
	}

	@RequestMapping(value = "/createEncashRequest", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EncashVO> createEncashRequest(@RequestBody EncashVO encashvo) throws Exception {
		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof LoginVO) {
				LoginVO login = (LoginVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (encashvo.getEncashamount().compareTo(BigDecimal.ZERO) == 1) {
					UserProfile userprofile = userservice.fetchUserAccordingToUserName(login.getUsername());
					encashvo.setUsername(login.getUsername());
					encashvo.setRequestdate(new Date());
					workflowservice.createWorkFlowForEncashRequest(encashvo, userprofile);
				}else{
					throw new Exception("Insufficient balance for encashing");
				}
			}
		}
		return new ResponseEntity<EncashVO>(encashvo, HttpStatus.OK);

	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ResponseEntity<UserDocument> uploadDocuemnt(@RequestParam("file") MultipartFile file) throws Exception {
		UserDocument docuemntUpload = new UserDocument();
		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof LoginVO) {
				LoginVO login = (LoginVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

				if (!file.isEmpty()) {
					try {
						byte[] bytes = file.getBytes();
						String fileName = login.getUsername() + "_" + System.currentTimeMillis() + ".png";
						String url = context.getContextPath();
						String filedirectory = PropertyHelper.loadUploadProperty().getProperty("fileDirectory");
						// Creating the directory to store file
						String rootPath = PropertyHelper.loadUploadProperty().getProperty("file.filepath");
						File dir = new File(filedirectory + rootPath + File.separator + login.getUsername());
						if (!dir.exists())
							dir.mkdirs();
						// Create the file on server
						File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);
						BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
						stream.write(bytes);
						stream.close();
						String fileUrl = PropertyHelper.loadUploadProperty().getProperty("url") + rootPath
								+ login.getUsername() + "/" + fileName;
						docuemntUpload.setDocumentPath(fileUrl);
						docuemntUpload.setMobileNumber(login.getMobilenumber());
						docuemntUpload.setName(serverFile.getName());
						docuemntUpload.setServiceType("Profile");
						docuemntUpload.setUploadFor(login.getUsername());
						docuemntUpload.setUploadDate(new Date());
						documentUploadService.saveDocumentDetails(docuemntUpload);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return new ResponseEntity<UserDocument>(docuemntUpload, HttpStatus.OK);
	}

	@RequestMapping(value = "/getProfileImage", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<byte[]> getProfileImage() throws Exception {
		byte[] imageBytes = null;
		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof LoginVO) {
				LoginVO login = (LoginVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				UserDocument profileDoc = documentUploadService.getDocumentsAccordingToUserName(login.getUsername());
				final HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.IMAGE_PNG);
				InputStream in = context.getResourceAsStream(profileDoc.getDocumentPath());
				imageBytes = IOUtils.toByteArray(in);
				return new ResponseEntity<byte[]>(imageBytes, headers, HttpStatus.OK);
			}
		}
		return new ResponseEntity<byte[]>(imageBytes, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/updatepassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDetailsVO> updatePassword(@RequestBody PasswordVO passwordVO) throws Exception {
		UserDetailsVO userDetails = null;
		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof LoginVO) {
				LoginVO login = (LoginVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (login.getPassword().equals(passwordVO.getOldpassword())) {
					UserProfile userprofile = userservice.fetchUserAccordingToUserName(login.getUsername());
					if (passwordVO.getNewPassword().equals(passwordVO.getConfirmPasword())) {
						userprofile.getLogindetails().setPassword(passwordVO.getConfirmPasword());
					} else {
						return new ResponseEntity<UserDetailsVO>(userDetails, HttpStatus.BAD_REQUEST);
					}
					loginService.updateLogingDetails(userprofile.getLogindetails());
					com.logicq.mlm.service.security.UserService.addUser(userprofile.getLogindetails());
					return new ResponseEntity<UserDetailsVO>(userDetails, HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<UserDetailsVO>(userDetails, HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "/fetchUserNetworkDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDetailsVO> fetchUserNetworkDetails() throws Exception {
		NetWorkDetails networkDetails = null;
		UserDetailsVO userdetailvo=new UserDetailsVO();
		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof LoginVO) {
				LoginVO login = (LoginVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				NetworkInfo networkinfo=networkservice.getNetworkDetails(login.getUsername());
				 networkDetails = PropertyHelper.convertJsonToNetworkInfo(networkinfo);
				 userdetailvo.setNetworkjson(networkDetails); 
				 UserProfile userprofile = userservice.fetchUserAccordingToUserName(login.getUsername());
				   WalletStatement walletStatement=new WalletStatement();
				    walletStatement.setWallet(userprofile.getWalletdetails());
					walletStatement.setWalletid(userprofile.getWalletdetails().getWalletid());
					walletStatement = walletStatementservice.fetchWalletStmnt(walletStatement);
					userdetailvo.setWalletStatement(walletStatement);
			}
		}
		return new ResponseEntity<UserDetailsVO>(userdetailvo, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/getUserWalletDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WalletStmntVO> getUserWalletDetails() throws Exception {
		WalletStmntVO walletstmsntvo=new WalletStmntVO();
		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof LoginVO) {
				LoginVO login = (LoginVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				 UserProfile userprofile = userservice.fetchUserAccordingToUserName(login.getUsername());
				WalletStatement walletstmnt=walletStatementservice.fetchWalletStmntFromWalletNumber(userprofile.getWalletdetails().getWalletnumber());
				walletstmsntvo.setCurrentbalance(walletstmnt.getCurrentbalance());
				walletstmsntvo.setEncashedAmount(walletstmnt.getEncashedAmount());
				walletstmsntvo.setMaxencashable(walletstmnt.getMaxencashable());
				walletstmsntvo.setPayout(walletstmnt.getPayout());
				walletstmsntvo.setWalletid(walletstmnt.getWalletid());
				walletstmsntvo.setWalletlastupdate(walletstmnt.getWalletlastupdate());
			}
		}
		return new ResponseEntity<WalletStmntVO>(walletstmsntvo, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/txndetailsforrefrenceno",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TxnRollBackVO> getTransactionDetailsForRefrenceNumber(@RequestBody TxnRollBackVO txnrollbackvo) throws Exception {
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof LoginVO) {
			LoginVO login = (LoginVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if ("ADMIN".equals(login.getUsername())) {
				List<TransactionDetails> transactionlist = transactionDetailsService
						.getTransactionDetailsAccordingToRefrenceNumber(txnrollbackvo.getTxnRefrenceNumber());
				for (TransactionDetails txnDetails : transactionlist) {
					if (txnDetails.getTxntype().equals("RECIVED")) {
						WalletDetails walletDetails = new WalletDetails();
						walletDetails.setWalletid(txnDetails.getWalletid());
						walletDetails = walletDetailsService.fetchWalletDetails(walletDetails);
						UserProfile userProfile = walletDetails.getUserprofile();
						WalletStatement payeewalletStatment = walletservice.fetchWalletStmnt(txnDetails.getWalletid());
						txnrollbackvo.setPayeeCurrentBalance(payeewalletStatment.getCurrentbalance());
						txnrollbackvo.setPayeeFirstName(userProfile.getFirstname());
						txnrollbackvo.setPayeeLastName(userProfile.getLastname());
						txnrollbackvo.setPayeeGpmIdNo(walletDetails.getWalletnumber());
						txnrollbackvo.setRollbackAmount(txnDetails.getAmount());
						txnrollbackvo.setTxnDate(txnDetails.getTxndate());
						txnrollbackvo.setTxnRefrenceNumber(txnDetails.getDescription());
					}
					if (txnDetails.getTxntype().equals("SEND")) {
						WalletDetails walletDetails = new WalletDetails();
						walletDetails.setWalletid(txnDetails.getWalletid());
						walletDetails = walletDetailsService.fetchWalletDetails(walletDetails);
						UserProfile userProfile = walletDetails.getUserprofile();
						txnrollbackvo.setCrediotrLastName(userProfile.getLastname());
						txnrollbackvo.setCreditorFirstName(userProfile.getFirstname());
						txnrollbackvo.setCreditorGpmIdNo(walletDetails.getWalletnumber());
					
					}
				}
			}
		}
		return new ResponseEntity<TxnRollBackVO>(txnrollbackvo, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/reloaNetworkPerformanceCount", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDetailsVO> reloadWallet() throws Exception {
		UserDetailsVO userdetailsvo = new UserDetailsVO();
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof LoginVO) {
			LoginVO login = (LoginVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<NetworkCountVO> networkCountList=userNetworkPerformance.getNetworkPerformanceAccordingToUser(login.getUsername());
			userdetailsvo.setNetworkcountlist(networkCountList);
		}
		return new ResponseEntity<UserDetailsVO>(userdetailsvo, HttpStatus.OK);
	}
	
}
