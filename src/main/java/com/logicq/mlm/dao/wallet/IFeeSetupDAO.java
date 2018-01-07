package com.logicq.mlm.dao.wallet;

import java.util.List;

import com.logicq.mlm.model.admin.FeeSetup;

public interface IFeeSetupDAO {
	
	List<FeeSetup> getFeeDetails();

}
