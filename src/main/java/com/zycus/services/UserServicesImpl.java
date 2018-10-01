package com.zycus.services;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zycus.entity.AdminUser;
import com.zycus.repository.UserRepository;

@Service
@Transactional
public class UserServicesImpl implements UserServices {

	@Autowired
	private UserRepository userRepo;
	
	public boolean validate(String user, String pass,HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		AdminUser authUser =userRepo.validate(user, pass);
		if(authUser!=null){
		
			HttpSession session = request.getSession();	
			session.setAttribute("authUser", authUser.getName());
				return true;
			}

	
		return false;
		
			
		}


}
