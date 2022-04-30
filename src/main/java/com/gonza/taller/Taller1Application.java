package com.gonza.taller;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import com.gonza.taller.model.auth.UserMine;
import com.gonza.taller.repository.UserRepositoryI;

@SpringBootApplication
@EntityScan(basePackages = {"com.gonza.taller.authentication","com.gonza.taller.model.*"})
@ComponentScan(basePackages = {"com.gonza.taller.authentication","com.gonza.taller.repository","com.gonza.taller.service","com.gonza.taller.controller"})
public class Taller1Application {
	
	
	
	@Bean
	public Java8TimeDialect java8TimeDialect() {
		return new Java8TimeDialect();
	}

	public static void main(String[] args) {
		SpringApplication.run(Taller1Application.class, args);
	}
	
	@Bean
	public CommandLineRunner dummy(UserRepositoryI ur) {
		
		return(args)->{
			
			UserMine admin = new UserMine();
			admin.setUserId(1);
			admin.setUsername("admin");
			admin.setPassword("{noop}admin");
			admin.setType(UserMine.ADMIN);
			ur.save(admin);
			
			UserMine user = new UserMine();
			user.setUserId(2);
			user.setUsername("user");
			user.setPassword("{noop}user");
			user.setType(UserMine.USER);
			ur.save(user);
			
			System.out.println("LOS GUARDO");
			
		};
	}

} //end of class
