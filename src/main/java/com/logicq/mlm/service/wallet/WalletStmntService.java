package com.logicq.mlm.service.wallet;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.logicq.mlm.dao.wallet.IWalletDetailsDAO;
import com.logicq.mlm.dao.wallet.IWalletStmntDAO;
import com.logicq.mlm.model.admin.FeeSetup;
import com.logicq.mlm.model.profile.WalletDetails;
import com.logicq.mlm.model.wallet.WalletStatement;
import com.logicq.mlm.vo.PaymentVO;

@Service
@Transactional
public class WalletStmntService  implements IWalletStmntService{

	@Autowired
	IWalletStmntDAO walletStmntDAO;
	
	@Autowired
	IWalletDetailsDAO walletDetailsDAO;
	
	
	@Override
	public WalletStatement fetchWalletStmnt(WalletStatement walletStatement) throws Exception {
		return walletStmntDAO.fetchWalletStmnt(walletStatement);
	}


	@Override
	public void createWallet(WalletDetails walletdetails) {
		walletDetailsDAO.createWallet(walletdetails);
	}


	@Override
	public WalletDetails fetchWalletDetails(WalletDetails walletdetails) {
		return walletDetailsDAO.fetchWalletDetails(walletdetails);
	}


	@Override
	public void addWalletStmnt(WalletStatement walletStatement) {
		walletStmntDAO.addWalletStmnt(walletStatement);
	}


	@Override
	public void updateWalletStmnt(WalletStatement walletStatement) throws Exception {
		walletStmntDAO.updateWalletStmnt(walletStatement);
	}


	@Override
	public void updateWalletStatementAccordingToFee(FeeSetup fee, WalletStatement walletStatement) {
		walletStatement.setCurrentbalance(walletStatement.getCurrentbalance().add(fee.getAmount()));
		walletStatement.setMaxencashable(walletStatement.getMaxencashable().add(fee.getAmount()));
		walletStatement.setPayout(walletStatement.getPayout().add(fee.getAmount()));
		walletStatement.setWalletlastupdate(new Date());
		walletStmntDAO.updateWalletStmnt(walletStatement);
	}


	@Override
	public WalletStatement fetchWalletStmnt(String walletid) throws Exception {
		return walletStmntDAO.fetchWalletStmnt(walletid);
	}

	@Override
	public WalletStatement fetchWalletStmntFromWalletNumber(String walletnumber) throws Exception {
		return walletStmntDAO.fetchWalletStmntFromWalletNumber(walletnumber);
	}



	

}
