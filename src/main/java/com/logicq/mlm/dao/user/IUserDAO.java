package com.logicq.mlm.dao.user;

import com.logicq.mlm.model.profile.UserProfile;

public interface IUserDAO {
	
	UserProfile fetchUser(UserProfile user);
	UserProfile fetchUserAccordingToUserName(String username);
	UserProfile fetchUserAccordingToProfileId(Long profileid);
	void deleteUser(UserProfile user);
	void saveUser(UserProfile user);
	void updateUser(UserProfile user);

}
