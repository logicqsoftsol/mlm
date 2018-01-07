package com.logicq.mlm.service.networkdetails;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.logicq.mlm.dao.networkdetails.INetworkTaskDAO;
import com.logicq.mlm.model.admin.NetWorkTask;

@Service
@Transactional
public class NetworkTaskService implements INetworkTaskService{

	@Autowired
	INetworkTaskDAO networktaskdao;
	
	@Override
	public List<NetWorkTask> getNetworkTaskList() {
		// TODO Auto-generated method stub
		return networktaskdao.getNetworkTaskList();
	}

	@Override
	public void updateNetworkTask(NetWorkTask networkTask) {
		networktaskdao.updateNetworkTask(networkTask);
		
	}

	@Override
	public void createNetworkTask(NetWorkTask networkTask) {
		networktaskdao.createNetworkTask(networkTask);
	}

	@Override
	public void deleteNetworkTask(NetWorkTask networkTask) {
		networktaskdao.deleteNetworkTask(networkTask);
	}

	
	
}
