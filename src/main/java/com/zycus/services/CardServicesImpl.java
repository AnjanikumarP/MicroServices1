package com.zycus.services;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.zycus.entity.Card;
import com.zycus.repository.CardRepository;

@Service
@Transactional
public class CardServicesImpl implements CardServices {

	@Autowired
	TransactionLogging tlog;
	
	
	@Autowired
	private CardRepository cardRepository;
	
	@CacheEvict(value ="cards.cache",allEntries=true)
	public void issueCard(HttpServletRequest request,Card card) {
		// TODO Auto-generated method stub
		
		cardRepository.save(card);
		String msg = "Card is created for"+card.getAccount().getHolderName()+" with Account No: "+card.getAccount().getAccountNo();
		tlog.UpdateLog(request, msg, card.getAccount().getAccountNo());
	}

	@CacheEvict(value ="cards.cache",allEntries=true)
	public void cancelCard(String cardNo) {
		// TODO Auto-generated method stub
		 Card card = cardRepository.findById(cardNo).get();
	    cardRepository.delete(card);
	}

	@CacheEvict(value ="cards.cache",allEntries=true)
	public void updateCardLimit(String cardNo,double newLimit) {
		// TODO Auto-generated method stub
		Card card = cardRepository.findById(cardNo).get();
		card.setCardLimit(newLimit);
		cardRepository.save(card);
	}

	
	@Cacheable(value="cards.cache")
	public Iterable<Card> fetchAllCard(String accountNo) {
		// TODO Auto-generated method stub
		return cardRepository.findByAccountNo(accountNo);
				
	}

}
