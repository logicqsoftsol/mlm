package com.logicq.mlm.dao.workflow;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.logicq.mlm.dao.AbstractDAO;
import com.logicq.mlm.model.workflow.WorkFlow;

@Repository
public class WorkFlowDAO extends AbstractDAO<WorkFlow> implements IWorkFlowDAO {

	@Override
	public void createWorkFlowForValidation(WorkFlow workflow) {
		save(workflow);
	}

	@Override
	public void updateWorkFlow(WorkFlow workflow) throws Exception {
		update(workflow);	
	}

	@Override
	public void createWorkFlowForListValidation(List<WorkFlow> workflow) throws Exception {
		saveList(workflow);
	}

	@Override
	public List<WorkFlow> getPendingWorkFlowForUser(String assignedto, String profileid) throws Exception {
		 StringBuilder query=new StringBuilder();
		 query.append(" from WorkFlow wf where  wf.profileid ='"+profileid+"'");
		return  (List<WorkFlow>) execcuteQuery(query.toString());
	}

	
	@Override
	public List<WorkFlow> getPendingWorkFlowAccordingToProfile(String profileid) {
		 StringBuilder query=new StringBuilder();
		 query.append(" from WorkFlow wf where  wf.profileid ='"+profileid+"'"+" and wf.status ='"+false+"'");
		return  (List<WorkFlow>) execcuteQuery(query.toString());
	}

	@Override
	public List<WorkFlow> getWorkFlowForUserAccordingToWorkType(String worktype,String username) {
		 StringBuilder query=new StringBuilder();
		 query.append(" from WorkFlow wf where  wf.assignedto ='"+username+"'"+" and wf.status ='"+false+"'"+" and wf.worktype='"+worktype+"'");
		return  (List<WorkFlow>) execcuteQuery(query.toString());
	}

	
	
	@Override
	public List<WorkFlow> getPendingWorkFlowForAdmin(String assignedto) throws Exception {
		StringBuilder query=new StringBuilder();
		 query.append(" from WorkFlow wf where wf.assignedto='"+assignedto+"'"+" and wf.status ='"+false+"'");
		return (List<WorkFlow>) execcuteQuery(query.toString());
	}

	@Override
	public WorkFlow getWorkFlowAccordingToWorkId(Long workid) throws Exception {
		StringBuilder query=new StringBuilder();
		 query.append(" from WorkFlow wf where wf.workid='"+workid+"'");
		return (WorkFlow) execcuteQuery(query.toString()).get(0);
	}
	
	@Override
	public List<WorkFlow> getWorkFlowStatusForLogin() throws Exception {
		StringBuilder query=new StringBuilder();
		query.append(" from WorkFlow wf where wf.worktype ='ADMIN_VERIFICATION' and wf.status=true");
		return (List<WorkFlow>) execcuteQuery(query.toString());
	}

}
