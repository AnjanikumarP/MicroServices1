package com.zycus.services;

import javax.servlet.http.HttpServletRequest;

import com.zycus.entity.Card;

public interface CardServices {
	
	void issueCard(HttpServletRequest request,Card card);
	void cancelCard(String cardNo);
	void updateCardLimit(String cardNo,double newLimit);
	Iterable<Card> fetchAllCard(String accountNo);

}
