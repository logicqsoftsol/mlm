package com.logicq.mlm.common.helper;

import java.math.BigDecimal;
import java.util.Date;

import com.logicq.mlm.model.admin.TransactionDetails;
import com.logicq.mlm.model.wallet.WalletStatement;
import com.logicq.mlm.vo.EncashVO;
import com.logicq.mlm.vo.LoginVO;
import com.logicq.mlm.vo.PaymentVO;
import com.logicq.mlm.vo.TxnRollBackVO;
import com.logicq.mlm.vo.WalletStmntVO;

public class WalletAmountCalculator {
	
	
	public static WalletStatement calculateCurrentBalance(WalletStatement walletstmnt){
		//Fetch amount according to level
	  BigDecimal currentbalance= walletstmnt.getCurrentbalance().add(new BigDecimal(60));
	  BigDecimal maxencashbalance= walletstmnt.getMaxencashable().add(new BigDecimal(60));
	  BigDecimal payoutbalance= walletstmnt.getPayout().add(new BigDecimal(60));
	  walletstmnt.setCurrentbalance(currentbalance);
	  walletstmnt.setMaxencashable(maxencashbalance);	
	  walletstmnt.setPayout(payoutbalance);
	  walletstmnt.setWalletlastupdate(new Date());
	  return walletstmnt;
	}

	public static WalletStatement calculateCurrentBalanceAfterEncashed(WalletStatement walletstmnt,BigDecimal enasedamount){
	  walletstmnt.setEncashedAmount(walletstmnt.getEncashedAmount().add(enasedamount));
	  walletstmnt.setWalletlastupdate(new Date());
	  return walletstmnt;
	}

	public static TransactionDetails populateTransactionDetails(PaymentVO paymentDetails, String walletid,String firstName,String lastName) {
		TransactionDetails transactionDetail = new TransactionDetails();
		transactionDetail.setAmount(paymentDetails.getAmount());
		transactionDetail.setDescription(paymentDetails.getDescription());
		transactionDetail.setModeoftxn(paymentDetails.getPaymentmode());
		transactionDetail.setTxndate(new Date());
		transactionDetail.setWalletid(walletid);
		transactionDetail.setTxnfor(firstName + " " + lastName);
		transactionDetail.setRefrenceno(paymentDetails.getRefrencenumber());
		transactionDetail.setTxnNumber(StringFormatHelper.randomString());
		return transactionDetail;
	}
	
	
	public static TransactionDetails populateTransactionDetails(TxnRollBackVO txnrollbackdetails, String walletid,String firstName,String lastName) {
		TransactionDetails transactionDetail = new TransactionDetails();
		transactionDetail.setAmount(txnrollbackdetails.getRollbackAmount());
		transactionDetail.setDescription(txnrollbackdetails.getRollbackReasone());
		transactionDetail.setModeoftxn("ONLINE");
		transactionDetail.setTxndate(new Date());
		transactionDetail.setWalletid(walletid);
		transactionDetail.setTxnfor(firstName + " " + lastName);
		transactionDetail.setRefrenceno(txnrollbackdetails.getTxnRefrenceNumber());
		transactionDetail.setTxnNumber(StringFormatHelper.randomString());
		return transactionDetail;
	}
	
	public static TransactionDetails populateTransactionDetails(EncashVO encahedDetails, String walletid,String firstName,String lastName) {
		TransactionDetails transactionDetail = new TransactionDetails();
		transactionDetail.setAmount(encahedDetails.getEncashamount());
		transactionDetail.setDescription(encahedDetails.getRefrencenumber());
		transactionDetail.setModeoftxn(encahedDetails.getPaymentmode());
		transactionDetail.setTxndate(new Date());
		transactionDetail.setWalletid(walletid);
		transactionDetail.setTxnfor(firstName + " " + lastName);
		transactionDetail.setRefrenceno(encahedDetails.getRefrencenumber());
		transactionDetail.setTxnNumber(StringFormatHelper.randomString());
		return transactionDetail;
	}
	
	public static WalletStmntVO populateWalletStmnt(WalletStatement walletstmnt){
		WalletStmntVO walletstmntvo=new WalletStmntVO();
		walletstmntvo.setCurrentbalance(walletstmnt.getCurrentbalance());
		walletstmntvo.setEncashedAmount(walletstmnt.getEncashedAmount());
		walletstmntvo.setMaxencashable(walletstmnt.getMaxencashable());
		walletstmntvo.setPayout(walletstmnt.getPayout());
		return walletstmntvo;
	}
}
