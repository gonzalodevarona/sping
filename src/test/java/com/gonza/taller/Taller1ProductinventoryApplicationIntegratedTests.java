package com.gonza.taller;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.gonza.taller.dao.ProductDAO;
import com.gonza.taller.model.prod.Location;
import com.gonza.taller.model.prod.Product;
import com.gonza.taller.model.prod.Productcosthistory;
import com.gonza.taller.model.prod.Productinventory;
import com.gonza.taller.repository.LocationRepository;
import com.gonza.taller.repository.ProductCostHistoryRepository;
import com.gonza.taller.repository.ProductInventoryRepository;

import com.gonza.taller.service.ProductcosthistoryServiceImp;
import com.gonza.taller.service.ProductinventoryServiceImp;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
public class Taller1ProductinventoryApplicationIntegratedTests {
	
	private ProductinventoryServiceImp productinventoryServiceImp;
	private ProductInventoryRepository productInventoryRepository;
	private Productinventory productinventoryEntity;
	
	private ProductDAO productDAO;
	private Product productEntity;
	
	private LocationRepository locationRepository;
	private Location locationEntity;

	@Autowired
	public Taller1ProductinventoryApplicationIntegratedTests(ProductinventoryServiceImp productinventoryServiceImp,
																ProductInventoryRepository productInventoryRepository
																) {
		

		this.productinventoryServiceImp = productinventoryServiceImp;
		this.productInventoryRepository = productInventoryRepository;
		this.productDAO = productDAO;
		this.locationRepository = locationRepository;
		

	}

} //end of class
