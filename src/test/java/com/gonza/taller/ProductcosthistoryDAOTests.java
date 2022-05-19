package com.gonza.taller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

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
import com.gonza.taller.dao.ProductcosthistoryDAO;
import com.gonza.taller.model.prod.Location;
import com.gonza.taller.model.prod.Product;
import com.gonza.taller.model.prod.Productcosthistory;
import com.gonza.taller.service.ProductService;
import com.gonza.taller.service.ProductServiceImp;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Taller1Application.class)
@Rollback(false)
public class ProductcosthistoryDAOTests {
	
	@Autowired
    private ProductcosthistoryDAO productcosthistoryDAO;
	
	@Autowired
	private ProductService productService;

   

    private Productcosthistory productcosthistory;

   
    @Autowired
    public ProductcosthistoryDAOTests(ProductcosthistoryDAO productcosthistoryDAO,
    		ProductServiceImp productService) {
        this.productcosthistoryDAO = productcosthistoryDAO;
        this.productService = productService;
    }
    
	public void createProductcosthistory() {
		productcosthistory = new Productcosthistory();
		
		productcosthistory.setStandardcost(new BigDecimal("3"));
		
		Product productProccessed = productService.findById(1).get();
		productcosthistory.setProduct(productProccessed);
		
		productcosthistory.setEnddate(LocalDate.of(2021, 12, 21));
		
		
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Test
    @Order(1)
    public void saveProductcosthistoryTest() {

		createProductcosthistory();
		productcosthistoryDAO.save(productcosthistory);
		
        // deberían haber 2 locaciones puesto que el primero fue guardado desde el commandlinerunner
		

        assertEquals(2, productcosthistoryDAO.findAll().size());
        
    }
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Test
    @Order(2)
    public void findByIdTest() {
    	assertEquals(LocalDate.of(2021, 12, 21), productcosthistoryDAO.findById(1).get().getEnddate());	
    }
    
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Test
    @Order(3)
    public void deleteProductcosthistoryTest() {
    	
    	createProductcosthistory();
		productcosthistoryDAO.save(productcosthistory);

		productcosthistoryDAO.delete(productcosthistoryDAO.findById(2).get());
        //deberia haber 1 productcosthistory porque el que cree previamente lo acabé de borrar
        assertEquals(1, productcosthistoryDAO.findAll().size());
       
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Test
    @Order(4)
    public void editProductcosthistoryTest() {

    	createProductcosthistory();
		productcosthistoryDAO.save(productcosthistory);
    	
    	
    	assertEquals(LocalDate.of(2021, 12, 21), productcosthistoryDAO.findById(1).get().getEnddate());
    	

    	Productcosthistory matter = productcosthistoryDAO.findById(1).get();
    	
    	matter.setEnddate(LocalDate.of(2021, 12, 30));
    	
    	productcosthistoryDAO.update(matter);
    	
    	assertEquals(LocalDate.of(2021, 12, 30), productcosthistoryDAO.findById(1).get().getEnddate());
        
    }
	

} //end of class
