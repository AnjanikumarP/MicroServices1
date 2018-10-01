package com.zycus.repository;

import org.springframework.data.repository.CrudRepository;

import com.zycus.entity.Account;

public interface AccountRepository extends CrudRepository<Account,String> {

}
