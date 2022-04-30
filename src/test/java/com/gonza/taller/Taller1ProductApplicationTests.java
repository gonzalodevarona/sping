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
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.gonza.taller.model.prod.Product;
import com.gonza.taller.model.prod.Productcategory;
import com.gonza.taller.model.prod.Productsubcategory;
import com.gonza.taller.repository.ProductCategoryRepository;
import com.gonza.taller.repository.ProductRepository;
import com.gonza.taller.repository.ProductSubCategoryRepository;
import com.gonza.taller.service.ProductServiceImp;

@SpringBootTest(classes = Taller1Application.class)
//@ContextConfiguration(classes = Taller1Application.class)
@ExtendWith(SpringExtension.class)
class Taller1ProductApplicationTests {

	// Mocks punto A
	@Mock
	private ProductRepository productRepository;
	
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
		when(productRepository.findById(1)).thenReturn(Optional.of(product));
		
	
	}
	
	
	

	//Test punto 5A save
	@Test
	public void testSaveGood() {
		setupProductTest();
		Product product = productRepository.findById(1).get();
		
//		when(productRepository.findById(1)).thenReturn(Optional.of(product));
		
		Productcategory pc = productCategoryRepository.findById(1).get();
		Productsubcategory psc = productSubCategoryRepository.findById(1).get();
		
		product.setProductnumber("1010");
		product.setWeight(1);
		product.setSize(2);
		
		try {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date date1 = (Date) df.parse("24/10/2021");
			Date date2 = (Date) df.parse("24/11/2021");
			long time1 = date1.getTime();
			long time2 = date2.getTime();
			Timestamp sellstart = new Timestamp(time1);
			Timestamp sellend = new Timestamp(time2);
			product.setSellstartdate(sellstart);
			product.setSellenddate(sellend);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		productService.save(product, 1, 1);

		Optional<Product> productEntity = productRepository.findById(01);
		Product theProduct = productEntity.get();
		
		assertFalse(theProduct.getProductnumber().isEmpty());
		assertTrue(theProduct.getWeight() > 0);
		assertTrue(theProduct.getSize() > 0);
		assertTrue(theProduct.getSellstartdate().before(theProduct.getSellenddate()));
		assertEquals(theProduct.getProductsubcategory(), psc);
		assertEquals(theProduct.getProductsubcategory().getProductcategory(), pc);

		verify(productRepository).save(product);
		verify(productRepository, atLeastOnce()).findById(1);

		
	}
	
	
	
	//Test punto 5A save
	@Test
	public void testSaveBadProductCategory() {
		setupProductTest();
		Product product = productRepository.findById(1).get();
				
		product.setProductnumber("122");
		product.setWeight(1);
		product.setSize(2);
		
		try {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date date1 = (Date) df.parse("24/10/2021");
			Date date2 = (Date) df.parse("24/11/2021");
			long time1 = date1.getTime();
			long time2 = date2.getTime();
			Timestamp sellstart = new Timestamp(time1);
			Timestamp sellend = new Timestamp(time2);
			product.setSellstartdate(sellstart);
			product.setSellenddate(sellend);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		
		assertThrows(IllegalArgumentException.class, () -> productService.save(product, 2, 1));

	
		
		verify(productRepository, atLeastOnce()).findById(1);
		
	}
	
	
	//Test punto 5A save
	@Test
	public void testSaveBadProductSubCategory() {
		setupProductTest();
		Product product = productRepository.findById(1).get();
		
//		when(productRepository.findById(1)).thenReturn(Optional.of(product));
		
		
		product.setProductnumber("122");
		product.setWeight(1);
		product.setSize(2);
		
		try {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date date1 = (Date) df.parse("24/10/2021");
			Date date2 = (Date) df.parse("24/11/2021");
			long time1 = date1.getTime();
			long time2 = date2.getTime();
			Timestamp sellstart = new Timestamp(time1);
			Timestamp sellend = new Timestamp(time2);
			product.setSellstartdate(sellstart);
			product.setSellenddate(sellend);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		
		assertThrows(IllegalArgumentException.class, () -> productService.save(product, 1, 2));

	
		
		verify(productRepository, atLeastOnce()).findById(1);
		
	}
	
	
	//Test punto 5A save
	@Test
	public void testSaveBadProductNumber() {
		setupProductTest();
		Product product = productRepository.findById(1).get();
		
		
		Productcategory pc = productCategoryRepository.findById(1).get();
		Productsubcategory psc = productSubCategoryRepository.findById(1).get();
		
		product.setProductnumber(null);
		product.setWeight(1);
		product.setSize(2);
		
		try {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date date1 = (Date) df.parse("24/10/2021");
			Date date2 = (Date) df.parse("24/11/2021");
			long time1 = date1.getTime();
			long time2 = date2.getTime();
			Timestamp sellstart = new Timestamp(time1);
			Timestamp sellend = new Timestamp(time2);
			product.setSellstartdate(sellstart);
			product.setSellenddate(sellend);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		
		assertThrows(NullPointerException.class, () -> productService.save(product, 1, 1));

	
		
		verify(productRepository, atLeastOnce()).findById(1);
		
	}
	
	
	
	
	//Test punto 5A save
	@Test
	public void testSaveBadStartDate() {
		setupProductTest();
		Product product = productRepository.findById(1).get();
		
		
		Productcategory pc = productCategoryRepository.findById(1).get();
		Productsubcategory psc = productSubCategoryRepository.findById(1).get();
		
		product.setProductnumber("12");
		product.setWeight(2);
		product.setSize(0);
		
		try {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date date1 = (Date) df.parse("24/10/2021");
			Date date2 = (Date) df.parse("24/11/2021");
			long time1 = date1.getTime();
			long time2 = date2.getTime();
			Timestamp sellstart = new Timestamp(time1);
			Timestamp sellend = new Timestamp(time2);
			product.setSellstartdate(null);
			product.setSellenddate(sellend);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		
		assertThrows(NullPointerException.class, () -> productService.save(product, 1, 1));

	
		
		verify(productRepository, atLeastOnce()).findById(1);
		
	}
	
	//Test punto 5A save
	@Test
	public void testSaveBadStartEnd() {
		setupProductTest();
		Product product = productRepository.findById(1).get();
		
		
		Productcategory pc = productCategoryRepository.findById(1).get();
		Productsubcategory psc = productSubCategoryRepository.findById(1).get();
		
		product.setProductnumber("12");
		product.setWeight(2);
		product.setSize(0);
		
		try {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date date1 = (Date) df.parse("24/10/2021");
			Date date2 = (Date) df.parse("24/11/2021");
			long time1 = date1.getTime();
			long time2 = date2.getTime();
			Timestamp sellstart = new Timestamp(time1);
			Timestamp sellend = new Timestamp(time2);
			product.setSellstartdate(sellstart);
			product.setSellenddate(null);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		
		assertThrows(NullPointerException.class, () -> productService.save(product, 1, 1));

	
		
		verify(productRepository, atLeastOnce()).findById(1);
		
	}
	
	
	//Validaciones 
	
	
	//Test punto 5A save
	@Test
	public void testSaveNoValidWeight() {
		setupProductTest();
		Product product = productRepository.findById(1).get();
		
		
		Productcategory pc = productCategoryRepository.findById(1).get();
		Productsubcategory psc = productSubCategoryRepository.findById(1).get();
		
		product.setProductnumber("12");
		product.setWeight(0);
		product.setSize(2);
		
		try {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date date1 = (Date) df.parse("24/10/2021");
			Date date2 = (Date) df.parse("24/11/2021");
			long time1 = date1.getTime();
			long time2 = date2.getTime();
			Timestamp sellstart = new Timestamp(time1);
			Timestamp sellend = new Timestamp(time2);
			product.setSellstartdate(sellstart);
			product.setSellenddate(sellend);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		
		assertThrows(IllegalArgumentException.class, () -> productService.save(product, 1, 1));

	
		
		verify(productRepository, atLeastOnce()).findById(1);
		
	}
	
	
	//Test punto 5A save
	@Test
	public void testSaveNoValidSize() {
		setupProductTest();
		Product product = productRepository.findById(1).get();
	
		
		Productcategory pc = productCategoryRepository.findById(1).get();
		Productsubcategory psc = productSubCategoryRepository.findById(1).get();
		
		product.setProductnumber("12");
		product.setWeight(2);
		product.setSize(0);
		
		try {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date date1 = (Date) df.parse("24/10/2021");
			Date date2 = (Date) df.parse("24/11/2021");
			long time1 = date1.getTime();
			long time2 = date2.getTime();
			Timestamp sellstart = new Timestamp(time1);
			Timestamp sellend = new Timestamp(time2);
			product.setSellstartdate(sellstart);
			product.setSellenddate(sellend);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		
		assertThrows(IllegalArgumentException.class, () -> productService.save(product, 1, 1));

	
		
		verify(productRepository, atLeastOnce()).findById(1);
		
	}
	
	
	//Test punto 5A save
	@Test
	public void testSaveNoValidProductNumber() {
		setupProductTest();
		Product product = productRepository.findById(1).get();
		
//		when(productRepository.findById(1)).thenReturn(Optional.of(product));
		
		Productcategory pc = productCategoryRepository.findById(1).get();
		Productsubcategory psc = productSubCategoryRepository.findById(1).get();
		
		product.setProductnumber("");
		product.setWeight(1);
		product.setSize(2);
		
		try {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date date1 = (Date) df.parse("24/10/2021");
			Date date2 = (Date) df.parse("24/11/2021");
			long time1 = date1.getTime();
			long time2 = date2.getTime();
			Timestamp sellstart = new Timestamp(time1);
			Timestamp sellend = new Timestamp(time2);
			product.setSellstartdate(sellstart);
			product.setSellenddate(sellend);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		assertThrows(IllegalArgumentException.class, () -> productService.save(product, 1, 1));

	
		
		verify(productRepository, atLeastOnce()).findById(1);
		

		
	}
	
	
	//Test punto 5A save
		@Test
		public void testSaveNoValidDates() {
			setupProductTest();
			Product product = productRepository.findById(1).get();
		
			
			Productcategory pc = productCategoryRepository.findById(1).get();
			Productsubcategory psc = productSubCategoryRepository.findById(1).get();
			
			product.setProductnumber("101");
			product.setWeight(1);
			product.setSize(2);
			
			try {
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				Date date1 = (Date) df.parse("24/10/2021");
				Date date2 = (Date) df.parse("24/11/2021");
				long time1 = date1.getTime();
				long time2 = date2.getTime();
				Timestamp sellstart = new Timestamp(time1);
				Timestamp sellend = new Timestamp(time2);
				product.setSellstartdate(sellend);
				product.setSellenddate(sellstart);

			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			assertThrows(IllegalArgumentException.class, () -> productService.save(product, 1, 1));

		
			
			verify(productRepository, atLeastOnce()).findById(1);
			

			
		}
	
	
		
		
		


		
		



	
} //end of class
