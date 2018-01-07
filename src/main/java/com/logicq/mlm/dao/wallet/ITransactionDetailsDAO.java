package com.logicq.mlm.dao.wallet;

import java.util.List;

import com.logicq.mlm.model.admin.TransactionDetails;

public interface ITransactionDetailsDAO {

	void saveTransactionDetails(TransactionDetails transactionDetails);
	List<TransactionDetails> getTransactionDetails(String walletid);
	List<TransactionDetails> getTransactionDetailsAccordingToRefrenceNumber(String refrenceNumber);
	void deleteTransaction(TransactionDetails txnDetails);
	List<TransactionDetails> getTransactionDetails(String walletid,String txnType);
}
