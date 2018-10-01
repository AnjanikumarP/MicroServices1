package com.zycus.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zycus.entity.Account;
import com.zycus.entity.AuditLog;
import com.zycus.proxy.AuditProxy;

@Service
public class TransactionLogging implements Logging {
	private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

	@Autowired
	private AuditProxy proxy;
	
	public void UpdateLog(HttpServletRequest request,String message,String account)
	{
		AuditLog audit = new AuditLog();
		audit.setAccount(account);
		
		try {
			audit.setDate( new SimpleDateFormat("dd/MM/yyyy").parse(dtf.format(LocalDateTime.now())));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		audit.setLoggedUser((String) request.getSession().getAttribute("authUser"));
	    audit.setId(System.currentTimeMillis()%100000000);
	    audit.setMessage(message);
	    
	    System.out.println(audit);
	    System.out.println("i am in transaction");
	    
	    if(proxy == null) {
	    	System.out.println("Proxy is null");
	    }
	    else {
	    	System.out.println("Proxy is not null");
	    	proxy.updateAuditShow(audit);
	    }
	}
}
