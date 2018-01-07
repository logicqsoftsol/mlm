package com.logicq.mlm.dao.networkdetails;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.logicq.mlm.dao.AbstractDAO;
import com.logicq.mlm.model.admin.NetWorkTask;

@Repository
public class NetworkTaskDAO extends AbstractDAO<NetWorkTask> implements INetworkTaskDAO {

	@Override
	public List<NetWorkTask> getNetworkTaskList() {
		return (List<NetWorkTask>) loadClass(NetWorkTask.class);
	}

	@Override
	public void updateNetworkTask(NetWorkTask networkTask) {
		update(networkTask);
		
	}

	@Override
	public void createNetworkTask(NetWorkTask networkTask) {
		save(networkTask);
	}

	

	@Override
	public void deleteNetworkTask(NetWorkTask networkTask) {
	   delete(networkTask);
	}



}
