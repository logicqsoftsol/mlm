package com.logicq.mlm.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.logicq.mlm.model.admin.TransactionDetails;

public class TxnRollBackVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4916748123302918893L;
	private String creditorFirstName;
	private String crediotrLastName;
	private String payeeFirstName;
	private String payeeLastName;
	private BigDecimal rollbackAmount;
	private String creditorGpmIdNo;
	private String payeeGpmIdNo;
	private BigDecimal payeeCurrentBalance;
	private Date txnDate;
	private String txnRefrenceNumber;
	private String txnDescription;
	private String rollbackReasone;
	private List<TransactionDetails> transactionDetails;
	
	public String getCreditorFirstName() {
		return creditorFirstName;
	}
	public void setCreditorFirstName(String creditorFirstName) {
		this.creditorFirstName = creditorFirstName;
	}
	public String getCrediotrLastName() {
		return crediotrLastName;
	}
	public void setCrediotrLastName(String crediotrLastName) {
		this.crediotrLastName = crediotrLastName;
	}
	public String getPayeeFirstName() {
		return payeeFirstName;
	}
	public void setPayeeFirstName(String payeeFirstName) {
		this.payeeFirstName = payeeFirstName;
	}
	public String getPayeeLastName() {
		return payeeLastName;
	}
	public void setPayeeLastName(String payeeLastName) {
		this.payeeLastName = payeeLastName;
	}
	public BigDecimal getRollbackAmount() {
		return rollbackAmount;
	}
	public void setRollbackAmount(BigDecimal rollbackAmount) {
		this.rollbackAmount = rollbackAmount;
	}
	public String getCreditorGpmIdNo() {
		return creditorGpmIdNo;
	}
	public void setCreditorGpmIdNo(String creditorGpmIdNo) {
		this.creditorGpmIdNo = creditorGpmIdNo;
	}
	public String getPayeeGpmIdNo() {
		return payeeGpmIdNo;
	}
	public void setPayeeGpmIdNo(String payeeGpmIdNo) {
		this.payeeGpmIdNo = payeeGpmIdNo;
	}
	public BigDecimal getPayeeCurrentBalance() {
		return payeeCurrentBalance;
	}
	public void setPayeeCurrentBalance(BigDecimal payeeCurrentBalance) {
		this.payeeCurrentBalance = payeeCurrentBalance;
	}
	public Date getTxnDate() {
		return txnDate;
	}
	public void setTxnDate(Date txnDate) {
		this.txnDate = txnDate;
	}
	public String getTxnRefrenceNumber() {
		return txnRefrenceNumber;
	}
	public void setTxnRefrenceNumber(String txnRefrenceNumber) {
		this.txnRefrenceNumber = txnRefrenceNumber;
	}
	public String getTxnDescription() {
		return txnDescription;
	}
	public void setTxnDescription(String txnDescription) {
		this.txnDescription = txnDescription;
	}
	public String getRollbackReasone() {
		return rollbackReasone;
	}
	public void setRollbackReasone(String rollbackReasone) {
		this.rollbackReasone = rollbackReasone;
	}
	
	
	
	public List<TransactionDetails> getTransactionDetails() {
		return transactionDetails;
	}
	public void setTransactionDetails(List<TransactionDetails> transactionDetails) {
		this.transactionDetails = transactionDetails;
	}
	
	
	@Override
	public String toString() {
		return "TxnRollBackVO [creditorFirstName=" + creditorFirstName + ", crediotrLastName=" + crediotrLastName
				+ ", payeeFirstName=" + payeeFirstName + ", payeeLastName=" + payeeLastName + ", rollbackAmount="
				+ rollbackAmount + ", creditorGpmIdNo=" + creditorGpmIdNo + ", payeeGpmIdNo=" + payeeGpmIdNo
				+ ", payeeCurrentBalance=" + payeeCurrentBalance + ", txnDate=" + txnDate + ", txnRefrenceNumber="
				+ txnRefrenceNumber + ", txnDescription=" + txnDescription + ", rollbackReasone=" + rollbackReasone
				+ "]";
	}
	
}
