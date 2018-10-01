package com.zycus.repository;



import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.zycus.entity.AdminUser;

public interface UserRepository extends CrudRepository<AdminUser, Integer> {
	
	@Query(value="Select usr from AdminUser usr where usr.email=:emailId AND usr.password=:pass")
	AdminUser validate(@Param("emailId")String user,@Param("pass")String pass);
	

}
