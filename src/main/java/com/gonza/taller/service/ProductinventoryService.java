package com.gonza.taller.service;

import java.util.Optional;

import com.gonza.taller.model.prod.Product;
import com.gonza.taller.model.prod.Productinventory;

public interface ProductinventoryService {
	
	public void save(Productinventory productinventory, Integer productId, Integer locationId);
	public void edit(Productinventory productinventory, Integer productId, Integer locationId);
	public Iterable<Productinventory> findAll();
	public Optional<Productinventory> findById(int id);
	public void delete(Productinventory productinventory);

}
