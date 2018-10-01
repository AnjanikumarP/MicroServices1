package com.zycus.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.zycus.entity.Types.AccountType;

@Entity
public class Account {

	@Id
	private String accountNo;
	
	private String holderName;
	private AccountType accountType;
	private double balance;
	private long openingDate;
	private AccountStatus status;
	
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="account")
	private Set<Card> cards;
	
	
	
	
	public AccountStatus getStatus() {
		return status;
	}
	public void setStatus(AccountStatus status) {
		this.status = status;
	}
	public String  getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getHolderName() {
		return holderName;
	}
	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}
	public AccountType getAccountType() {
		return accountType;
	}
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	
	@Override
	public String toString() {
		return "Account [accountNo=" + accountNo + ", holderName=" + holderName + ", accountType=" + accountType
				+ ", balance=" + balance + ", openingDate=" + openingDate + ", status=" + status + ", cards=" + cards
				+ "]";
	}
	public long getOpeningDate() {
		return openingDate;
	}
	public void setOpeningDate(long openingDate) {
		this.openingDate = openingDate;
	}
	
	
	
 
	
}
