package com.gonza.taller.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gonza.taller.dao.LocationDAO;
import com.gonza.taller.model.prod.Location;
import com.gonza.taller.model.prod.Product;
import com.gonza.taller.model.prod.Productcategory;
import com.gonza.taller.model.prod.Productsubcategory;

import com.gonza.taller.repository.ProductCategoryRepository;
import com.gonza.taller.repository.ProductSubCategoryRepository;

@Service
public class LocationServiceImp implements LocationService{
	
	private com.gonza.taller.dao.LocationDAO locationDAO;
	
	public LocationServiceImp(LocationDAO locationDAO){
		
		this.locationDAO = locationDAO;
		
		
	}
	
	
	@Override
	public void save(Location location) {
		
		

		if (location == null) {
			throw new RuntimeException();
		
		} else if (location.getName().length() <5) {
			throw new IllegalArgumentException("Error: Location's name have less than 5 letters");
		
		} else if ((location.getAvailability().compareTo(new BigDecimal("1")) == -1) ||
				(location.getAvailability().compareTo(new BigDecimal("10")) == 1)) {
			throw new IllegalArgumentException("Error: Location's availability is not between 1 and 10");
		
		} else if ((location.getCostrate().compareTo(new BigDecimal("0")) == -1) ||
				(location.getCostrate().compareTo(new BigDecimal("1")) == 1)) {
			throw new IllegalArgumentException("Error: Location's availability is not between 1 and 10");
		
		}  else {
			
			
			
			
			locationDAO.save(location);
		
		}
	}


	@Override
	public void edit(Location location) {
		
		if (location == null) {
			throw new RuntimeException();
		} else {
			Optional<Location> l = locationDAO.findById(location.getLocationid());
			
			if (location == null) {
				throw new RuntimeException();
			
			} else if (location.getName().length() <5) {
				throw new IllegalArgumentException("Error: Location's name have less than 5 letters");
			
			} else if ((location.getAvailability().compareTo(new BigDecimal("1")) == -1) ||
					(location.getAvailability().compareTo(new BigDecimal("10")) == 1)) {
				throw new IllegalArgumentException("Error: Location's availability is not between 1 and 10");
			
			} else if ((location.getCostrate().compareTo(new BigDecimal("0")) == -1) ||
					(location.getCostrate().compareTo(new BigDecimal("1")) == 1)) {
				throw new IllegalArgumentException("Error: Location's availability is not between 1 and 10");
			
			}  else {
				
				Location locationEntity = l.get();
				
				locationEntity.setLocationid(location.getLocationid());
				locationEntity.setAvailability(location.getAvailability());
				locationEntity.setCostrate(location.getCostrate());
				locationEntity.setModifieddate(location.getModifieddate());
				locationEntity.setName(location.getName());
				locationEntity.setProductinventories(location.getProductinventories());
				locationEntity.setWorkorderroutings(location.getWorkorderroutings());

				locationDAO.save(locationEntity);

			}
		}
		
	}
	
	
	@Override
	public Iterable<Location> findAll() {
		return locationDAO.findAll();
	}
	
	@Override
	public Optional<Location> findById(int id){
		return locationDAO.findById(id);
	}
	
	@Override
	public void delete(Location location) {
		locationDAO.delete(location);
		
	}

	
	
	
} //end of class
