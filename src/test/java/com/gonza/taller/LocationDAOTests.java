package com.gonza.taller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gonza.taller.dao.LocationDAO;
import com.gonza.taller.model.prod.Location;
import com.gonza.taller.model.prod.Product;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Taller1Application.class)
@Rollback(false)
public class LocationDAOTests {
	
	@Autowired
    private LocationDAO locationDAO;

   

    private Location location;

   
    @Autowired
    public LocationDAOTests(LocationDAO locationDAO) {
        this.locationDAO = locationDAO;
    }
    
	public void createLocation() {
		location = new Location();
		location.setName("Yonaguni");
		location.setAvailability(new BigDecimal(2));
		location.setCostrate(new BigDecimal(0.3));
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Test
    @Order(1)
    public void saveLocationTest() {

		createLocation();
		locationDAO.save(location);
		
        // deberían haber 2 locaciones puesto que el primero fue guardado desde el commandlinerunner
		

        assertEquals(2, locationDAO.findAll().size());
        

    }
    

    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Test
    @Order(2)
    public void findByIdTest() {
    	assertEquals("Ibiza", locationDAO.findById(1).get().getName());
    	
    }
    
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Test
    @Order(3)
    public void deleteLocationTest() {
    	
    	createLocation();
		locationDAO.save(location);

    	locationDAO.delete(locationDAO.findById(3).get());
        //deberia haber 2 location porque el que cree previamente lo acabé de borrar
        assertEquals(2, locationDAO.findAll().size());
       
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Test
    @Order(4)
    public void editProductTest() {

    	createLocation();
		locationDAO.save(location);
    	
    	
    	assertEquals("Yonaguni", locationDAO.findById(2).get().getName());
    	

    	Location matter = locationDAO.findById(2).get();
    	
    	matter.setName("Dakiti");
    	
    	locationDAO.update(matter);
    	
    	assertEquals("Dakiti", locationDAO.findById(2).get().getName());
        
    }
    

} //end of class
