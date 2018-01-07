package com.logicq.mlm.service.user;

import com.logicq.mlm.model.profile.UserProfile;

public interface IUserService {

	UserProfile fetchUser(UserProfile user);

	UserProfile fetchUserAccordingToProfileId(Long id);

	UserProfile fetchUserAccordingToUserName(String username);

	void deleteUser(UserProfile user);

	void saveUser(UserProfile user) throws Exception;

	void updateUser(UserProfile user);

}
