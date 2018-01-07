package com.logicq.mlm.dao.wallet;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.logicq.mlm.dao.AbstractDAO;
import com.logicq.mlm.model.admin.FeeSetup;

@Repository
public class FeeSetupDAO extends AbstractDAO<FeeSetup> implements IFeeSetupDAO {

	@Override
	public List<FeeSetup> getFeeDetails() {
		return (List<FeeSetup>) loadClass(FeeSetup.class);
	}

}
