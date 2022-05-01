package com.gonza.taller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.gonza.taller.model.prod.Productsubcategory;
import com.gonza.taller.repository.ProductCategoryRepository;
import com.gonza.taller.repository.ProductSubCategoryRepository;
import com.gonza.taller.repository.UserRepositoryI;
import com.gonza.taller.service.LocationService;
import com.gonza.taller.service.ProductServiceImp;

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
			LocationService locationService) {
		
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
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date date1 = (Date) df.parse("24/10/2021");
			Date date2 = (Date) df.parse("24/11/2021");
			long time1 = date1.getTime();
			long time2 = date2.getTime();
			Timestamp sellstart = new Timestamp(time1);
			Timestamp sellend = new Timestamp(time2);
			product.setSellstartdate(sellstart);
			product.setSellenddate(sellend);
			
			Productcategory pc = new Productcategory();
			Productsubcategory psc = new Productsubcategory();
			
			pc.setProductcategoryid(1);
			psc.setProductsubcategoryid(1);
			
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
			Date datemd = (Date) df.parse("24/12/2021");
			long timemd = datemd.getTime();
			Timestamp md = new Timestamp(timemd);
			location.setModifieddate(md);
			
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
			Date datemd2 = (Date) df.parse("26/12/2021");
			long timemd2 = datemd.getTime();
			Timestamp md2 = new Timestamp(timemd);
			pch.setEnddate(md2);
			
			
			
			
//			Product p = productRepository.findById(1).get();
//			
//			productcosthistory.setStandardcost(new BigDecimal("3"));
//			
//			
//			try {
//				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//				Date date1 = (Date) df.parse("24/10/2021");
//				
//				long time1 = date1.getTime();
//				
//				Timestamp mofifiedDate = new Timestamp(time1);
//				
//				productcosthistory.setEnddate(mofifiedDate);
//				
//
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//			
//			
			
			System.out.println("LOS GUARDO");
			
		};
	}

} //end of class
