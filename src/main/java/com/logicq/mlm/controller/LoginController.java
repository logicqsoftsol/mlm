package com.logicq.mlm.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logicq.mlm.common.factory.LoginFactory;
import com.logicq.mlm.model.admin.TaskDetails;
import com.logicq.mlm.model.performance.UserPerformance;
import com.logicq.mlm.model.profile.NetWorkDetails;
import com.logicq.mlm.model.profile.NetworkInfo;
import com.logicq.mlm.model.profile.UserDocument;
import com.logicq.mlm.model.profile.UserProfile;
import com.logicq.mlm.model.wallet.WalletStatement;
import com.logicq.mlm.model.workflow.WorkFlow;
import com.logicq.mlm.service.networkdetails.INetworkDetailsService;
import com.logicq.mlm.service.performance.IUserNetworkPerformanceService;
import com.logicq.mlm.service.performance.IUserPerformanceService;
import com.logicq.mlm.service.security.UserService;
import com.logicq.mlm.service.user.IDocumentUploadService;
import com.logicq.mlm.service.user.IUserService;
import com.logicq.mlm.service.wallet.IWalletStmntService;
import com.logicq.mlm.service.workflow.IWorkFlowService;
import com.logicq.mlm.vo.EncashVO;
import com.logicq.mlm.vo.LoginVO;
import com.logicq.mlm.vo.NetworkCountVO;
import com.logicq.mlm.vo.NetworkVO;
import com.logicq.mlm.vo.UserDetailsVO;

@RestController
@RequestMapping("/api")
public class LoginController {

	@Autowired
	IUserService userservice;
	
	@Autowired
	IWalletStmntService walletStatementservice;
	
	@Autowired
    IUserPerformanceService userperformanceservice;
	
	@Autowired
	IWorkFlowService workflowservice;
	@Autowired
	INetworkDetailsService networkservice;
	@Autowired
	IDocumentUploadService documentUploadService;
	
	@Autowired
	IUserNetworkPerformanceService userNetworkPerformance;
	
	  
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDetailsVO> login() {
		ObjectMapper mapper = new ObjectMapper();
		UserDetailsVO userdetailsvo=new UserDetailsVO();
		UserProfile userprofile = new UserProfile();
		WalletStatement walletStatement=new WalletStatement();
		//UserPerformance userperformance=new UserPerformance();
		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof LoginVO) {
				LoginVO login = (LoginVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				try {
					String authorityname=null;
					List<GrantedAuthority> authorities= (List<GrantedAuthority>) login.getAuthorities();
					if(null!=authorities && !authorities.isEmpty()){
						authorityname=authorities.get(0).getAuthority();
					}
					userprofile.setLogindetails(LoginFactory.createLoginDetails(login));
					userprofile = userservice.fetchUser(userprofile);
					userdetailsvo.setUserprofile(userprofile);
					List<TaskDetails> tasklist=new ArrayList<TaskDetails>();
					if(authorityname.equals("ADMIN")){
						List<WorkFlow> workflowlist = workflowservice.getPendingWorkFlowForAdmin(authorityname);
					
						for(WorkFlow workflow:workflowlist){
							TaskDetails task=new TaskDetails();
							if(workflow.getWorktype().equals("ENCASH_REQUEST")){
								EncashVO encashvo=mapper.readValue(new String(workflow.getWorkparameter()), EncashVO.class);
								task.setEncashvo(encashvo);
							}
							task.setPriority("HIGH");
							task.setTaskassigneddate(workflow.getCreatetime());
							task.setTaskfor(workflow.getCreatedby());
							task.setTaskname(workflow.getWorktype());
							task.setMessage(workflow.getMessage());
							if(!workflow.getStatus()){
								task.setTaskstatus("Pending");	
							}							
							task.setTasktype(workflow.getWorktype());
							task.setTaskid(workflow.getWorkid());
							tasklist.add(task);
						}
						userdetailsvo.setTasklist(tasklist);
						List<NetworkVO> networkinfolist=networkservice.getAllNetworkList(1, 20);
						userdetailsvo.setNetworkinfolist(networkinfolist);
					}else{
					List<WorkFlow> workflowlist = workflowservice.getPendingWorkFlowForUser(login.getUsername(),
							String.valueOf(userprofile.getId()));
					for (WorkFlow work : workflowlist) {
						if (work.getWorktype().equalsIgnoreCase("MOBILE_VERIFICATION")) {
							userdetailsvo.setMobilenoVerified(work.getStatus());
						} 
//						if (work.getWorktype().equalsIgnoreCase("EMAIL_VERIFICATION")) {
//							userdetailsvo.setEmailVerified(work.getStatus());
//						}
						if (work.getWorktype().equalsIgnoreCase("ADMIN_VERIFICATION")) {
							userdetailsvo.setAdminVerified(work.getStatus());
						} 
						if(work.getWorktype().equals("ENCASH_REQUEST") && !work.getStatus()){
							TaskDetails task=new TaskDetails();
							EncashVO encashvo=mapper.readValue(new String(work.getWorkparameter()), EncashVO.class);
							task.setEncashvo(encashvo);
							task.setPriority("HIGH");
							task.setTaskassigneddate(work.getCreatetime());
							task.setTaskfor(work.getCreatedby());
							task.setTaskname(work.getWorktype());
							task.setMessage(work.getMessage());
							if(!work.getStatus()){
								task.setTaskstatus("Pending");	
							}							
							task.setTasktype(work.getWorktype());
							task.setTaskid(work.getWorkid());
							tasklist.add(task);
						}
					}
					userdetailsvo.setTasklist(tasklist);
					List<NetworkVO> networkinfolist=networkservice.getNetworkDetailsForParentWithPagination(login.getUsername());
					userdetailsvo.setNetworkinfolist(networkinfolist);
					}
					NetworkInfo networkinfo=networkservice.getNetworkDetails(login.getUsername());
					NetWorkDetails networkdetails = mapper.readValue(new String(networkinfo.getNetworkjson()), NetWorkDetails.class);
					userdetailsvo.setNetworkjson(networkdetails);
					walletStatement.setWallet(userprofile.getWalletdetails());
					walletStatement.setWalletid(userprofile.getWalletdetails().getWalletid());
					walletStatement=walletStatementservice.fetchWalletStmnt(walletStatement);
					userdetailsvo.setWalletStatement(walletStatement);
					UserDocument userdoc=documentUploadService.getDocumentsAccordingToUserName(login.getUsername());
					userdetailsvo.setDocument(userdoc);
					List<NetworkCountVO> networkCountList=userNetworkPerformance.getNetworkPerformanceAccordingToUser(login.getUsername());
					userdetailsvo.setNetworkcountlist(networkCountList);
				} catch (Exception e) {
					return new ResponseEntity<UserDetailsVO>(userdetailsvo, HttpStatus.INTERNAL_SERVER_ERROR);
				}

			}

		}
		return new ResponseEntity<UserDetailsVO>(userdetailsvo, HttpStatus.OK);
	}
	
	
	
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LoginVO> logout() {
		LoginVO login =null;
		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof LoginVO) {
				login = (LoginVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				String token=(String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
				if (!StringUtils.isEmpty(token)) {
					LoginVO logindetails =null; //tokenAuthenticationService.getTokenHandler().parseUserFromToken(token);
					UserService.removeUser(login.getUsername());
				} 
			
			}

		}
		return new ResponseEntity<LoginVO>(login, HttpStatus.OK);
	}

	@RequestMapping(value = "/forgetpassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LoginVO> resetPassword() {
		LoginVO login =null;
		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof LoginVO) {
				login = (LoginVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				String token=(String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
				if (!StringUtils.isEmpty(token)) {
					LoginVO logindetails =null; //tokenAuthenticationService.getTokenHandler().parseUserFromToken(token);
					UserService.removeUser(login.getUsername());
				} 
			
			}

		}
		return new ResponseEntity<LoginVO>(login, HttpStatus.OK);
	}
	
}
