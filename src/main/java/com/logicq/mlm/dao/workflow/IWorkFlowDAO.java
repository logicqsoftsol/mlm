package com.logicq.mlm.dao.workflow;

import java.util.List;

import com.logicq.mlm.model.workflow.WorkFlow;

public interface IWorkFlowDAO {
	
	public void createWorkFlowForValidation(WorkFlow workflow)throws Exception;
	
	public void createWorkFlowForListValidation(List<WorkFlow> workflow)throws Exception;
	
	public void updateWorkFlow(WorkFlow workflow)throws Exception;
	
	public List<WorkFlow> getPendingWorkFlowForUser(String assignedto, String profileid) throws Exception;
	public List<WorkFlow> getPendingWorkFlowForAdmin(String assignedto)throws Exception;
	
	public WorkFlow getWorkFlowAccordingToWorkId(Long workid)throws Exception;
	
	public List<WorkFlow> getPendingWorkFlowAccordingToProfile(String profileid);
	public List<WorkFlow> getWorkFlowForUserAccordingToWorkType(String worktype,String username);
	public List<WorkFlow> getWorkFlowStatusForLogin() throws Exception;

}
