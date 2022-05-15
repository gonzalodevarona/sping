package com.gonza.taller.service;

import java.util.Optional;

import com.gonza.taller.model.prod.Productsubcategory;
import com.gonza.taller.model.prod.Unitmeasure;

public interface UnitmeasureService {
	
	public void save(Unitmeasure unitmeasure);

	public Optional<Unitmeasure> findById(int id);

	public Iterable<Unitmeasure> findAll();

	public void delete(Unitmeasure unitmeasure);

}
