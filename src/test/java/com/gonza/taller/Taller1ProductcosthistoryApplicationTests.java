package com.gonza.taller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.gonza.taller.model.prod.Location;
import com.gonza.taller.model.prod.Product;
import com.gonza.taller.model.prod.Productcategory;
import com.gonza.taller.model.prod.Productcosthistory;
import com.gonza.taller.repository.LocationRepository;
import com.gonza.taller.repository.ProductCostHistoryRepository;
import com.gonza.taller.repository.ProductRepository;
import com.gonza.taller.repository.ProductSubCategoryRepository;
import com.gonza.taller.service.LocationServiceImp;
import com.gonza.taller.service.ProductServiceImp;
import com.gonza.taller.service.ProductcosthistoryServiceImp;

@SpringBootTest(classes = Taller1Application.class)
@ExtendWith(SpringExtension.class)
public class Taller1ProductcosthistoryApplicationTests {
	
	// Mocks punto C
	@Mock
	private ProductCostHistoryRepository productcosthistoryRepository;
	
	@Mock
	private ProductRepository productRepository;
	
	@InjectMocks
	private ProductcosthistoryServiceImp productcosthistoryService;
	
	@InjectMocks
	private ProductServiceImp productService;
	
	@Autowired
	public Taller1ProductcosthistoryApplicationTests( ProductcosthistoryServiceImp productcosthistoryService,
													ProductServiceImp productService) {
		this.productcosthistoryService = productcosthistoryService;
		this.productService = productService;
	}

	
	
	public void setupProductcosthistoryTest() {
		
		Productcosthistory productcosthistory= new Productcosthistory();
		when(productcosthistoryRepository.findById(1)).thenReturn(Optional.of(productcosthistory));
		
		Product product= new Product();
		when(productRepository.findById(1)).thenReturn(Optional.of(product));
	
	}
	


	//Test punto 5B save
	@Test
	public void testSave() {
		setupProductcosthistoryTest();
		Productcosthistory productcosthistory = new Productcosthistory();
		when(productcosthistoryRepository.findById(1)).thenReturn(Optional.of(productcosthistory));
		
		Product p = productRepository.findById(1).get();
		
		productcosthistory.setStandardcost(new BigDecimal("3"));
		
		
		try {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date date1 = (Date) df.parse("24/10/2021");
			
			long time1 = date1.getTime();
			
			Timestamp mofifiedDate = new Timestamp(time1);
			
			productcosthistory.setEnddate(mofifiedDate);
			

		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		productcosthistoryService.save(productcosthistory, 1);

		Optional<Productcosthistory> productcosthistoryEntity = productcosthistoryRepository.findById(1);
		Productcosthistory theProductcosthistory = productcosthistoryEntity.get();
		

		
	
		assertTrue(theProductcosthistory.getStandardcost().compareTo(new BigDecimal("0")) == 1);
			
		
		assertEquals(theProductcosthistory.getProduct(), p);
		
		
		Date date= new Date();
		Timestamp today = new Timestamp(date.getTime());
		
		assertFalse(today.before(productcosthistory.getEnddate()));
		
		
		verify(productcosthistoryRepository).save(productcosthistory);
		verify(productcosthistoryRepository).findById(1);

		
	}
	


	//Test punto 5B edit
		@Test
	public void testEdit() {
		setupProductcosthistoryTest();
		try {
			Productcosthistory theProductcosthistory = new Productcosthistory();
			
			 // este es el argumento invalido
			
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date date1 = df.parse("24/09/2025"); // este es el argumento invalido
			
			long time1 = date1.getTime();
			
			Timestamp endDate = new Timestamp(time1);
			

			
			theProductcosthistory.setStandardcost(new BigDecimal("2"));
			
			assertThrows(RuntimeException.class, () -> productcosthistoryService.edit(theProductcosthistory, 1));
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ahora si se edita el producto con argumentos validos
		
		try {
			Productcosthistory theProductcosthistory = new Productcosthistory();
			
			Integer id = 1;
			theProductcosthistory.setId(id);
			
			when(productcosthistoryRepository.findById(1)).thenReturn(Optional.of(theProductcosthistory));
			
			
		
			BigDecimal standardCost = new BigDecimal("2");
			
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date date1 = df.parse("23/09/2020");
			
			long time1 = date1.getTime();
			
			Timestamp enddate = new Timestamp(time1);
			
			theProductcosthistory.setEnddate(enddate);
			theProductcosthistory.setStandardcost(standardCost);
			
			
			productcosthistoryService.edit(theProductcosthistory, 1);

			Optional<Productcosthistory> theProductcosthistoryOptional = productcosthistoryRepository.findById(1);

			Productcosthistory theProductcosthistoryEntity = theProductcosthistoryOptional.get();
			
			assertEquals(enddate, theProductcosthistoryEntity.getEnddate());
			assertEquals(id, theProductcosthistoryEntity.getId());
			assertEquals(standardCost, theProductcosthistoryEntity.getStandardcost());
			
			
			verify(productRepository, VerificationModeFactory.times(2)).findById(01);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	
	

} //end of class
