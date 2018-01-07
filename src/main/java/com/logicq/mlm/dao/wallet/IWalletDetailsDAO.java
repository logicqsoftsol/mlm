package com.logicq.mlm.dao.wallet;

import com.logicq.mlm.model.profile.WalletDetails;

public interface IWalletDetailsDAO {
	
	public void createWallet(WalletDetails walletdetails);
	
	public WalletDetails fetchWalletDetails(WalletDetails walletdetails);

	public void updateWalletDetails(WalletDetails walletdetails);
	public WalletDetails getWalletDetails(String  walletid);
}
