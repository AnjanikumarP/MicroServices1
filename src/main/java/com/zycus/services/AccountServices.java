package com.zycus.services;

import javax.servlet.http.HttpServletRequest;

import com.zycus.entity.Account;

public interface AccountServices {

	
	void openAccount(HttpServletRequest request,Account account);
	String closeAccount(String acno);
	Account getAccount(String acno);
	String withdraw(String acno,double amount);
	String deposit(String acno,double amount);
	String transfer(String toAno,String fromAno,double amount);
	Iterable<Account> fetchAllAccount();
	
	
}
