package com.zycus.services;

import javax.servlet.http.HttpServletRequest;

public interface UserServices {
	
	boolean validate(String user,String pass,HttpServletRequest request);

}
