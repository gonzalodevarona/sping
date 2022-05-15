package com.gonza.taller.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gonza.taller.model.prod.Location;
import com.gonza.taller.model.prod.Productcosthistory;
import com.gonza.taller.model.prod.Productinventory;


@Repository

public class ProductinventoryDAO implements Dao<Productinventory>{
	
	@PersistenceContext
	private EntityManager entityManager;
		
	@Transactional
	@Override
	public void save(Productinventory productinventory) {
		entityManager.merge(productinventory);
	}
	
	@Override
	public Optional<Productinventory> findById(int id){
		
		return Optional.ofNullable(entityManager.find(Productinventory.class, id));
		
	}
	
	@Override
	public void delete(Productinventory productinventory) {
		entityManager.remove(productinventory);
	}
	
	@Override
	public List<Productinventory> findAll() {
		Query query = entityManager.createQuery("SELECT a FROM Productinventory a");
		return query.getResultList();
	}
	
	public List<Productcosthistory> findAllByProductId(int id) {
		Query query = entityManager.createQuery("SELECT a FROM Product a WHERE a.product.productid = :id");
		query.setParameter("id", id);
		return query.getResultList();
	}
	
	public List<Productcosthistory> findAllByLocationId(int id) {
		Query query = entityManager.createQuery("SELECT a FROM Product a WHERE a.location.locationid = :id");
		query.setParameter("id", id);
		return query.getResultList();
	}

	@Override
	public void update(Productinventory productinventory) {
		entityManager.merge(productinventory);
		
	}
	
} //end of class

