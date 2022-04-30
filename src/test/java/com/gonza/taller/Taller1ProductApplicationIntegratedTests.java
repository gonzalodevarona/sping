package com.gonza.taller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.gonza.taller.model.prod.Product;
import com.gonza.taller.model.prod.Productcategory;
import com.gonza.taller.model.prod.Productsubcategory;
import com.gonza.taller.repository.ProductCategoryRepository;
import com.gonza.taller.repository.ProductRepository;
import com.gonza.taller.repository.ProductSubCategoryRepository;
import com.gonza.taller.service.ProductServiceImp;


@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = Taller1PruebasApplication.class)
public class Taller1ProductApplicationIntegratedTests {
	
	private ProductServiceImp productService;
	private ProductRepository productRepository;
	private Product productEntity;
	
	private Productsubcategory productsubcategoryEntity;
	private Productcategory productcategoryEntity;
	
	private ProductSubCategoryRepository productSubcategoryRepository;
	private ProductCategoryRepository productcategoryRepository;
	
	@Autowired
	public Taller1ProductApplicationIntegratedTests(ProductServiceImp productService,
													ProductRepository productRepository,
													ProductSubCategoryRepository productSubcategoryRepository,
													ProductCategoryRepository productcategoryRepository) {

		this.productService = productService;
		this.productRepository = productRepository;
		this.productSubcategoryRepository = productSubcategoryRepository;
		this.productcategoryRepository = productcategoryRepository;

	}
	
	public void setupProductService() {
		productsubcategoryEntity = new Productsubcategory();
		productsubcategoryEntity.setName("Marketing");
		productsubcategoryEntity.setProductsubcategoryid(1);
		
		productsubcategoryEntity= productSubcategoryRepository.save(productsubcategoryEntity);

		productcategoryEntity = new Productcategory();
		productcategoryEntity.setName("Marketing");
		productcategoryEntity.setProductcategoryid(1);
		
		productcategoryEntity = productcategoryRepository.save(productcategoryEntity);

	}
	
	@Test
	@Order(1)
	public void testSaveIntegrated() {
		setupProductService();
		
		productEntity = new Product();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date date1;
		Date date2;
		try {
			date1 = df.parse("24/10/2021");
			date2 = df.parse("24/11/2021");
			long time1 = date1.getTime();
			long time2 = date2.getTime();
			Timestamp sellstart = new Timestamp(time1);
			Timestamp sellend = new Timestamp(time2);
			productEntity.setSellstartdate(sellstart);
			productEntity.setSellenddate(sellend);
		} catch (ParseException  e1) {
			e1.printStackTrace();
		}

		
		productEntity.setProductnumber("2");
		productEntity.setWeight(2);
		productEntity.setSize(4);

		productService.save(productEntity, productcategoryEntity.getProductcategoryid(),
				productsubcategoryEntity.getProductsubcategoryid());

		Optional<Product> theProductEntity = productRepository.findById(productEntity.getProductid()); // me devuelve a p

		Product theProduct = theProductEntity.get();
		
		assertFalse(theProduct.getProductnumber().isEmpty());
		assertTrue(theProduct.getWeight() > 0);
		assertTrue(theProduct.getSize() > 0);
		assertTrue(theProduct.getSellstartdate().before(theProduct.getSellenddate()));

	}
	
	
	// Punto 5A: Test para el servicio de editar un producto
	@Test
	@Order(2)
	public void testEditIntegrated() {

		// si se edita un producto con argumentos invalidos, salta una excepcion

		try {
			String productNumber = "1010";  
			
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date date1 = df.parse("23/10/2021");
			Date date2 = df.parse("24/11/2021");
			long time1 = date1.getTime();
			long time2 = date2.getTime();
			Timestamp sellstart = new Timestamp(time1);
			Timestamp sellend = new Timestamp(time2);

			productEntity.setProductnumber(productNumber);
			productEntity.setSellstartdate(sellstart);
			productEntity.setSellenddate(sellend);
			productEntity.setWeight(-2); //argumento invalido
			productEntity.setSize(2);
			
			assertThrows(IllegalArgumentException.class,
					() -> productService.edit(productEntity,
							productEntity.getProductsubcategory().getProductcategory().getProductcategoryid(),
							productEntity.getProductsubcategory().getProductsubcategoryid()));
		} catch (ParseException e) {

			e.printStackTrace();
		}

		// ahora si se edita el producto con argumentos validos
		try {
			String productNumber = "1010";
			long weight = 2;
			long size = 3;
			
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date date1 = df.parse("23/09/2020");
			Date date2 = df.parse("23/10/2020");
			long time1 = date1.getTime();
			long time2 = date2.getTime();
			Timestamp sellstart = new Timestamp(time1);
			Timestamp sellend = new Timestamp(time2);
			
			productEntity.setProductnumber(productNumber);
			
			productEntity.setSellstartdate(sellstart);
			productEntity.setSellenddate(sellend);
			productEntity.setWeight(weight);
			productEntity.setSize(size);

			productService.edit(productEntity,
					productEntity.getProductsubcategory().getProductcategory().getProductcategoryid(),
					productEntity.getProductsubcategory().getProductsubcategoryid());

			Optional<Product> theProductEntity = productRepository.findById(productEntity.getProductid());
			Product theProduct =  theProductEntity.get();
			
			assertEquals(productNumber, theProduct.getProductnumber());
			assertEquals(weight, theProduct.getWeight());
			assertEquals(size, theProduct.getSize());
			assertEquals(sellstart, theProduct.getSellstartdate());
			assertEquals(sellend, theProduct.getSellenddate());

		} catch (ParseException e) {

			e.printStackTrace();
		}

	}




	

} //end of class
