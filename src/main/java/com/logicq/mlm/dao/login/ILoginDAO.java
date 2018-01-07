package com.logicq.mlm.dao.login;

import com.logicq.mlm.model.login.Login;

public interface ILoginDAO {
	
	void loadUsers();
	
	void updateLogingDetails(Login login);

}
