package com.gonza.taller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import com.gonza.taller.model.auth.Usermine;
import com.gonza.taller.model.prod.Location;
import com.gonza.taller.model.prod.Product;
import com.gonza.taller.model.prod.Productcategory;
import com.gonza.taller.model.prod.Productcosthistory;
import com.gonza.taller.model.prod.Productinventory;
import com.gonza.taller.model.prod.Productmodel;
import com.gonza.taller.model.prod.Productsubcategory;
import com.gonza.taller.model.prod.Unitmeasure;
import com.gonza.taller.repository.ProductCategoryRepository;
import com.gonza.taller.repository.ProductSubCategoryRepository;
import com.gonza.taller.repository.UserRepositoryI;
import com.gonza.taller.service.LocationService;
import com.gonza.taller.service.LocationServiceImp;
import com.gonza.taller.service.ProductServiceImp;
import com.gonza.taller.service.ProductcategoryServiceImp;
import com.gonza.taller.service.ProductcosthistoryService;
import com.gonza.taller.service.ProductcosthistoryServiceImp;
import com.gonza.taller.service.ProductinventoryService;
import com.gonza.taller.service.ProductinventoryServiceImp;
import com.gonza.taller.service.ProductmodelService;
import com.gonza.taller.service.ProductmodelServiceImp;
import com.gonza.taller.service.ProductsubcategoryServiceImp;
import com.gonza.taller.service.UnitmeasureServiceImp;

@SpringBootApplication
@EntityScan(basePackages = {"com.gonza.taller.authentication","com.gonza.taller.model.*"})
@ComponentScan(basePackages = {"com.gonza.taller.authentication","com.gonza.taller.service","com.gonza.taller.dao","com.gonza.taller.repository","com.gonza.taller.controller"})
public class Taller1Application {
	
	
	
	@Bean
	public Java8TimeDialect java8TimeDialect() {
		return new Java8TimeDialect();
	}

	public static void main(String[] args) {
		SpringApplication.run(Taller1Application.class, args);
	}
	
	@Bean
	@Transactional
	public CommandLineRunner dummy(UserRepositoryI userRepository, ProductServiceImp productService,
			LocationServiceImp locationService, ProductcosthistoryServiceImp productcosthistoryService,
			ProductinventoryServiceImp productinventoryService,
			ProductcategoryServiceImp productcategoryService, ProductsubcategoryServiceImp productsubcategoryService,
			ProductmodelServiceImp productmodelService, UnitmeasureServiceImp unitmeasureService) {
		return(args)->{

			//ADMIN
			
			Usermine admin = new Usermine();
			admin.setUserId(1);
			admin.setUsername("admin");
			admin.setPassword("{noop}admin");
			admin.setType(Usermine.ADMIN);
			userRepository.save(admin);
			
			Product product = new Product();
			product.setProductnumber("1010");
			product.setWeight(1);
			product.setSize(2);
			product.setName("RTX 3090");
			product.setProductid(1);
			
			product.setSellstartdate(LocalDate.of(2021, 10, 24));
			product.setSellenddate(LocalDate.of(2021, 11, 24));
			
			Productcategory pc1 = new Productcategory();
			Productsubcategory psc1 = new Productsubcategory();
			
//			pc.setProductcategoryid(1);
//			psc.setProductsubcategoryid(1);
			
			pc1.setName("Vehiculos");
			psc1.setName("Fuera de borda");
			
			product.setProductsubcategory(psc1);
			psc1.setProductcategory(pc1);
			
			
			Productcategory pc2 = new Productcategory();
			Productsubcategory psc2 = new Productsubcategory();
			
//			pc.setProductcategoryid(1);
//			psc.setProductsubcategoryid(1);
			
			pc2.setName("Tarjetas Graficas");
			psc2.setName("Turing");
			
			product.setProductsubcategory(psc2);
			psc2.setProductcategory(pc2);
			
			Productmodel  productmodel1 = new Productmodel();
			Productmodel  productmodel2 = new Productmodel();
			
			productmodel1.setCatalogdescription("Motor para largos trayectos");
			productmodel1.setInstructions("Cuidar muy bien");
			productmodel1.setName("Serie 3");
			
			productmodel2.setCatalogdescription("Tarjeta grafica para jugar o minar");
			productmodel2.setInstructions("No usar para minar aunque da mucho dinero");
			productmodel2.setName("Serie 3000");
			
			product.setProductmodel(productmodel2);
			
			productmodelService.save(productmodel1);
			productmodelService.save(productmodel2);
			
			
			Unitmeasure unitmeasure = new Unitmeasure();
			
			unitmeasure.setName("unidad");
			unitmeasure.setUnitmeasurecode(1);
			
			unitmeasureService.save(unitmeasure);
			
			product.setUnitmeasure1(unitmeasure);
			
			
			productcategoryService.save(pc2);
			productsubcategoryService.save(psc2);
			
			
			productcategoryService.save(pc1);
			productsubcategoryService.save(psc1);
			productService.save(product,1,1);
			
			
			
			Location location = new Location();
			
			location.setAvailability(new BigDecimal("2"));
			location.setCostrate(new BigDecimal("0.5"));
			location.setLocationid(1);
			location.setName("Ibiza");
		
			location.setModifieddate(LocalDate.of(2021, 12, 21));
			
			locationService.save(location);
			
			
			
			
			
			
			//USER
			
			Usermine user = new Usermine();
			user.setUserId(2);
			user.setUsername("user");
			user.setPassword("{noop}user");
			user.setType(Usermine.USER);
			userRepository.save(user);
			
			Productcosthistory pch = new Productcosthistory(); 
			pch.setStandardcost(new BigDecimal("3"));
			
			Product productProccessed = productService.findById(1).get();
			pch.setProduct(product);
			
			pch.setEnddate(LocalDate.of(2021, 12, 21));
			productcosthistoryService.save(pch, productProccessed.getProductid());
			
			
			Productinventory productinventory = new Productinventory();
			Location locationProccessed = locationService.findById(1).get();
			
			productinventory.setLocation(locationProccessed);
			
			
			productinventory.setProduct(productProccessed);
			productinventory.setQuantity(5);
			productinventoryService.save(productinventory,productProccessed.getProductid(),locationProccessed.getLocationid());
			
			
			
			

			
			
			System.out.println("LOS GUARDO");
			
		};
	}

} //end of class
