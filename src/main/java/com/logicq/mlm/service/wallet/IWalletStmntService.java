package com.logicq.mlm.service.wallet;

import com.logicq.mlm.model.admin.FeeSetup;
import com.logicq.mlm.model.profile.WalletDetails;
import com.logicq.mlm.model.wallet.WalletStatement;

public interface IWalletStmntService {

	WalletStatement fetchWalletStmnt(WalletStatement walletStatement) throws Exception;
	
	void addWalletStmnt(WalletStatement walletStatement);
	
	void updateWalletStmnt(WalletStatement walletStatement) throws Exception;
	
     void createWallet(WalletDetails walletdetails);
	
	 WalletDetails fetchWalletDetails(WalletDetails walletdetails);
	 void updateWalletStatementAccordingToFee(FeeSetup fee,WalletStatement walletStatement);
	  WalletStatement fetchWalletStmnt(String walletid) throws Exception;
	  WalletStatement fetchWalletStmntFromWalletNumber(String walletnumber)throws Exception;
	   
	
}
