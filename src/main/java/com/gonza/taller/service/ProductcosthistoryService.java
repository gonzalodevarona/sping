package com.gonza.taller.service;

import java.util.Optional;

import com.gonza.taller.model.prod.Productcosthistory;

public interface ProductcosthistoryService {
	
	public void save(Productcosthistory productcosthistory, Integer productId);
	
	public void edit(Productcosthistory productcosthistory, Integer productId);
	
	public Iterable<Productcosthistory> findAll();
	
	public Optional<Productcosthistory> findById(int id);
	
	public void delete(Productcosthistory productcosthistory);

}
