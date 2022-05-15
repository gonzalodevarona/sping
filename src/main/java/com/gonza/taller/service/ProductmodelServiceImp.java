package com.gonza.taller.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gonza.taller.model.prod.Productmodel;
import com.gonza.taller.repository.ProductmodelRepository;

@Service
public class ProductmodelServiceImp implements  ProductmodelService{
	
	private ProductmodelRepository productmodelRepository;

	@Autowired
	public ProductmodelServiceImp(ProductmodelRepository productmodelRepository) {
		this.productmodelRepository = productmodelRepository;
	}

	@Override
	public void save(Productmodel user) {
		productmodelRepository.save(user);

	}

	@Override
	public Optional<Productmodel> findById(int id) {

		return productmodelRepository.findById(id);
	}

	@Override
	public Iterable<Productmodel> findAll() {
		return productmodelRepository.findAll();
	}

	@Override
	public void delete(Productmodel productmodel) {
		productmodelRepository.delete(productmodel);
		
	}

}
