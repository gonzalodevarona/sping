package com.gonza.taller.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gonza.taller.model.prod.Location;
import com.gonza.taller.model.prod.Product;
import com.gonza.taller.model.prod.Productcosthistory;



@Repository

public class ProductcosthistoryDAO implements Dao<Productcosthistory>{
	
	@PersistenceContext
	private EntityManager entityManager;
		
	@Override
	@Transactional
	public void save(Productcosthistory productcosthistory) {
		entityManager.merge(productcosthistory);
	}
	
	@Override
	public Optional<Productcosthistory> findById(int id){
		
		return Optional.ofNullable(entityManager.find(Productcosthistory.class, id));
		
	}
	
	@Override
	@Transactional
	public void delete(Productcosthistory productcosthistory) {
		entityManager.remove(productcosthistory);
	}
	
	@Override
	public List<Productcosthistory> findAll() {
		Query query = entityManager.createQuery("SELECT a FROM Productcosthistory a");
		return query.getResultList();
	}
	
	public List<Productcosthistory> findAllByProductId(int id) {
		Query query = entityManager.createQuery("SELECT a FROM Product a WHERE a.product.productid = :id");
		query.setParameter("id", id);
		return query.getResultList();
	}
	
	public List<Productcosthistory> findAllByStandardcost(double cost) {
		Query query = entityManager.createQuery("SELECT a FROM Product a WHERE a.standardcost = :cost");
		query.setParameter("cost", cost);
		return query.getResultList();
	}

	@Override
	@Transactional
	public void update(Productcosthistory productcosthistory) {
		entityManager.merge(productcosthistory);
		
	}
	
}