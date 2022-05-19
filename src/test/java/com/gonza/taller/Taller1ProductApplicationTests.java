package com.gonza.taller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.NoSuchElementException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.gonza.taller.dao.ProductDAO;
import com.gonza.taller.model.prod.Location;
import com.gonza.taller.model.prod.Product;
import com.gonza.taller.model.prod.Productcategory;
import com.gonza.taller.model.prod.Productsubcategory;
import com.gonza.taller.repository.ProductCategoryRepository;

import com.gonza.taller.repository.ProductSubCategoryRepository;
import com.gonza.taller.service.ProductServiceImp;

@SpringBootTest(classes = Taller1Application.class)
//@ContextConfiguration(classes = Taller1Application.class)
@ExtendWith(SpringExtension.class)
class Taller1ProductApplicationTests {

	// Mocks punto A
	@Mock
	private ProductDAO productDAO;
	
	@InjectMocks
	private ProductServiceImp productService;
	
	@Mock
	private ProductSubCategoryRepository productSubCategoryRepository;

	@Mock
	private ProductCategoryRepository productCategoryRepository;
	
	@Autowired
	public Taller1ProductApplicationTests(ProductServiceImp productService) {
		this.productService = productService;
	}
	
	
	public void setupProductTest() {
	
		Productcategory pc = new Productcategory();
		when(productCategoryRepository.findById(1)).thenReturn(Optional.of(pc));
		
		Productsubcategory psc = new Productsubcategory();
		when(productSubCategoryRepository.findById(1)).thenReturn(Optional.of(psc));
		
		Product product = new Product();
		when(productDAO.findById(1)).thenReturn(Optional.of(product));
		
	
	}
	
	
	

	//Test punto 5A save
	@Test
	public void testSaveGood() {
		setupProductTest();
		Product product = productDAO.findById(1).get();
		
//		when(productDAO.findById(1)).thenReturn(Optional.of(product));
		
		Productcategory pc = productCategoryRepository.findById(1).get();
		Productsubcategory psc = productSubCategoryRepository.findById(1).get();
		
		product.setProductnumber("1010");
		product.setWeight(1);
		product.setSize(2);
		

		
		LocalDate sellstart = LocalDate.of(2021, 10, 24);
		LocalDate sellend = LocalDate.of(2021, 11, 24);
		product.setSellstartdate(sellstart);
		product.setSellenddate(sellend);
		
		productService.save(product, 1, 1);

		Optional<Product> productEntity = productDAO.findById(01);
		Product theProduct = productEntity.get();
		
		assertFalse(theProduct.getProductnumber().isEmpty());
		assertTrue(theProduct.getWeight() > 0);
		assertTrue(theProduct.getSize() > 0);
		assertTrue(theProduct.getSellstartdate().isBefore(theProduct.getSellenddate()));
		assertEquals(theProduct.getProductsubcategory(), psc);
		assertEquals(theProduct.getProductsubcategory().getProductcategory(), pc);

		verify(productDAO).save(product);
		verify(productDAO, atLeastOnce()).findById(1);

		
	}
	
	
	
	//Test punto 5A save
	@Test
	public void testSaveBadProductCategory() {
		setupProductTest();
		Product product = productDAO.findById(1).get();
				
		product.setProductnumber("122");
		product.setWeight(1);
		product.setSize(2);
		

		LocalDate sellstart = LocalDate.of(2021, 10, 24);
		LocalDate sellend = LocalDate.of(2021, 11, 24);
		product.setSellstartdate(sellstart);
		product.setSellenddate(sellend);
		
		
		
		assertThrows(IllegalArgumentException.class, () -> productService.save(product, 2, 1));

	
		
		verify(productDAO, atLeastOnce()).findById(1);
		
	}
	
	
	//Test punto 5A save
	@Test
	public void testSaveBadProductSubCategory() {
		setupProductTest();
		Product product = productDAO.findById(1).get();
		
//		when(productDAO.findById(1)).thenReturn(Optional.of(product));
		
		
		product.setProductnumber("122");
		product.setWeight(1);
		product.setSize(2);
		

		LocalDate sellstart = LocalDate.of(2021, 10, 24);
		LocalDate sellend = LocalDate.of(2021, 11, 24);
		product.setSellstartdate(sellstart);
		product.setSellenddate(sellend);
		
		
		assertThrows(IllegalArgumentException.class, () -> productService.save(product, 1, 2));

	
		
		verify(productDAO, atLeastOnce()).findById(1);
		
	}
	
	
	//Test punto 5A save
	@Test
	public void testSaveBadProductNumber() {
		setupProductTest();
		Product product = productDAO.findById(1).get();
		
		
		Productcategory pc = productCategoryRepository.findById(1).get();
		Productsubcategory psc = productSubCategoryRepository.findById(1).get();
		
		product.setProductnumber(null);
		product.setWeight(1);
		product.setSize(2);
		

		LocalDate sellstart = LocalDate.of(2021, 10, 24);
		LocalDate sellend = LocalDate.of(2021, 11, 24);
		product.setSellstartdate(sellstart);
		product.setSellenddate(sellend);
		
		
		assertThrows(NullPointerException.class, () -> productService.save(product, 1, 1));

	
		
		verify(productDAO, atLeastOnce()).findById(1);
		
	}
	
	
	
	
	//Test punto 5A save
	@Test
	public void testSaveBadStartDate() {
		setupProductTest();
		Product product = productDAO.findById(1).get();
		
		
		Productcategory pc = productCategoryRepository.findById(1).get();
		Productsubcategory psc = productSubCategoryRepository.findById(1).get();
		
		product.setProductnumber("12");
		product.setWeight(2);
		product.setSize(0);
		

		LocalDate sellstart = LocalDate.of(2021, 10, 24);
		LocalDate sellend = LocalDate.of(2021, 11, 24);
		product.setSellstartdate(null);
		product.setSellenddate(sellend);
		
		
		assertThrows(NullPointerException.class, () -> productService.save(product, 1, 1));

	
		
		verify(productDAO, atLeastOnce()).findById(1);
		
	}
	
	//Test punto 5A save
	@Test
	public void testSaveBadStartEnd() {
		setupProductTest();
		Product product = productDAO.findById(1).get();
		
		
		Productcategory pc = productCategoryRepository.findById(1).get();
		Productsubcategory psc = productSubCategoryRepository.findById(1).get();
		
		product.setProductnumber("12");
		product.setWeight(2);
		product.setSize(0);
		

		LocalDate sellstart = LocalDate.of(2021, 10, 24);
		LocalDate sellend = LocalDate.of(2021, 11, 24);
		product.setSellstartdate(sellstart);
		product.setSellenddate(null);
		
		
		
		assertThrows(NullPointerException.class, () -> productService.save(product, 1, 1));

	
		
		verify(productDAO, atLeastOnce()).findById(1);
		
	}
	
	
	//Validaciones 
	
	
	//Test punto 5A save
	@Test
	public void testSaveNoValidWeight() {
		setupProductTest();
		Product product = productDAO.findById(1).get();
		
		
		Productcategory pc = productCategoryRepository.findById(1).get();
		Productsubcategory psc = productSubCategoryRepository.findById(1).get();
		
		product.setProductnumber("12");
		product.setWeight(0);
		product.setSize(2);
		

		
		LocalDate sellstart1 = LocalDate.of(2021, 10, 24);
		LocalDate sellend1 = LocalDate.of(2021, 11, 24);
		product.setSellstartdate(sellstart1);
		product.setSellenddate(sellend1);
		
		
		
		assertThrows(IllegalArgumentException.class, () -> productService.save(product, 1, 1));

	
		
		verify(productDAO, atLeastOnce()).findById(1);
		
	}
	
	
	//Test punto 5A save
	@Test
	public void testSaveNoValidSize() {
		setupProductTest();
		Product product = productDAO.findById(1).get();
	
		
		Productcategory pc = productCategoryRepository.findById(1).get();
		Productsubcategory psc = productSubCategoryRepository.findById(1).get();
		
		product.setProductnumber("12");
		product.setWeight(2);
		product.setSize(0);
		
		LocalDate sellstart1 = LocalDate.of(2021, 10, 24);
		LocalDate sellend1 = LocalDate.of(2021, 11, 24);
		product.setSellstartdate(sellstart1);
		product.setSellenddate(sellend1);
		
		
		
		
		
		assertThrows(IllegalArgumentException.class, () -> productService.save(product, 1, 1));

	
		
		verify(productDAO, atLeastOnce()).findById(1);
		
	}
	
	
	//Test punto 5A save
	@Test
	public void testSaveNoValidProductNumber() {
		setupProductTest();
		Product product = productDAO.findById(1).get();
		
//		when(productDAO.findById(1)).thenReturn(Optional.of(product));
		
		Productcategory pc = productCategoryRepository.findById(1).get();
		Productsubcategory psc = productSubCategoryRepository.findById(1).get();
		
		product.setProductnumber("");
		product.setWeight(1);
		product.setSize(2);
		
		LocalDate sellstart1 = LocalDate.of(2021, 10, 24);
		LocalDate sellend1 = LocalDate.of(2021, 11, 24);
		product.setSellstartdate(sellstart1);
		product.setSellenddate(sellend1);
		
		
		
		assertThrows(IllegalArgumentException.class, () -> productService.save(product, 1, 1));

	
		
		verify(productDAO, atLeastOnce()).findById(1);
		

		
	}
	
	
	//Test punto 5A save
		@Test
		public void testSaveNoValidDates() {
			setupProductTest();
			Product product = productDAO.findById(1).get();
		
			
			Productcategory pc = productCategoryRepository.findById(1).get();
			Productsubcategory psc = productSubCategoryRepository.findById(1).get();
			
			product.setProductnumber("101");
			product.setWeight(1);
			product.setSize(2);
			
			LocalDate sellstart1 = LocalDate.of(2021, 10, 24);
			LocalDate sellend1 = LocalDate.of(2021, 11, 24);
			product.setSellstartdate(sellstart1);
			product.setSellenddate(sellend1);
			
			
			
			assertThrows(IllegalArgumentException.class, () -> productService.save(product, 1, 1));

		
			
			verify(productDAO, atLeastOnce()).findById(1);
			

			
		}
	
	
		
		//TEST EDIT
		

		//Test punto 5A edit
		@Test
		public void testEditGood() {
			setupProductTest();

			
			Product theProduct = new Product();
			
			theProduct.setProductid(1);
			
			when(productDAO.findById(1)).thenReturn(Optional.of(theProduct));
			
			
			String productNumber = "1010";
			long productWeight = 1;
			long productSize = 2;
			
			LocalDate sellstart = LocalDate.of(2021, 10, 23);
			LocalDate sellend = LocalDate.of(2021, 11, 23);
			theProduct.setSellstartdate(sellstart);
			theProduct.setSellenddate(sellend);
			
			
			
			theProduct.setProductnumber(productNumber);
			

			theProduct.setWeight(productWeight);
			theProduct.setSize(productSize);
			productService.edit(theProduct, 1, 1);

			Optional<Product> theProductOptional = productDAO.findById(1);

			Product theProductEntity = theProductOptional.get();
			
			assertEquals(productNumber, theProductEntity.getProductnumber());
			assertEquals(productSize, theProductEntity.getSize());
			assertEquals(productWeight, theProductEntity.getWeight());
			assertEquals(sellstart, theProductEntity.getSellstartdate());
			assertEquals(sellend, theProductEntity.getSellenddate());

			
			verify(productDAO, VerificationModeFactory.times(2)).findById(01);

		}
		
		
		
		//Test punto 5A edit
				@Test
				public void testEditBadProductNumber() {
					setupProductTest();
					Product theProduct = new Product();
					
					String productNumber = ""; // este es el argumento invalido
					
					
					LocalDate sellstart = LocalDate.of(2021, 9, 24);
					LocalDate sellend = LocalDate.of(2021, 10, 24);
					theProduct.setSellstartdate(sellstart);
					theProduct.setSellenddate(sellend);

					theProduct.setProductnumber(productNumber);
					theProduct.setSellstartdate(sellstart);
					theProduct.setSellenddate(sellend);
					theProduct.setWeight(1);
					theProduct.setSize(2);
					
					assertThrows(IllegalArgumentException.class, () -> productService.edit(theProduct, 1, 1));

					

				}
				
				
				//Test punto 5A save
				@Test
				public void testEditBadProductCategory() {
					setupProductTest();
					Product product = productDAO.findById(1).get();
							
					product.setProductnumber("122");
					product.setWeight(1);
					product.setSize(2);
					
					LocalDate sellstart = LocalDate.of(2021, 9, 24);
					LocalDate sellend = LocalDate.of(2021, 10, 24);
					product.setSellstartdate(sellstart);
					product.setSellenddate(sellend);
					
					
					
					assertThrows(IllegalArgumentException.class, () -> productService.edit(product, 2, 1));

				
					
					verify(productDAO, atLeastOnce()).findById(1);
					
				}
				
				
				

				
				//Test punto 5A save
				@Test
				public void testEditBadProductSubCategory() {
					setupProductTest();
					Product product = productDAO.findById(1).get();
							
					product.setProductnumber("122");
					product.setWeight(1);
					product.setSize(2);
					
					LocalDate sellstart = LocalDate.of(2021, 9, 24);
					LocalDate sellend = LocalDate.of(2021, 10, 24);
					product.setSellstartdate(sellstart);
					product.setSellenddate(sellend);
					
					
					
					assertThrows(IllegalArgumentException.class, () -> productService.edit(product, 1, 2));

				
					
					verify(productDAO, atLeastOnce()).findById(1);
					
				}
				
				
		
				
				

				
				
				
				
				//Test punto 5A save
				@Test
				public void testEditBadStartDate() {
					setupProductTest();
					Product product = productDAO.findById(1).get();
					
					
					Productcategory pc = productCategoryRepository.findById(1).get();
					Productsubcategory psc = productSubCategoryRepository.findById(1).get();
					
					product.setProductnumber("12");
					product.setWeight(2);
					product.setSize(0);
					
					LocalDate sellstart = LocalDate.of(2021, 9, 24);
					LocalDate sellend = LocalDate.of(2021, 10, 24);
					product.setSellstartdate(null);
					product.setSellenddate(sellend);
					
					
					
					assertThrows(NullPointerException.class, () -> productService.edit(product, 1, 1));

				
					
					verify(productDAO, atLeastOnce()).findById(1);
					
				}
				
				//Test punto 5A save
				@Test
				public void testEditBadStartEnd() {
					setupProductTest();
					Product product = productDAO.findById(1).get();
					
					
					Productcategory pc = productCategoryRepository.findById(1).get();
					Productsubcategory psc = productSubCategoryRepository.findById(1).get();
					
					product.setProductnumber("12");
					product.setWeight(2);
					product.setSize(0);
					
					LocalDate sellstart = LocalDate.of(2021, 9, 24);
					LocalDate sellend = LocalDate.of(2021, 10, 24);
					product.setSellstartdate(sellstart);
					product.setSellenddate(null);
					
					
					
					assertThrows(NullPointerException.class, () -> productService.edit(product, 1, 1));

				
					
					verify(productDAO, atLeastOnce()).findById(1);
					
				}
				
				
				//Validaciones 
				
				
				//Test punto 5A save
				@Test
				public void testEditNoValidWeight() {
					setupProductTest();
					Product product = productDAO.findById(1).get();
					
					
					Productcategory pc = productCategoryRepository.findById(1).get();
					Productsubcategory psc = productSubCategoryRepository.findById(1).get();
					
					product.setProductnumber("12");
					product.setWeight(0);
					product.setSize(2);
					
					LocalDate sellstart = LocalDate.of(2021, 9, 24);
					LocalDate sellend = LocalDate.of(2021, 10, 24);
					product.setSellstartdate(sellstart);
					product.setSellenddate(sellend);
					
					
					
					assertThrows(IllegalArgumentException.class, () -> productService.edit(product, 1, 1));

				
					
					verify(productDAO, atLeastOnce()).findById(1);
					
				}
				
				
				//Test punto 5A save
				@Test
				public void testEditNoValidSize() {
					setupProductTest();
					Product product = productDAO.findById(1).get();
				
					
					Productcategory pc = productCategoryRepository.findById(1).get();
					Productsubcategory psc = productSubCategoryRepository.findById(1).get();
					
					product.setProductnumber("12");
					product.setWeight(2);
					product.setSize(0);
					
					LocalDate sellstart = LocalDate.of(2021, 9, 24);
					LocalDate sellend = LocalDate.of(2021, 10, 24);
					product.setSellstartdate(sellstart);
					product.setSellenddate(sellend);
					
					
					
					assertThrows(IllegalArgumentException.class, () -> productService.edit(product, 1, 1));

				
					
					verify(productDAO, atLeastOnce()).findById(1);
					
				}
				
				
				//Test punto 5A save
				@Test
				public void testEditNoValidProductNumber() {
					setupProductTest();
					Product product = productDAO.findById(1).get();
					
//					when(productDAO.findById(1)).thenReturn(Optional.of(product));
					
					Productcategory pc = productCategoryRepository.findById(1).get();
					Productsubcategory psc = productSubCategoryRepository.findById(1).get();
					
					product.setProductnumber("");
					product.setWeight(1);
					product.setSize(2);
					
					LocalDate sellstart = LocalDate.of(2021, 9, 24);
					LocalDate sellend = LocalDate.of(2021, 10, 24);
					product.setSellstartdate(sellstart);
					product.setSellenddate(sellend);
					
					assertThrows(IllegalArgumentException.class, () -> productService.edit(product, 1, 1));

				
					
					verify(productDAO, atLeastOnce()).findById(1);
					

					
				}
				
				
				//Test punto 5A save
					@Test
					public void testEditNoValidDates() {
						setupProductTest();
						Product product = productDAO.findById(1).get();
					
						
						Productcategory pc = productCategoryRepository.findById(1).get();
						Productsubcategory psc = productSubCategoryRepository.findById(1).get();
						
						product.setProductnumber("101");
						product.setWeight(1);
						product.setSize(2);
						
						LocalDate sellstart = LocalDate.of(2021, 9, 24);
						LocalDate sellend = LocalDate.of(2021, 10, 24);
						product.setSellstartdate(sellend);
						product.setSellenddate(sellstart);
						
						assertThrows(IllegalArgumentException.class, () -> productService.edit(product, 1, 1));

					
						
						verify(productDAO, atLeastOnce()).findById(1);
						

						
					}

		


		
		



	
} //end of class
