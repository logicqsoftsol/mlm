package com.logicq.mlm.dao.user;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.logicq.mlm.dao.AbstractDAO;
import com.logicq.mlm.model.profile.UserProfile;

@Repository
public class UserProfileDAO extends AbstractDAO<UserProfile> implements IUserDAO{

	@Override
	public UserProfile fetchUser(UserProfile user) {
		StringBuilder query=new StringBuilder();
		query.append(" from UserProfile up where up.logindetails.username ='"+user.getLogindetails().getUsername()+"'");
		List<UserProfile> userprofiles= (List<UserProfile>) execcuteQuery(query.toString());
		return   userprofiles.get(0);
		
	}
	
	@Override
	public UserProfile fetchUserAccordingToUserName(String username) {
		StringBuilder query=new StringBuilder();
		query.append(" from UserProfile up where up.logindetails.username ='"+username+"'");
		List<UserProfile> userprofiles= (List<UserProfile>) execcuteQuery(query.toString());
		return   userprofiles.get(0);
		
	}
	
	@Override
	public UserProfile fetchUserAccordingToProfileId(Long profileid) {
		StringBuilder query=new StringBuilder();
		query.append(" from UserProfile up where up.id = "+profileid);
		List<UserProfile> userprofiles= (List<UserProfile>) execcuteQuery(query.toString());
		return   userprofiles.get(0);
		
	}
	

	@Override
	public void deleteUser(UserProfile user) {
		delete(user);
		
	}

	@Override
	public void saveUser(UserProfile user) {
		save(user);
		
	}

	@Override
	public void updateUser(UserProfile user) {
		saveOrUpdate(user);
	}

}
