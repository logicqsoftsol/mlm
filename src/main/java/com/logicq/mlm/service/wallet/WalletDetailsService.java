package com.logicq.mlm.service.wallet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.logicq.mlm.dao.wallet.IWalletDetailsDAO;
import com.logicq.mlm.model.profile.WalletDetails;

@Service
@Transactional
public class WalletDetailsService implements IWalletDetailsService {
	
	@Autowired
	IWalletDetailsDAO walletDetailsDAO;

	@Override
	public void createWallet(WalletDetails walletdetails) {
		walletDetailsDAO.createWallet(walletdetails);
		
	}

	@Override
	public WalletDetails fetchWalletDetails(WalletDetails walletdetails) {
		// TODO Auto-generated method stub
		return walletDetailsDAO.fetchWalletDetails(walletdetails);
	}

	@Override
	public void updateWalletDetails(WalletDetails walletdetails) {
		walletDetailsDAO.updateWalletDetails(walletdetails);
	}

	@Override
	public WalletDetails getWalletDetails(String walletid) {
		return walletDetailsDAO.getWalletDetails(walletid);
	}
	
}
