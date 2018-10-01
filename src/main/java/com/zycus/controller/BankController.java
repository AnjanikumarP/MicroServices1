package com.zycus.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zycus.entity.Account;
import com.zycus.entity.AccountStatus;
import com.zycus.entity.Card;
import com.zycus.entity.Types.AccountType;
import com.zycus.entity.Types.CardType;
import com.zycus.services.AccountServices;
import com.zycus.services.CardServices;
import com.zycus.services.UserServices;

@Controller
@RequestMapping("/bank/*")
public class BankController {
	
	@Autowired
	private AccountServices accountServices;
	
	@Autowired
	private CardServices cardServices;
	
	
	@Autowired
	private UserServices userServices;
	
	
	@RequestMapping(value="/login1",method=RequestMethod.GET)
	public String login()
	{
		System.out.println("Hey i came into the get");
		return "login";
	}

	
	@RequestMapping(value="/login2",method=RequestMethod.POST,consumes="application/json",produces="plain/text")
	public @ResponseBody String login2(@RequestBody Map<String, String> userInfo,HttpServletRequest request)
	{
		
		
		if(userServices.validate(userInfo.get("email"), userInfo.get("password"),request)){
			
			return "success";	
		}
	//	System.out.println("coming out of Loop");
		return "fail";
		
	}

	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logout(HttpServletRequest request)
	{
		request.getSession().invalidate();
		
		return "login";
	}

	@RequestMapping(value="/admin",method=RequestMethod.GET)
	public String welcome(Model model,HttpServletRequest request)
	{
		if(isSessionActive(request))
		{
			System.out.println(request.getSession().getAttribute("authUser"));
		    model.addAttribute("adminName",request.getSession().getAttribute("authUser"));
		    model.addAttribute("allAccounts",accountServices.fetchAllAccount());
			return "Admin";
		}else {
			return "login";
		}
	}
	
	
	
	@RequestMapping(value="/newAccount",method=RequestMethod.POST,
			consumes="application/json",produces="plain/text")
	public @ResponseBody String addAccount(@RequestBody Map<String,String> AccountInfo, HttpServletRequest request)
	{
		Account account =new Account();
		account.setAccountNo(String.valueOf(System.currentTimeMillis()));
		if(AccountInfo.get("accountType").equalsIgnoreCase("Saving"))
		{
			account.setAccountType(AccountType.Savings);
		}else 
		{
			account.setAccountType(AccountType.Current);
		}
		Double bal = Double.parseDouble(AccountInfo.get("balance"));
		account.setBalance(bal);
		account.setStatus(AccountStatus.Active);
		account.setOpeningDate(System.currentTimeMillis());
		account.setHolderName(AccountInfo.get("holderName"));
		accountServices.openAccount(request,account);
		return "successAccount";
	}
	

	
	
	@RequestMapping(value="/newcard",method=RequestMethod.POST,
			consumes="application/json",produces="plain/text")
	public @ResponseBody String newCard(@RequestBody Map<String, String> cardInfo,HttpServletRequest request)
	{
		
		System.out.println("Hello sir your card is being processed");
	    Card card = new  Card();
	    card.setCardNo(String.valueOf(System.currentTimeMillis()/5));
	    if(cardInfo.get("cardType").equalsIgnoreCase("Credit"))
	    {
	    card.setCardType(CardType.Credit);
	    }
	    else if(cardInfo.get("cardType").equalsIgnoreCase("Debit"))
	    	{
	    	card.setCardType(CardType.Debit);
	    	}
	    else 
	    	return null;
	    card.setCardLimit(Double.parseDouble(cardInfo.get("maximumLimit")));			
	    card.setCardExpiry((System.currentTimeMillis()+94608000000L));
	    Account account = accountServices.getAccount(cardInfo.get("accountNo"));
	    card.setAccount(account);
	    cardServices.issueCard(request,card);
	    return cardInfo.get("accountNo");
	}
	
	@RequestMapping(value="/cancelCard",method=RequestMethod.POST,
			consumes="application/json",produces="plain/text")
	public String cancelCard(String cardNo)
	{
		cardServices.cancelCard(cardNo);
		return "Card Successfully cancelled";
	}
	
	
	
	
	@RequestMapping(value="/fetchCards/{accountNumber}",method=RequestMethod.GET)
	public String FetchUserCards(@PathVariable("accountNumber") String accountNo,Model model,HttpServletRequest request)
	{
		
		if(isSessionActive(request))
		{
			System.out.println(request.getSession().getAttribute("authUser"));
		    model.addAttribute("adminName",request.getSession().getAttribute("authUser"));
		    model.addAttribute("allCards",cardServices.fetchAllCard(accountNo));
		    model.addAttribute("accountNo",accountNo);
			return "Cards";
		}else {
			return "login";
		}
	}
	
	
	
	private boolean isSessionActive(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		
		if(session!=null && session.getAttribute("authUser")!=null){
			return true;
		}
		else
			return false;
		
	}
	
	//Unused methods
	
	
	
	@RequestMapping(value="/updateCard",method=RequestMethod.POST,
			consumes="application/json",produces="plain/text")
	public String updateCardLimit(String cardNo,double newLimit)
	{
		cardServices.updateCardLimit(cardNo, newLimit);
		return "Card Limit Updated to "+newLimit;
	}
	
	
	@RequestMapping(value="/closeAccount",method=RequestMethod.GET,
			consumes="application/json",produces="plain/text")
	public String closeAccount(String accountNo)
	{
		return accountServices.closeAccount(accountNo);	
	}
	
	
	
	
	@RequestMapping(value="/withdraw",method=RequestMethod.POST,
			consumes="application/json",produces="plain/text")
	public String withdraw(String accountNo,double amount)
	{
		return accountServices.withdraw(accountNo, amount);
	}
	
	
	
	
	
	@RequestMapping(value="/deposit",method= RequestMethod.POST,
			consumes="application/json",produces="plain/text")
	public String deposit(String accountNo,double amount)
	{
		return accountServices.deposit(accountNo, amount);
	}
	
	
	
	
	@RequestMapping(value="/transfer",method=RequestMethod.POST,
			consumes="application/json",produces="plain/text")
	public String transfer(String fromAno,String toAno,double amount)
	{
		return accountServices.transfer(toAno, fromAno, amount);
	}
	
}
