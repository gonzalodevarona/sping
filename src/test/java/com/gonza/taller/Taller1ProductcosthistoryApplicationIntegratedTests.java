package com.gonza.taller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.gonza.taller.model.prod.Location;
import com.gonza.taller.model.prod.Product;
import com.gonza.taller.model.prod.Productcosthistory;
import com.gonza.taller.model.prod.Productsubcategory;
import com.gonza.taller.repository.ProductCategoryRepository;
import com.gonza.taller.repository.ProductCostHistoryRepository;
import com.gonza.taller.repository.ProductRepository;
import com.gonza.taller.repository.ProductSubCategoryRepository;
import com.gonza.taller.service.ProductServiceImp;
import com.gonza.taller.service.ProductcosthistoryServiceImp;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = Taller1PruebasApplication.class)
public class Taller1ProductcosthistoryApplicationIntegratedTests {
	
	private ProductcosthistoryServiceImp productcosthistoryService;
	private ProductCostHistoryRepository productcosthistoryRepository;
	private Productcosthistory productcosthistoryEntity;
	
	private ProductRepository productRepository;
	private Product productEntity;

	@Autowired
	public Taller1ProductcosthistoryApplicationIntegratedTests(ProductcosthistoryServiceImp productcosthistoryService,
																ProductCostHistoryRepository productcosthistoryRepository,
																ProductRepository productRepository) {
		

		this.productcosthistoryService = productcosthistoryService;
		this.productcosthistoryRepository = productcosthistoryRepository;
		this.productRepository = productRepository;
		

	}
	
	public void setupProductcosthistoryService() {
		productEntity = new Product();
		productEntity.setProductid(1);
		productEntity.setName("GPU");
		
		productEntity = productRepository.save(productEntity);
		
	}
	
	@Test
	@Order(1)
	public void testSaveIntegrated() {
		setupProductcosthistoryService();
		
		productcosthistoryEntity = new Productcosthistory();
	
		
		productcosthistoryEntity.setStandardcost(new BigDecimal("4"));
		
		
		
		try {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date date1 = (Date) df.parse("24/10/2021");
			
			long time1 = date1.getTime();
			
			Timestamp modifiedDate = new Timestamp(time1);
			
			productcosthistoryEntity.setEnddate(modifiedDate);
			

		} catch (ParseException e) {
			e.printStackTrace();
		}

		productcosthistoryService.save(productcosthistoryEntity, productEntity.getProductid());

		Optional<Productcosthistory> theProductcosthistoryEntity = productcosthistoryRepository.findById(productcosthistoryEntity.getId()); 

		Productcosthistory theProductcosthistory = theProductcosthistoryEntity.get();
		
		
		assertTrue(theProductcosthistory.getStandardcost().compareTo(new BigDecimal("0")) == 1);
			

		
		
		Date date= new Date();
		Timestamp today = new Timestamp(date.getTime());
		
		assertFalse(today.before(theProductcosthistory.getEnddate()));

	}
	
	

	@Test
	@Order(2)
	public void testEditIntegrated() {

		// si se edita un productcosthistory con argumentos invalidos, salta una excepcion
	
			
			
			
		try {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date date1 = (Date) df.parse("24/10/2021");
			
			long time1 = date1.getTime();
			
			Timestamp modifiedDate = new Timestamp(time1);
			
			productcosthistoryEntity.setEnddate(modifiedDate);
			

		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		productcosthistoryEntity.setStandardcost(new BigDecimal(-10)); //argumento invalido
		
		
		
	

	}
	
	
	
} //end of class
