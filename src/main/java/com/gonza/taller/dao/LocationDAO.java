package com.gonza.taller.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gonza.taller.model.prod.Location;


@Repository

public class LocationDAO implements Dao<Location>{
	
	@PersistenceContext
	private EntityManager entityManager;
		
	@Override
	@Transactional
	public void save(Location location) {
		entityManager.merge(location);
	}
	
	@Override
	public Optional<Location> findById(int id){
		
		return Optional.ofNullable(entityManager.find(Location.class, id));
		
	}
	
	@Override
	public void delete(Location location) {
		entityManager.remove(location);
	}
	
	@Override
	public List<Location> findAll() {
		Query query = entityManager.createQuery("SELECT a FROM Location a");
		return query.getResultList();
	}

	@Override
	public void update(Location location) {
		entityManager.merge(location);
		
	}
	
}
