package com.logicq.mlm.service.workflow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logicq.mlm.common.helper.sms.MessageHelper;
import com.logicq.mlm.common.vendor.sms.SMSVendor;
import com.logicq.mlm.dao.workflow.IWorkFlowDAO;
import com.logicq.mlm.model.message.EmailDetails;
import com.logicq.mlm.model.profile.UserProfile;
import com.logicq.mlm.model.workflow.WorkFlow;
import com.logicq.mlm.service.messaging.IEmailService;
import com.logicq.mlm.vo.EncashVO;

@Service
@Transactional
public class WorkFlowService  implements IWorkFlowService{

	@Autowired
	IWorkFlowDAO workflowdao;
	
	@Autowired
	IEmailService emailservice;
	
	private final static  SMSVendor smsvendor=SMSVendor.getInstance();
	
	@Override
	public void createWorkFlowForValidation(WorkFlow workflow) throws Exception {
		workflowdao.createWorkFlowForValidation(workflow);
	}

	@Override
	public void createWorkFlowForListValidation(List<WorkFlow> workflow) throws Exception {
		workflowdao.createWorkFlowForListValidation(workflow);
		
	}

	@Override
	public void updateWorkFlow(WorkFlow workflow) throws Exception {
		workflowdao.updateWorkFlow(workflow);
	}

	@Override
	public List<WorkFlow> getPendingWorkFlowForUser(String assignedto, String profileid) throws Exception {
		return workflowdao.getPendingWorkFlowForUser(assignedto, profileid);
	}

	@Override
	public List<WorkFlow> getPendingWorkFlowForAdmin(String assignedto) throws Exception {
		
		return workflowdao.getPendingWorkFlowForAdmin(assignedto);
	}

	@Override
	public WorkFlow getWorkFlowAccordingToWorkId(Long workid) throws Exception {
		return workflowdao.getWorkFlowAccordingToWorkId(workid);
	}

	@Override
	public List<WorkFlow> getPendingWorkFlowAccordingToProfile(String profileid) {
		return workflowdao.getPendingWorkFlowAccordingToProfile(profileid);
	}

	@Override
	public void createWorkFlowForEncashRequest(EncashVO encashvo,UserProfile userprofile) throws Exception {
		WorkFlow workflowadmin=new WorkFlow();
		workflowadmin.setAssignedto("ADMIN");
		workflowadmin.setCreatedby(userprofile.getLogindetails().getUsername());
		workflowadmin.setCreatetime(new Date());
		workflowadmin.setMessage("Encash Request Pending");
		workflowadmin.setProfileid(String.valueOf(userprofile.getId()));
		workflowadmin.setStatus(Boolean.FALSE);
		workflowadmin.setWorktype("ENCASH_REQUEST");
		byte[] encashparameter=prepareWorkFlowParameter(encashvo);
		workflowadmin.setWorkparameter(encashparameter);
		
		WorkFlow workflowuser=new WorkFlow();
		workflowuser.setAssignedto(userprofile.getLogindetails().getUsername());
		workflowuser.setCreatedby(userprofile.getLogindetails().getUsername());
		workflowuser.setCreatetime(new Date());
		workflowuser.setMessage("Request For Encashing ");
		workflowuser.setProfileid(String.valueOf(userprofile.getId()));
		workflowuser.setStatus(Boolean.FALSE);
		workflowuser.setWorktype("ENCASH_REQUEST");
		workflowuser.setWorkparameter(encashparameter);
		List<WorkFlow> workflowlist=new ArrayList<WorkFlow>();
		workflowlist.add(workflowuser);
		workflowlist.add(workflowadmin);
		workflowdao.createWorkFlowForListValidation(workflowlist);
		
		String adminmessage=MessageHelper.generateAmountEncashMessageForAdmin(userprofile.getLogindetails().getUsername(), userprofile.getLogindetails().getMobilenumber(), encashvo.getEncashamount());
		String usermessage=MessageHelper.generateAmountEncashMessageForUser(userprofile.getLogindetails().getUsername(), userprofile.getLogindetails().getMobilenumber(), encashvo.getEncashamount());
		EmailDetails adminemail=new EmailDetails();
		adminemail.setSenddate(new Date());
		adminemail.setSendto(smsvendor.getAdminEmail());
		adminemail.setSubject("Encashing Request For UserName : "+userprofile.getLogindetails().getUsername());
		adminemail.setText(adminmessage);
		
		EmailDetails useremail=new EmailDetails();
		useremail.setSenddate(new Date());
		useremail.setSendto(userprofile.getLogindetails().getEmail());
		useremail.setSubject("Encashing Request to Admin ");
		useremail.setText(usermessage);
		
		emailservice.sendEmail(adminemail);
		emailservice.sendEmail(useremail);
		
		//SMS can be added
	}

	private byte[] prepareWorkFlowParameter(EncashVO encashvo) throws Exception {
		ObjectMapper objectmapper=new ObjectMapper();
		return objectmapper.writeValueAsBytes(encashvo);
	}

	@Override
	public List<WorkFlow> getWorkFlowForUserAccordingToWorkType(String worktype, String username) {
		return workflowdao.getWorkFlowForUserAccordingToWorkType(worktype, username);
	}

	@Override
	public List<WorkFlow> getWorkFlowStatusForLogin() throws Exception {
		return workflowdao.getWorkFlowStatusForLogin();
	}

}
