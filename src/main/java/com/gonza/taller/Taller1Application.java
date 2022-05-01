package com.gonza.taller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import com.gonza.taller.model.auth.UserMine;
import com.gonza.taller.model.prod.Location;
import com.gonza.taller.model.prod.Product;
import com.gonza.taller.model.prod.Productcategory;
import com.gonza.taller.model.prod.Productcosthistory;
import com.gonza.taller.model.prod.Productinventory;
import com.gonza.taller.model.prod.Productsubcategory;
import com.gonza.taller.repository.ProductCategoryRepository;
import com.gonza.taller.repository.ProductSubCategoryRepository;
import com.gonza.taller.repository.UserRepositoryI;
import com.gonza.taller.service.LocationService;
import com.gonza.taller.service.LocationServiceImp;
import com.gonza.taller.service.ProductServiceImp;
import com.gonza.taller.service.ProductcosthistoryService;
import com.gonza.taller.service.ProductcosthistoryServiceImp;
import com.gonza.taller.service.ProductinventoryService;
import com.gonza.taller.service.ProductinventoryServiceImp;

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
	public CommandLineRunner dummy(UserRepositoryI userRepository, ProductServiceImp productService,
			ProductCategoryRepository productcategoryRepository, ProductSubCategoryRepository productsubcategoryRepository,
			LocationServiceImp locationService, ProductcosthistoryServiceImp productcosthistoryService,
			ProductinventoryServiceImp productinventoryService) {
		
		return(args)->{

			//ADMIN
			
			UserMine admin = new UserMine();
			admin.setUserId(1);
			admin.setUsername("admin");
			admin.setPassword("{noop}admin");
			admin.setType(UserMine.ADMIN);
			userRepository.save(admin);
			
			Product product = new Product();
			product.setProductnumber("1010");
			product.setWeight(1);
			product.setSize(2);
			product.setName("RTX 3090");
			
			
			product.setSellstartdate(LocalDate.of(2021, 10, 24));
			product.setSellenddate(LocalDate.of(2021, 11, 24));
			
			Productcategory pc = new Productcategory();
			Productsubcategory psc = new Productsubcategory();
			
			pc.setProductcategoryid(1);
			psc.setProductsubcategoryid(1);
			
			pc.setName("Tarjetas Graficas");
			psc.setName("Turing");
			
			product.setProductsubcategory(psc);
			psc.setProductcategory(pc);
			
			
			productcategoryRepository.save(pc);
			productsubcategoryRepository.save(psc);
			productService.save(product,1,1);
			
			
			
			Location location = new Location();
			
			location.setAvailability(new BigDecimal("2"));
			location.setCostrate(new BigDecimal("0.5"));
			location.setLocationid(1);
			location.setName("Ibiza");
		
			location.setModifieddate(LocalDate.of(2021, 12, 21));
			
			locationService.save(location);
			
			
			
			
			
			
			//USER
			
			UserMine user = new UserMine();
			user.setUserId(2);
			user.setUsername("user");
			user.setPassword("{noop}user");
			user.setType(UserMine.USER);
			userRepository.save(user);
			
			Productcosthistory pch = new Productcosthistory(); 
			pch.setStandardcost(new BigDecimal("3"));
			
			pch.setEnddate(LocalDate.of(2021, 12, 21));
			pch.setProduct(product);
			productcosthistoryService.save(pch, 1);
			
			
			Productinventory productinventory = new Productinventory();
			productinventory.setLocation(location);
			productinventory.setProduct(product);
			productinventory.setQuantity(5);
			productinventoryService.save(productinventory,1,1);
			
			
			
			

			
			
			System.out.println("LOS GUARDO");
			
		};
	}

} //end of class
