package com.zycus.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.zycus.entity.Card;

public interface CardRepository extends CrudRepository<Card, String> {

	@Query(value="Select c from Card c where c.account.accountNo=:AccountNo")
	Iterable<Card> findByAccountNo(@Param("AccountNo")String AccountNo);
	
	

}
