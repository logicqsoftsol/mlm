package com.logicq.mlm.service.wallet;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.logicq.mlm.dao.wallet.IFeeSetupDAO;
import com.logicq.mlm.model.admin.FeeSetup;


@Service
@Transactional
public class FeeSetupService implements IFeeSetupService {

	@Autowired
	IFeeSetupDAO feesetupdao;
	
	@Override
	public List<FeeSetup> getFeeDetails() {
		return feesetupdao.getFeeDetails();
	}

}
