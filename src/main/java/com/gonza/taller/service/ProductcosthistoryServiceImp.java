package com.gonza.taller.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gonza.taller.dao.ProductDAO;
import com.gonza.taller.dao.ProductcosthistoryDAO;
import com.gonza.taller.model.prod.Location;
import com.gonza.taller.model.prod.Product;
import com.gonza.taller.model.prod.Productcategory;
import com.gonza.taller.model.prod.Productcosthistory;
import com.gonza.taller.repository.ProductCostHistoryRepository;


@Service
public class ProductcosthistoryServiceImp implements ProductcosthistoryService{
	
	private ProductcosthistoryDAO productcosthistoryDAO;
	private ProductDAO productDAO;
	
	public ProductcosthistoryServiceImp(ProductcosthistoryDAO productcosthistoryDAO,
			ProductDAO productDAO){
		
		this.productcosthistoryDAO = productcosthistoryDAO;
		this.productDAO = productDAO;
	}

	@Override
	@Transactional
	public void save(Productcosthistory productcosthistory, Integer productId) {
		
		Optional<Product> product = productDAO.findById(productId);
		
		LocalDate today= LocalDate.now();
		 
		
		
		if (productcosthistory == null) {
			throw new RuntimeException();
			
		} else if (product.isEmpty()) {
			throw new RuntimeException();	
		
		} else if (today.isBefore(productcosthistory.getEnddate())) {
			throw new IllegalArgumentException("Error: Product's cost history end date must not be greater than today's date.");
		
		} else if (productcosthistory.getStandardcost().compareTo(new BigDecimal("0")) == -1) {
			throw new IllegalArgumentException("Error: Product historical standard cost is less than 0");
		
		} else {
		
			productcosthistory.setProduct(product.get());
			
			productcosthistoryDAO.save(productcosthistory);
		
		}
		
	}
	
	@Override
	@Transactional
	public void edit(Productcosthistory productcosthistory, Integer productId) {
		
		Optional<Product> product = productDAO.findById(productId);
		
	
		 
		LocalDate today = LocalDate.now();
		
		
		if (productcosthistory == null) {
			throw new RuntimeException();
			
		} else {
			Optional<Productcosthistory> pch = productcosthistoryDAO.findById(productcosthistory.getId());
			
			 if (pch.isEmpty()) {
				 throw new RuntimeException();	
		
			 } else if (product.isEmpty()) {
				 throw new RuntimeException();	
		
			 } else if (today.isBefore(productcosthistory.getEnddate())) {
				 throw new IllegalArgumentException("Error: Product's cost history end date must not be greater than today's date.");
		
			 } else if (pch.get().getStandardcost().compareTo(new BigDecimal("0")) == -1) {
				 throw new IllegalArgumentException("Error: Product historical standard cost is less than 0");
		
			 } else {
				 Productcosthistory productcosthistoryEntity = pch.get();
				
				 productcosthistoryEntity.setEnddate(productcosthistory.getEnddate());
				 productcosthistoryEntity.setId(productcosthistory.getId());
				 productcosthistoryEntity.setModifieddate(productcosthistory.getModifieddate());
				 productcosthistoryEntity.setStandardcost(productcosthistory.getStandardcost());
				 productcosthistoryEntity.setProduct(product.get());
				
				 productcosthistoryDAO.save(productcosthistoryEntity);
			
			 	}
		}
		
	}
	
	
	@Override
	public Iterable<Productcosthistory> findAll() {
		return productcosthistoryDAO.findAll();
	}
	
	@Override
	public Optional<Productcosthistory> findById(int id){
		return productcosthistoryDAO.findById(id);
	}
	
	@Override
	@Transactional
	public void delete(Productcosthistory location) {
		productcosthistoryDAO.delete(location);
		
	}
	
	
	
	
} //end of class
