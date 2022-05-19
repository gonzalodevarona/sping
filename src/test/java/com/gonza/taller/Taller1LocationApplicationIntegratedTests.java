package com.gonza.taller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.gonza.taller.model.prod.Location;
import com.gonza.taller.repository.LocationRepository;
import com.gonza.taller.service.LocationServiceImp;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = Taller1PruebasApplication.class)
public class Taller1LocationApplicationIntegratedTests {
	
	private LocationServiceImp locationService;
	
	private LocationRepository locationRepository;
	
	private Location locationEntity;
	
	@Autowired
	public Taller1LocationApplicationIntegratedTests(LocationServiceImp locationService,
													LocationRepository locationRepository) {

		this.locationService = locationService;
		this.locationRepository = locationRepository;
		

	}
	
	public void setupProductService() {
		

	}
	
	@Test
	@Order(1)
	public void testSaveIntegrated() {
		setupProductService();
		
		locationEntity = new Location();

		locationEntity.setAvailability(new BigDecimal("7"));
		locationEntity.setCostrate(new BigDecimal("0.8"));
		locationEntity.setLocationid(1);
		locationEntity.setName("Venecia");
		

		locationService.save(locationEntity);

		Optional<Location> theLocationEntity = locationRepository.findById(locationEntity.getLocationid()); // me devuelve a p

		Location theLocation = theLocationEntity.get();
		
		assertFalse(theLocation.getName().length()<5);
		
		assertTrue(theLocation.getAvailability().compareTo(new BigDecimal("1")) == 1);
		assertTrue(theLocation.getAvailability().compareTo(new BigDecimal("10")) == -1);
		
		assertTrue(theLocation.getCostrate().compareTo(new BigDecimal("0")) == 1);
		assertTrue(theLocation.getCostrate().compareTo(new BigDecimal("1")) == -1);


	}
	
	

	
	@Test
	@Order(2)
	public void testEditIntegrated() {

		// si se edita un location con argumentos invalidos, salta una excepcion
		try {
			String locationNameFail = "Lollapalooza";
			BigDecimal availabilityFail = new BigDecimal("15"); //argumento invalido
			BigDecimal costrateFail = new BigDecimal("0.7");
			
			
			locationEntity.setAvailability(availabilityFail);
			locationEntity.setCostrate(costrateFail);
			locationEntity.setName(locationNameFail);
			
			assertThrows(IllegalArgumentException.class,
					() -> locationService.edit(locationEntity));
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ahora si se edita el location con argumentos validos
		
		String locationName = "Helsinki";
		BigDecimal availability = new BigDecimal("6.00");
		BigDecimal costrate = new BigDecimal("0.40");
		
		
		locationEntity.setAvailability(availability);
		locationEntity.setCostrate(costrate);
		locationEntity.setName(locationName);

		locationService.edit(locationEntity);

		Optional<Location> theLocationEntity = locationRepository.findById(locationEntity.getLocationid());
		Location theLocation =  theLocationEntity.get();
		
		assertEquals(locationName, theLocation.getName());
		assertEquals(availability, theLocation.getAvailability());
		assertEquals(costrate, theLocation.getCostrate());

	}
	
	
} //end of class
