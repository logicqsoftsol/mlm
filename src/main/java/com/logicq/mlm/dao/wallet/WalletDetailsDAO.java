package com.logicq.mlm.dao.wallet;

import org.springframework.stereotype.Repository;

import com.logicq.mlm.dao.AbstractDAO;
import com.logicq.mlm.model.profile.WalletDetails;

@Repository
public class WalletDetailsDAO extends AbstractDAO<WalletDetails>  implements IWalletDetailsDAO{

	@Override
	public void createWallet(WalletDetails walletdetails) {
		save(walletdetails);
		
	}

	@Override
	public WalletDetails fetchWalletDetails(WalletDetails walletdetails) {
		StringBuilder query=new StringBuilder();
		if(null!=walletdetails.getUserprofile().getId()){
		query.append(" from WalletDetails wd where wd.userprofile.id="+walletdetails.getUserprofile().getId());
		}else{
			query.append(" from WalletDetails wd where wd.walletnumber= '"+walletdetails.getWalletnumber()+"'");
		}
		return (WalletDetails) execcuteQuery(query.toString()).get(0);
	}



	public WalletDetails getWalletDetails(String  walletid) {
		StringBuilder query=new StringBuilder();
			query.append(" from WalletDetails wd where wd.walletid= '"+walletid+"'");
		
		return (WalletDetails) execcuteQueryForUnique(query.toString());
	}
	
	@Override
	public void updateWalletDetails(WalletDetails walletdetails) {
		update(walletdetails);
	}

}
