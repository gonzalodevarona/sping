package com.gonza.taller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
	public void testSaveGood() {
		setupLocationTest();
		Location location = new Location();
		when(locationRepository.findById(1)).thenReturn(Optional.of(location));
		
		location.setAvailability(new BigDecimal("2"));
		location.setCostrate(new BigDecimal("0.5"));
		location.setLocationid(1);
		location.setName("Ibiza");
		
		
		LocalDate mofifiedDate = LocalDate.of(2021, 10, 24);
		
		
		location.setModifieddate(mofifiedDate);
		

		
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
	
	//Test punto 5A save
	@Test
	public void testSaveBadAvailability() {
		setupLocationTest();
		Location location = new Location();
		when(locationRepository.findById(1)).thenReturn(Optional.of(location));
		
		location.setAvailability(new BigDecimal("0"));
		location.setCostrate(new BigDecimal("0.5"));
		location.setLocationid(1);
		location.setName("Ibiza");
		
		
		LocalDate mofifiedDate = LocalDate.of(2021, 10, 24);
		
		
		location.setModifieddate(mofifiedDate);
		
		
		
		
		assertThrows(IllegalArgumentException.class, () -> locationService.save(location));

	
	}
	
	
	@Test
	public void testSaveBadCostrate() {
		setupLocationTest();
		Location location = new Location();
		when(locationRepository.findById(1)).thenReturn(Optional.of(location));
		
		location.setAvailability(new BigDecimal("2"));
		location.setCostrate(new BigDecimal("-1"));
		location.setLocationid(1);
		location.setName("Ibiza");
		
		
		LocalDate mofifiedDate = LocalDate.of(2021, 10, 24);
		
		
		location.setModifieddate(mofifiedDate);
		
		
		
		assertThrows(IllegalArgumentException.class, () -> locationService.save(location));

	
	}
	
	
	@Test
	public void testSaveBadName() {
		setupLocationTest();
		Location location = new Location();
		when(locationRepository.findById(1)).thenReturn(Optional.of(location));
		
		location.setAvailability(new BigDecimal("2"));
		location.setCostrate(new BigDecimal("-1"));
		location.setLocationid(1);
		location.setName("Cali");
		
		
		LocalDate mofifiedDate = LocalDate.of(2021, 10, 24);
		
		
		location.setModifieddate(mofifiedDate);
		
		
		
		
		assertThrows(IllegalArgumentException.class, () -> locationService.save(location));

	
	}
	
	

	//Test punto 5B edit
	@Test
	public void testEditGood() {
			
		setupLocationTest();
		
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
	
	
	@Test
	public void testEditBadAvailability() {
		setupLocationTest();
		Location location = new Location();
		when(locationRepository.findById(1)).thenReturn(Optional.of(location));
		
		location.setAvailability(new BigDecimal("0"));
		location.setCostrate(new BigDecimal("0.5"));
		location.setLocationid(1);
		location.setName("Ibiza");
		
		
		LocalDate mofifiedDate = LocalDate.of(2021, 10, 24);
		
		
		location.setModifieddate(mofifiedDate);
		
		
		
		
		assertThrows(IllegalArgumentException.class, () -> locationService.edit(location));

	
	}
	
	
	@Test
	public void testEditBadCostrate() {
		setupLocationTest();
		Location location = new Location();
		when(locationRepository.findById(1)).thenReturn(Optional.of(location));
		
		location.setAvailability(new BigDecimal("2"));
		location.setCostrate(new BigDecimal("-1"));
		location.setLocationid(1);
		location.setName("Ibiza");
		
		
		LocalDate mofifiedDate = LocalDate.of(2021, 10, 24);
		
		
		location.setModifieddate(mofifiedDate);
		
		
		
		
		assertThrows(IllegalArgumentException.class, () -> locationService.edit(location));

	
	}
	
	
	@Test
	public void testEditBadName() {
		setupLocationTest();
		Location location = new Location();
		when(locationRepository.findById(1)).thenReturn(Optional.of(location));
		
		location.setAvailability(new BigDecimal("2"));
		location.setCostrate(new BigDecimal("-1"));
		location.setLocationid(1);
		location.setName("Cali");
		
		
		LocalDate mofifiedDate = LocalDate.of(2021, 10, 24);
		
		
		location.setModifieddate(mofifiedDate);
		
		
		
		
		assertThrows(IllegalArgumentException.class, () -> locationService.edit(location));

	
	}

	



		
} //end of class
