package com.gonza.taller.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gonza.taller.model.prod.Productcategory;
import com.gonza.taller.repository.ProductCategoryRepository;


@Service
public class ProductcategoryServiceImp implements ProductcategoryService {

	private ProductCategoryRepository productCategoryRepository;

	@Autowired
	public ProductcategoryServiceImp(ProductCategoryRepository productCategoryRepository) {
		this.productCategoryRepository = productCategoryRepository;
	}

	public void save(Productcategory user) {
		productCategoryRepository.save(user);

	}

	public Optional<Productcategory> findById(int id) {

		return productCategoryRepository.findById(id);
	}

	public Iterable<Productcategory> findAll() {
		return productCategoryRepository.findAll();
	}

	public void delete(Productcategory productcategory) {
		productCategoryRepository.delete(productcategory);
		
	}
}
