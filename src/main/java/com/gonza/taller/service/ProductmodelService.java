package com.gonza.taller.service;

import java.util.Optional;

import com.gonza.taller.model.prod.Productmodel;


public interface ProductmodelService {
	
	public void save(Productmodel productmodel);

	public Optional<Productmodel> findById(int id);

	public Iterable<Productmodel> findAll();

	public void delete(Productmodel productmodel);

}
