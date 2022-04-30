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

import org.junit.jupiter.api.BeforeAll;
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
import com.gonza.taller.model.prod.Productsubcategory;
import com.gonza.taller.repository.LocationRepository;
import com.gonza.taller.repository.ProductCategoryRepository;
import com.gonza.taller.repository.ProductRepository;
import com.gonza.taller.repository.ProductSubCategoryRepository;
import com.gonza.taller.service.LocationServiceImp;
import com.gonza.taller.service.ProductServiceImp;

@SpringBootTest(classes = Taller1Application.class)
@ExtendWith(SpringExtension.class)
public class Taller1LocationApplicationTests {
	
	// Mocks punto B
	@Mock
	private LocationRepository locationRepository;
	
	@InjectMocks
	private LocationServiceImp locationService;
	
	@Autowired
	public Taller1LocationApplicationTests(LocationServiceImp locationService) {
		this.locationService = locationService;
	}

	
	
	public void setupLocationTest() {
		
		Location location= new Location();
		when(locationRepository.findById(1)).thenReturn(Optional.of(location));
	
	}
		
		
		

	//Test punto 5B save
	@Test
	public void testSave() {
		setupLocationTest();
		Location location = new Location();
		when(locationRepository.findById(1)).thenReturn(Optional.of(location));
		
		location.setAvailability(new BigDecimal("2"));
		location.setCostrate(new BigDecimal("0.5"));
		location.setLocationid(1);
		location.setName("Ibiza");
		
		
		try {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date date1 = (Date) df.parse("24/10/2021");
			
			long time1 = date1.getTime();
			
			Timestamp mofifiedDate = new Timestamp(time1);
			
			location.setModifieddate(mofifiedDate);
			

		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		locationService.save(location);

		Optional<Location> locationEntity = locationRepository.findById(1);
		Location theLocation = locationEntity.get();
		

		assertFalse(theLocation.getName().length()<5);
	
		assertTrue(theLocation.getAvailability().compareTo(new BigDecimal("1")) == 1);
		assertTrue(theLocation.getAvailability().compareTo(new BigDecimal("10")) == -1);
		
		assertTrue(theLocation.getCostrate().compareTo(new BigDecimal("0")) == 1);
		assertTrue(theLocation.getCostrate().compareTo(new BigDecimal("1")) == -1);
		
		verify(locationRepository).save(location);
		verify(locationRepository).findById(1);

		
	}
	
	

	//Test punto 5B edit
	@Test
	public void testEdit() {
			
		setupLocationTest();
		
		try {
			Location theLocation = new Location();
			
			String locationName1 = "Cali"; // este es el argumento invalido
			
			

			theLocation.setName(locationName1);
			theLocation.setAvailability(new BigDecimal("3"));
			theLocation.setCostrate(new BigDecimal("0.2"));
			
			assertThrows(RuntimeException.class, () -> locationService.edit(theLocation));
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ahora si se edita el producto con argumentos validos
		
		try {
			Location theLocation = new Location();
			
			theLocation.setLocationid(1);
			
			when(locationRepository.findById(1)).thenReturn(Optional.of(theLocation));
			
			
			String locationName = "Ibiza";
			BigDecimal locationCostrate = new BigDecimal("0.5");
			BigDecimal locationAvailability = new BigDecimal("4");
			
			theLocation.setAvailability(locationAvailability);
			theLocation.setCostrate(locationCostrate);
			theLocation.setName(locationName);
			
			
			locationService.edit(theLocation);

			Optional<Location> theLocationOptional = locationRepository.findById(1);

			Location theLocationEntity = theLocationOptional.get();
			
			assertEquals(locationName, theLocationEntity.getName());
			assertEquals(locationCostrate, theLocationEntity.getCostrate());
			assertEquals(locationAvailability, theLocationEntity.getAvailability());
			

			
			verify(locationRepository, VerificationModeFactory.times(2)).findById(01);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	



		
} //end of class
