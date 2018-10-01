package com.zycus.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages="com.zycus.repository")
@ComponentScan(basePackages={"com.zycus.services","com.zycus.controller"})
@EntityScan(basePackages="com.zycus.entity")
@EnableFeignClients("com.zycus.proxy")
public class Launcher {
	
	public static void main(String[] args) {
		SpringApplication.run(Launcher.class, args);//will start the tomcat server after checking it has done all setup
	}

}
