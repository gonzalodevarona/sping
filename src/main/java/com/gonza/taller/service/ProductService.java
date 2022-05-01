package com.gonza.taller.service;

import java.util.Optional;

import com.gonza.taller.model.prod.Product;


public interface ProductService {
	
	public void save(Product product, Integer prCategoryId, Integer prSCategoryId);
	public void edit(Product product, Integer prCategoryId, Integer prSCategoryId);
	public Iterable<Product> findAll();
	public Optional<Product> findById(int id);
	public void delete(Product product);

} //end of interface
