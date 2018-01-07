package com.logicq.mlm.dao.networkdetails;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.logicq.mlm.dao.AbstractDAO;
import com.logicq.mlm.model.profile.NetworkInfo;

@Repository
public class NetworkDetailsDAO  extends AbstractDAO<NetworkInfo> implements INetworkDetailsDAO{

	@Override
	public NetworkInfo getNetworkDetails(String memberid) {
		StringBuilder query=new StringBuilder();
		query.append(" from NetworkInfo ni where ni.memberid='"+memberid+"'");
		// need to change query may break as null pointer
		List<NetworkInfo> networkinfolist=(List<NetworkInfo>) execcuteQuery(query.toString());
		if(null!=networkinfolist && !networkinfolist.isEmpty()){
			return networkinfolist.get(0);
		}
		return null;
	}
	
	@Override
	public List<NetworkInfo> getNetworkDetailsForParent(String parentid) {
		StringBuilder query=new StringBuilder();
		query.append(" from NetworkInfo ni where ni.parentmemberid='"+parentid+"'");
		// need to change query may break as null pointer
		return (List<NetworkInfo>) execcuteQuery(query.toString());
	}
	

	@Override
	public void saveNetworkDetails(NetworkInfo networkinfo) {
		save(networkinfo);
	}

	@Override
	public void updateNetworkDetails(NetworkInfo networkinfo) {
		update(networkinfo);
	}

	@Override
	public NetworkInfo getUpdatePedingNetworkDetails(String memberid) {
		StringBuilder query=new StringBuilder();
		query.append(" from NetworkInfo ni where isUpdate=false and ni.memberid='"+memberid+"'");
		// need to change query may break as null pointer
		List<NetworkInfo> networkinfolist=(List<NetworkInfo>) execcuteQuery(query.toString());
		if(null!=networkinfolist && !networkinfolist.isEmpty()){
			return (NetworkInfo) execcuteQuery(query.toString()).get(0);
		}
		return null;
	}

	@Override
	public List<NetworkInfo> getAllNetworkList(int pagesize, int pagenumber) {
		StringBuilder query=new StringBuilder();
		return loadClassWithPagination(NetworkInfo.class,pagesize,pagenumber);
	}
	
	
	@Override
	public List<NetworkInfo> getNetworkDetailsForParentWithPagination(String parentid) {
		StringBuilder query=new StringBuilder();
		query.append(" from NetworkInfo ni where ni.parentmemberid='"+parentid+"' order by dateofjoin desc");
		// need to change query may break as null pointer
		return (List<NetworkInfo>) executeQueryWithPagination(query.toString(), 1, 10);
	}

}
