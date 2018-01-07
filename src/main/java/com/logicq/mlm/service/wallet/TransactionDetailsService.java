package com.logicq.mlm.service.wallet;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.logicq.mlm.dao.wallet.ITransactionDetailsDAO;
import com.logicq.mlm.dao.wallet.IWalletDetailsDAO;
import com.logicq.mlm.model.admin.TransactionDetails;
import com.logicq.mlm.model.profile.UserProfile;
import com.logicq.mlm.model.profile.WalletDetails;
import com.logicq.mlm.service.security.UserService;
import com.logicq.mlm.service.user.IUserService;

@Service
@Transactional
public class TransactionDetailsService implements ITransactionDetailsService {

	@Autowired
	ITransactionDetailsDAO transactionDetailsDAO;
	
	@Autowired
	IUserService userService;
	
	@Autowired
	IWalletStmntService walletStatementservice;
	
	@Override
	public void save(TransactionDetails transactionDetails) {
		transactionDetailsDAO.saveTransactionDetails(transactionDetails);
		
	}

	@Override
	public List<TransactionDetails> getTransactionDetails(String username) {
		UserProfile userprofile=userService.fetchUserAccordingToUserName(username);
		return transactionDetailsDAO.getTransactionDetails(userprofile.getWalletdetails().getWalletid());
	}

	@Override
	public List<TransactionDetails> getTransactionDetails(String username,String txnType) {
		UserProfile userprofile=userService.fetchUserAccordingToUserName(username);
		return transactionDetailsDAO.getTransactionDetails(userprofile.getWalletdetails().getWalletid(),txnType);
	}
	
	@Override
	public List<TransactionDetails> getTransactionDetailsAccordingToRefrenceNumber(String refrenceNumber) {
		return transactionDetailsDAO.getTransactionDetailsAccordingToRefrenceNumber(refrenceNumber);
	}

	@Override
	public void deleteTransaction(TransactionDetails txn) {
		transactionDetailsDAO.deleteTransaction(txn);
		
	}
	
	

}
