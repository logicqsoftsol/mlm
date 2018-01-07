package com.logicq.mlm.dao.wallet;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.logicq.mlm.dao.AbstractDAO;
import com.logicq.mlm.model.wallet.WalletStatement;

@Repository
public class WalletStmntDAO extends AbstractDAO<WalletStatement> implements IWalletStmntDAO {

	@Override
	public WalletStatement fetchWalletStmnt(WalletStatement walletStatement) throws Exception {
		StringBuilder query = new StringBuilder();
		query.append(" from WalletStatement ws where ws.walletid='" + walletStatement.getWalletid() + "'");
		List<WalletStatement> walletstmntlist = (List<WalletStatement>) execcuteQuery(query.toString());
		if (null != walletstmntlist && !walletstmntlist.isEmpty()) {
			return walletstmntlist.get(0);
		}else{
			walletStatement.setCurrentbalance(new BigDecimal(0.0));
			walletStatement.setMaxencashable(new BigDecimal(0.0));
			walletStatement.setPayout(new BigDecimal(0.0));
		}
		return walletStatement;

	}

	@Override
	public void addWalletStmnt(WalletStatement walletStatement) {
		save(walletStatement);
	}

	@Override
	public void updateWalletStmnt(WalletStatement walletStatement){
		update(walletStatement);
	}

	@Override
	public WalletStatement fetchWalletStmnt(String walletid) throws Exception {
		StringBuilder query = new StringBuilder();
		query.append(" from WalletStatement ws where ws.walletid='" +walletid+ "'");
		return  execcuteQueryForUnique(query.toString());
	}

	@Override
	public WalletStatement fetchWalletStmntFromWalletNumber(String walletnumber) throws Exception {
		StringBuilder query = new StringBuilder();
		query.append(" from WalletStatement ws where ws.wallet.walletnumber='" +walletnumber+ "'");
		return  execcuteQueryForUnique(query.toString());
	}
	
	

}
