package com.logicq.mlm.service.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.logicq.mlm.dao.login.ILoginDAO;
import com.logicq.mlm.model.login.Login;

@Service
@Transactional
public class LoginService implements ILoginService{

	@Autowired
	ILoginDAO logindao;
	
	@Override
	public void loadusers() {
		logindao.loadUsers();
	}

	@Override
	public void updateLogingDetails(Login login) {
		logindao.updateLogingDetails(login);
		
	}
	
	

}
