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
import com.gonza.taller.model.prod.Productinventory;
import com.gonza.taller.repository.LocationRepository;
import com.gonza.taller.repository.ProductInventoryRepository;
import com.gonza.taller.repository.ProductRepository;
import com.gonza.taller.service.LocationServiceImp;
import com.gonza.taller.service.ProductinventoryServiceImp;

@SpringBootTest(classes = Taller1Application.class)
//@ContextConfiguration(classes = Taller1Application.class)
@ExtendWith(SpringExtension.class)
public class Taller1ProductinventoryApplicationTests {
	
	// Mocks punto D
	@Mock
	private ProductInventoryRepository productinventoryRepository;
	
	@Mock
	private ProductRepository productRepository;
	
	@Mock
	private LocationRepository locationRepository;
	
	@InjectMocks
	private ProductinventoryServiceImp productinventoryService;
	
	@Autowired
	public Taller1ProductinventoryApplicationTests(ProductinventoryServiceImp productinventoryService) {
		
		this.productinventoryService = productinventoryService;
	}

	
	
	public void setupProductinventoryTest() {
		
		Productinventory productinventory = new Productinventory();
		when(productinventoryRepository.findById(1)).thenReturn(Optional.of(productinventory));
		
		Product product = new Product();
		when(productRepository.findById(1)).thenReturn(Optional.of(product));
		
		Location location = new Location();
		when(locationRepository.findById(1)).thenReturn(Optional.of(location));
	
	}
	
	

	//Test punto 5D save
	@Test
	public void testSave() {
		setupProductinventoryTest();
		Productinventory productinventory = new Productinventory();
		when(productinventoryRepository.findById(1)).thenReturn(Optional.of(productinventory));
		
		productinventory.setQuantity(5);
		
		Product p = productRepository.findById(1).get();
		
		Location l = locationRepository.findById(1).get();
		
		
		productinventoryService.save(productinventory, 1, 1);

		Optional<Productinventory> productinventoryEntity = productinventoryRepository.findById(1);
		Productinventory theProductinventory = productinventoryEntity.get();
		
	
		assertEquals(theProductinventory.getLocation(),l);
		assertEquals(theProductinventory.getProduct(),p);
		assertEquals(theProductinventory.getQuantity(),5);
		
	
		
		verify(productinventoryRepository).save(productinventory);
		verify(productinventoryRepository).findById(1);

		
	}
	

	//Test punto 5D edit
		@Test
	public void testEdit() {
		setupProductinventoryTest();
		// si se edita un inventario de producto con argumentos invalidos, salta una excepcion
		
		
		Productinventory productinventory = new Productinventory();
		
		Integer quantity = -2; // este es el argumento invalido
	
		
		assertThrows(RuntimeException.class, () -> productinventoryService.edit(productinventory, 1, 1));
		

		// ahora si se edita el inventario de producto con argumentos validos
		
		
		
		Productinventory theProductinventory = new Productinventory();
		
		when(productinventoryRepository.findById(1)).thenReturn(Optional.of(theProductinventory));
		
		Integer quantityValid = 2;
		
		theProductinventory.setQuantity(quantityValid);
		
		productinventoryService.edit(theProductinventory, 1, 1);

		Optional<Productinventory> theProductinventoryOptional = productinventoryRepository.findById(1);

		Productinventory theProductinventoryEntity = theProductinventoryOptional.get();
		
		assertEquals(quantityValid, theProductinventoryEntity.getQuantity());
		

		
		verify(productRepository, VerificationModeFactory.times(2)).findById(1);
			
		


	}

	
	

	
	
} //end of class
