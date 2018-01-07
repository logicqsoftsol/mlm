package com.logicq.mlm.service.networkdetails;

import java.util.List;

import com.logicq.mlm.model.profile.NetworkInfo;
import com.logicq.mlm.vo.NetworkVO;

public interface INetworkDetailsService {
	NetworkInfo getNetworkDetails(String memberid);
	void saveNetworkDetails(NetworkInfo networkinfo);
	void updateNetworkDetails(NetworkInfo networkinfo);
	List<NetworkInfo> getNetworkDetailsForParent(String parentid);
	 NetworkInfo getUpdatePedingNetworkDetails(String memberid);
	 List<NetworkVO> getAllNetworkList(int pagesize,int pagenumber);
	 List<NetworkVO> getNetworkDetailsForParentWithPagination(String parentid);

}
