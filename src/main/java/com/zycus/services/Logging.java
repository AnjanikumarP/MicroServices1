package com.zycus.services;

import javax.servlet.http.HttpServletRequest;

public interface Logging {
	public void UpdateLog(HttpServletRequest request,String message,String account);
}
