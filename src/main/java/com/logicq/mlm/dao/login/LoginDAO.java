package com.logicq.mlm.dao.login;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.logicq.mlm.common.factory.LoginFactory;
import com.logicq.mlm.dao.AbstractDAO;
import com.logicq.mlm.model.login.Login;
import com.logicq.mlm.service.security.UserService;

@Repository
public class LoginDAO extends AbstractDAO<Login> implements ILoginDAO {

	@Override
	public void loadUsers() {
		List<Login> userdetails = (List<Login>) loadClass(Login.class);
		for (Login user : userdetails) {
			UserService.addUser(user);
		}
	}

	@Override
	public void updateLogingDetails(Login login) {
		update(login);
		
	}

}
