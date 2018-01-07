package com.logicq.mlm.dao.networkdetails;

import java.util.List;

import com.logicq.mlm.model.admin.NetWorkTask;

public interface INetworkTaskDAO {
	
   List<NetWorkTask> getNetworkTaskList();

   void	updateNetworkTask(NetWorkTask networkTask);
   
   void	createNetworkTask(NetWorkTask networkTask);
   
   void deleteNetworkTask(NetWorkTask networkTask);

}
