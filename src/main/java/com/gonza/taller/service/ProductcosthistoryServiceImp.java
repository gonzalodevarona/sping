package com.gonza.taller.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gonza.taller.model.prod.Location;
import com.gonza.taller.model.prod.Product;
import com.gonza.taller.model.prod.Productcategory;
import com.gonza.taller.model.prod.Productcosthistory;
import com.gonza.taller.repository.ProductCostHistoryRepository;
import com.gonza.taller.repository.ProductRepository;

@Service
public class ProductcosthistoryServiceImp implements ProductcosthistoryService{
	
	private ProductCostHistoryRepository productcosthistoryRepository;
	private ProductRepository productRepository;
	
	public ProductcosthistoryServiceImp(ProductCostHistoryRepository productcosthistoryRepository,
			ProductRepository productRepository){
		
		this.productcosthistoryRepository = productcosthistoryRepository;
		this.productRepository = productRepository;
	}

	@Override
	public void save(Productcosthistory productcosthistory, Integer productId) {
		
		Optional<Product> product = productRepository.findById(productId);
		
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
			
			productcosthistoryRepository.save(productcosthistory);
		
		}
		
	}
	
	@Override
	public void edit(Productcosthistory productcosthistory, Integer productId) {
		
		Optional<Product> product = productRepository.findById(productId);
		
	
		 
		LocalDate today = LocalDate.now();
		
		
		if (productcosthistory == null) {
			throw new RuntimeException();
			
		} else {
			Optional<Productcosthistory> pch = productcosthistoryRepository.findById(productcosthistory.getId());
			
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
				
				 productcosthistoryRepository.save(productcosthistoryEntity);
			
			 	}
		}
		
	}
	
	
	@Override
	public Iterable<Productcosthistory> findAll() {
		return productcosthistoryRepository.findAll();
	}
	
	@Override
	public Optional<Productcosthistory> findById(int id){
		return productcosthistoryRepository.findById(id);
	}
	
	@Override
	public void delete(Productcosthistory location) {
		productcosthistoryRepository.delete(location);
		
	}
	
	
	
	
} //end of class
