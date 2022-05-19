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
import java.time.LocalDate;
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

import com.gonza.taller.dao.ProductDAO;
import com.gonza.taller.model.prod.Location;
import com.gonza.taller.model.prod.Product;
import com.gonza.taller.model.prod.Productcosthistory;
import com.gonza.taller.model.prod.Productsubcategory;
import com.gonza.taller.repository.ProductCategoryRepository;
import com.gonza.taller.repository.ProductCostHistoryRepository;
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
	
	private ProductDAO productDAO;
	private Product productEntity;

	@Autowired
	public Taller1ProductcosthistoryApplicationIntegratedTests(ProductcosthistoryServiceImp productcosthistoryService,
																ProductCostHistoryRepository productcosthistoryRepository,
																ProductDAO productDAO) {
		

		this.productcosthistoryService = productcosthistoryService;
		this.productcosthistoryRepository = productcosthistoryRepository;
		this.productDAO = productDAO;
		

	}
	
	public void setupProductcosthistoryService() {
		productEntity = new Product();
		productEntity.setProductid(1);
		productEntity.setName("GPU");
		
		productDAO.save(productEntity);
		
	}
	
	@Test
	@Order(1)
	public void testSaveIntegrated() {
		setupProductcosthistoryService();
		
		productcosthistoryEntity = new Productcosthistory();
	
		
		productcosthistoryEntity.setStandardcost(new BigDecimal("4"));
		
		
		LocalDate modifiedDate = LocalDate.of(2021, 10, 24);
	
		productcosthistoryEntity.setEnddate(modifiedDate);
		

		productcosthistoryService.save(productcosthistoryEntity, productEntity.getProductid());

		Optional<Productcosthistory> theProductcosthistoryEntity = productcosthistoryRepository.findById(productcosthistoryEntity.getId()); 

		Productcosthistory theProductcosthistory = theProductcosthistoryEntity.get();
		
		
		assertTrue(theProductcosthistory.getStandardcost().compareTo(new BigDecimal("0")) == 1);
			

		
		
		
		LocalDate today = LocalDate.now();
		
		
		
		productcosthistoryEntity.setEnddate(modifiedDate);
		
		
		assertFalse(today.isBefore(theProductcosthistory.getEnddate()));

	}
	
	

	@Test
	@Order(2)
	public void testEditIntegrated() {

		// si se edita un productcosthistory con argumentos invalidos, salta una excepcion
	
			
			

		LocalDate modifiedDate = LocalDate.of(2021, 10, 24);
		productcosthistoryEntity.setEnddate(modifiedDate);
		
		productcosthistoryEntity.setStandardcost(new BigDecimal(-10)); //argumento invalido
		
		
		
	

	}
	
	
	
} //end of class
