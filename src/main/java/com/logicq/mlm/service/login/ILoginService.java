package com.logicq.mlm.service.login;

import com.logicq.mlm.model.login.Login;

public interface ILoginService {
	
	void loadusers();
	void updateLogingDetails(Login login);

}
