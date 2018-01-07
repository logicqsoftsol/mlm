package com.logicq.mlm.service.wallet;

import com.logicq.mlm.model.profile.WalletDetails;

public interface IWalletDetailsService {
	

	public void createWallet(WalletDetails walletdetails);
	
	public WalletDetails fetchWalletDetails(WalletDetails walletdetails);

	public void updateWalletDetails(WalletDetails walletdetails);
	
	public WalletDetails getWalletDetails(String walletid);

}
