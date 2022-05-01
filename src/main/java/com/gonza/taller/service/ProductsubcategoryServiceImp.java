package com.gonza.taller.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gonza.taller.model.prod.Productsubcategory;
import com.gonza.taller.repository.ProductSubCategoryRepository;


@Service
public class ProductsubcategoryServiceImp implements ProductsubcategoryService {

	private ProductSubCategoryRepository productSubCategoryRepository;

	@Autowired
	public ProductsubcategoryServiceImp(ProductSubCategoryRepository productSubCategoryRepository) {
		this.productSubCategoryRepository = productSubCategoryRepository;
	}

	public void save(Productsubcategory user) {
		productSubCategoryRepository.save(user);

	}

	public Optional<Productsubcategory> findById(int id) {

		return productSubCategoryRepository.findById(id);
	}

	public Iterable<Productsubcategory> findAll() {
		return productSubCategoryRepository.findAll();
	}

	public void delete(Productsubcategory productsubcategory) {
		productSubCategoryRepository.delete(productsubcategory);
		
	}
}
