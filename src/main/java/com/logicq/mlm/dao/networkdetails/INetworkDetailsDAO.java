package com.logicq.mlm.dao.networkdetails;

import java.util.List;

import com.logicq.mlm.model.profile.NetworkInfo;

public interface INetworkDetailsDAO {
	
	NetworkInfo getNetworkDetails(String memberid);
	void saveNetworkDetails(NetworkInfo networkinfo);
	void updateNetworkDetails(NetworkInfo networkinfo);
    List<NetworkInfo> getNetworkDetailsForParent(String parentid);
    NetworkInfo getUpdatePedingNetworkDetails(String memberid);
    List<NetworkInfo> getAllNetworkList(int pagesize,int pagenumber);
    List<NetworkInfo> getNetworkDetailsForParentWithPagination(String parentid) ;

}
