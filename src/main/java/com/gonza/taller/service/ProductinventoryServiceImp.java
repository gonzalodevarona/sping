package com.gonza.taller.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gonza.taller.model.prod.Product;
import com.gonza.taller.model.prod.Location;
import com.gonza.taller.model.prod.Productcategory;
import com.gonza.taller.model.prod.Productinventory;
import com.gonza.taller.model.prod.Productsubcategory;
import com.gonza.taller.repository.LocationRepository;
import com.gonza.taller.repository.ProductInventoryRepository;
import com.gonza.taller.repository.ProductRepository;

@Service
public class ProductinventoryServiceImp implements ProductinventoryService {
	
	
	
	private ProductInventoryRepository productinventoryRepository;
	private ProductRepository productRepository;
	private LocationRepository locationRepository;
	
	
	
	public ProductinventoryServiceImp(ProductInventoryRepository productinventoryRepository,
			ProductRepository productRepository,
			LocationRepository locationRepository) {
		
		this.productinventoryRepository = productinventoryRepository;
		this.productRepository = productRepository;
		this.locationRepository = locationRepository;
	}
	
	@Override
	public void save(Productinventory productInventory, Integer productId, Integer locationId) {
		
		Optional<Product> product = productRepository.findById(productId);
		Optional<Location> location = locationRepository.findById(locationId);
		
		
		if (productInventory == null) {
			throw new RuntimeException();
		} else if (product.isEmpty()) {
			throw new RuntimeException();
		} else if (location.isEmpty()) {
			throw new RuntimeException();
			
		} else if (productInventory.getQuantity() < 0) {
			throw new IllegalArgumentException("Error: The product inventory quantity can not be less than zero");
		} else {
			

			productInventory.setLocation(location.get());
			productInventory.setProduct(product.get());
			
			productinventoryRepository.save(productInventory);
		}
	}
	
	@Override	
	public void edit(Productinventory productInventory, Integer productId, Integer locationId) {
		
		Optional<Product> product = productRepository.findById(productId);
		Optional<Location> location = locationRepository.findById(locationId);
		
		
		if (productInventory == null) {
			throw new RuntimeException();
		} else if (product.isEmpty()) {
			throw new RuntimeException();
		} else if (location.isEmpty()) {
			throw new RuntimeException();
			
		} else if (productInventory.getQuantity() < 0) {
			throw new IllegalArgumentException("Error: The product inventory quantity can not be less than zero");
		} else {
			
			
			productInventory.setLocation(location.get());
			productInventory.setProduct(product.get());
			
			productinventoryRepository.save(productInventory);
		}
	}

	@Override
	public Iterable<Productinventory> findAll() {
		return productinventoryRepository.findAll();
	}
	
	@Override
	public Optional<Productinventory> findById(int id){
		return productinventoryRepository.findById(id);
	}
	
	@Override
	public void delete(Productinventory productinventory) {
		productinventoryRepository.delete(productinventory);
		
	}
	
	

} //end of class
