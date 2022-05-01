package com.gonza.taller.service;

import java.util.Optional;

import com.gonza.taller.model.prod.Productsubcategory;



public interface ProductsubcategoryService {
	
	public void save(Productsubcategory productsubcategory);

	public Optional<Productsubcategory> findById(int id);

	public Iterable<Productsubcategory> findAll();

	public void delete(Productsubcategory productsubcategory);

}
