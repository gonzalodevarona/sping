package com.gonza.taller.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gonza.taller.model.prod.Productsubcategory;
import com.gonza.taller.model.prod.Unitmeasure;
import com.gonza.taller.repository.UnitmeasureRepository;

@Service
public class UnitmeasureServiceImp implements UnitmeasureService{
	
	private UnitmeasureRepository unitmeasureRepository;

	@Autowired
	public UnitmeasureServiceImp(UnitmeasureRepository unitmeasureRepository) {
		this.unitmeasureRepository = unitmeasureRepository;
	}

	@Override
	public void save(Unitmeasure user) {
		unitmeasureRepository.save(user);

	}
	
	@Override
	public Optional<Unitmeasure> findById(int id) {

		return unitmeasureRepository.findById(id);
	}

	@Override
	public Iterable<Unitmeasure> findAll() {
		return unitmeasureRepository.findAll();
	}

	@Override
	public void delete(Unitmeasure unitmeasure) {
		unitmeasureRepository.delete(unitmeasure);
		
	}

}
