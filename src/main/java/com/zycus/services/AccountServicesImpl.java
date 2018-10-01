package com.zycus.services;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.zycus.entity.Account;
import com.zycus.entity.AccountStatus;
import com.zycus.repository.AccountRepository;


@Service
@Transactional
public class AccountServicesImpl implements AccountServices {

	
	@Autowired
	private AccountRepository accountRepo;
	
	@Autowired
	TransactionLogging tlog;
	
	@CacheEvict(value="account.cache", allEntries=true)
	public void openAccount(HttpServletRequest request,Account account) {
		// TODO Auto-generated method stub
		
		System.out.println(account);
		System.out.println(account.getAccountNo());
		accountRepo.save(account);
		String message = "Account created with Amount:"+account.getBalance();

		System.out.println("i am in acoount service");
		tlog.UpdateLog(request, message, account.getAccountNo());
	}

	
	@CacheEvict(value="account.cache", allEntries=true)
	public String closeAccount(String acNo) {
		// TODO Auto-generated method stub
		Account account =accountRepo.findById(acNo).get();
		
		if(account== null )
		{
		return "NO Account found by the Supplied Number";
		
		}
	      account.setStatus(AccountStatus.Inactive);
		accountRepo.save(account);
		return "Account Successfully deleted";
	}

	
	@Cacheable(value="account.cache")
	public Iterable<Account> getAllAccounts()
	{
		
		return accountRepo.findAll();
	}
	

	
	
	public Account getAccount(String acno) {
		// TODO Auto-generated method stub
		
		return accountRepo.findById(acno).get();
		
	}
	
	
	

	public String withdraw(String acno, double amount) {
		// TODO Auto-generated method stub
		Account account = accountRepo.findById(acno).get();
		if(account==null)
		{
			return "No account with Account Number: "+acno;
		}
		double temp= account.getBalance()-amount;
		if(temp>1000)
		{
			account.setBalance(temp);
			return "Withdraw Successful from Account: "+account.getAccountNo();
		}
		else
			return "Cannot Perform Withdraw,Your Balance: "+account.getBalance();
		
			
	}

	public String deposit(String acno, double amount) {
		// TODO Auto-generated method stub
		Account account = accountRepo.findById(acno).get();
		if(account==null)
		{
			return "No account with Account Number: "+acno;
		}
		account.setBalance(account.getBalance()+amount);
		return amount+" Successfully added to Account: "+acno;
	}

	public String transfer(String toAno, String fromAno, double amount) {
		// TODO Auto-generated method stub
		Account toaccount = accountRepo.findById(toAno).get();
		Account fromaccount = accountRepo.findById(fromAno).get();
		if(toaccount==null || toaccount.getStatus()==AccountStatus.Inactive)
		{
			return "No active account with Account Number: "+toAno;
		}else if(fromaccount==null)
		{
			return "No account with Account Number: "+fromAno;
		}
		double temp= fromaccount.getBalance()-amount;
		if(temp>1000)
		{
			fromaccount.setBalance(temp);
			toaccount.setBalance(toaccount.getBalance()+amount);
			return "Transfer Successfully done";
			
		}
		return "Transfer Failed";
		
		
		
	}


	public Iterable<Account> fetchAllAccount() {
		// TODO Auto-generated method stub
		 return accountRepo.findAll();
		
	}
	

}
