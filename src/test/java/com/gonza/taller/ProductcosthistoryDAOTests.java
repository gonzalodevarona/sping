package com.gonza.taller;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

}
