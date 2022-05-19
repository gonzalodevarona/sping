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
import java.time.LocalDate;
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

import com.gonza.taller.dao.ProductDAO;
import com.gonza.taller.model.prod.Product;
import com.gonza.taller.model.prod.Productcategory;
import com.gonza.taller.model.prod.Productsubcategory;
import com.gonza.taller.repository.ProductCategoryRepository;

import com.gonza.taller.repository.ProductSubCategoryRepository;
import com.gonza.taller.service.ProductServiceImp;


@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = Taller1PruebasApplication.class)
public class Taller1ProductApplicationIntegratedTests {
	
	private ProductServiceImp productService;
	private ProductDAO productDAO;
	private Product productEntity;
	
	private Productsubcategory productsubcategoryEntity;
	private Productcategory productcategoryEntity;
	
	private ProductSubCategoryRepository productSubcategoryRepository;
	private ProductCategoryRepository productcategoryRepository;
	
	@Autowired
	public Taller1ProductApplicationIntegratedTests(ProductServiceImp productService,
													ProductDAO productDAO,
													ProductSubCategoryRepository productSubcategoryRepository,
													ProductCategoryRepository productcategoryRepository) {

		this.productService = productService;
		this.productDAO = productDAO;
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


		LocalDate sellstart = LocalDate.of(2021, 10, 24);
		LocalDate sellend = LocalDate.of(2021, 11, 24);
		productEntity.setSellstartdate(sellstart);
		productEntity.setSellenddate(sellend);
		
		
		productEntity.setProductnumber("2");
		productEntity.setWeight(2);
		productEntity.setSize(4);

		productService.save(productEntity, productcategoryEntity.getProductcategoryid(),
				productsubcategoryEntity.getProductsubcategoryid());

		Optional<Product> theProductEntity = productDAO.findById(productEntity.getProductid()); // me devuelve a p

		Product theProduct = theProductEntity.get();
		
		assertFalse(theProduct.getProductnumber().isEmpty());
		assertTrue(theProduct.getWeight() > 0);
		assertTrue(theProduct.getSize() > 0);
		assertTrue(theProduct.getSellstartdate().isBefore(theProduct.getSellenddate()));

	}
	
	
	// Punto 5A: Test para el servicio de editar un producto
	@Test
	@Order(2)
	public void testEditIntegrated() {

		// si se edita un producto con argumentos invalidos, salta una excepcion

		String productNumber = "1010";  
		

		productEntity.setProductnumber(productNumber);
		LocalDate sellstart = LocalDate.of(2021, 10, 23);
		LocalDate sellend = LocalDate.of(2021, 11, 24);
		productEntity.setSellstartdate(sellstart);
		productEntity.setSellenddate(sellend);
		
		productEntity.setWeight(-2); //argumento invalido
		productEntity.setSize(2);
		
		assertThrows(IllegalArgumentException.class,
				() -> productService.edit(productEntity,
						productEntity.getProductsubcategory().getProductcategory().getProductcategoryid(),
						productEntity.getProductsubcategory().getProductsubcategoryid()));

		// ahora si se edita el producto con argumentos validos
		
			String productNumber1 = "1010";
			long weight = 2;
			long size = 3;
			

			productEntity.setProductnumber(productNumber1);
			
	
			LocalDate sellstart1 = LocalDate.of(2021, 9, 23);
			LocalDate sellend1 = LocalDate.of(2021, 10, 23);
			productEntity.setSellstartdate(sellstart1);
			productEntity.setSellenddate(sellend1);
			
			productEntity.setWeight(weight);
			productEntity.setSize(size);

			productService.edit(productEntity,
					productEntity.getProductsubcategory().getProductcategory().getProductcategoryid(),
					productEntity.getProductsubcategory().getProductsubcategoryid());

			Optional<Product> theProductEntity = productDAO.findById(productEntity.getProductid());
			Product theProduct =  theProductEntity.get();
			
			assertEquals(productNumber, theProduct.getProductnumber());
			assertEquals(weight, theProduct.getWeight());
			assertEquals(size, theProduct.getSize());
			assertEquals(sellstart, theProduct.getSellstartdate());
			assertEquals(sellend, theProduct.getSellenddate());



	}




	

} //end of class
