package com.logicq.mlm.dao.performance;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.logicq.mlm.dao.AbstractDAO;
import com.logicq.mlm.model.performance.UserNetworkCount;

@Repository
public class UserNetworkPerformanceDAO extends AbstractDAO<UserNetworkCount> implements IUserNetworkPerformanceDAO{

	@Override
	public void updateUserNetworkPerformance(UserNetworkCount usernetowrk) {
		update(usernetowrk);
	}

	@Override
	public void addUserNetworkPerformance(UserNetworkCount usernetowrk) {
		save(usernetowrk);
	}
	
	@Override
	public void addUserNetworkPerformanceList(List<UserNetworkCount> usernetowrkList) {
		saveOrUpdateList(usernetowrkList);
	}
	
	@Override
	public void saveorupdateUserNetworkPerformance(UserNetworkCount usernetowrk) {
		saveOrUpdate(usernetowrk);
	}
	

	@Override
	public List<UserNetworkCount> getNetworkPerformance(UserNetworkCount usernetowrk) {
		StringBuilder query=new StringBuilder();
		query.append(" from UserNetworkCount un where un.memberid='"+usernetowrk.getMemberid()+"'");
		return (List<UserNetworkCount>) execcuteQuery(query.toString());
	}

	@Override
	public UserNetworkCount getNetworkPerformanceForMemeberandLevel(UserNetworkCount usernetowrk) {
		StringBuilder query=new StringBuilder();
		query.append(" from UserNetworkCount un where un.memberid='"+usernetowrk.getMemberid()+"' and un.networklevel='"+usernetowrk.getNetworklevel()+"'");
		return  execcuteQueryForUnique(query.toString());
	}

	



}
