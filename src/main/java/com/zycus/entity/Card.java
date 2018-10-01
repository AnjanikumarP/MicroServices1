package com.zycus.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.zycus.entity.Types.CardType;

@Entity
public class Card {
	
	@Id
	private String cardNo;
	
	private CardType cardType;
	
	@ManyToOne
	@JoinColumn(name="accountNo")
	private Account account;
	
	
	private double cardLimit;
	private long cardExpiry;
	
	
	
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	public double getCardLimit() {
		return cardLimit;
	}
	public void setCardLimit(double cardLimit) {
		this.cardLimit = cardLimit;
	}
	public long getCardExpiry() {
		return cardExpiry;
	}
	public void setCardExpiry(long cardExpiry) {
		this.cardExpiry = cardExpiry;
	}
	public CardType getCardType() {
		return cardType;
	}
	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}
	
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	

}
