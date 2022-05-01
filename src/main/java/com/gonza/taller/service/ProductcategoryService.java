package com.gonza.taller.service;

import java.util.Optional;

import com.gonza.taller.model.prod.Productcategory;
import com.gonza.taller.model.prod.Productsubcategory;

public interface ProductcategoryService {
	
	public void save(Productcategory productcategory);

	public Optional<Productcategory> findById(int id);

	public Iterable<Productcategory> findAll();

	public void delete(Productcategory productcategory);

}
