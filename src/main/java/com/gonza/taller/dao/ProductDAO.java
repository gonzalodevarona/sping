package com.gonza.taller.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gonza.taller.model.prod.Product;
import com.gonza.taller.model.prod.Productcategory;
import com.gonza.taller.model.prod.Productsubcategory;



@Repository

public class ProductDAO {
	
	@PersistenceContext
	private EntityManager entityManager;
		
	@Transactional
	public void save(Product product) {
		entityManager.merge(product);
	}
	
	public Optional<Product> findById(int id){
		
		return Optional.ofNullable(entityManager.find(Product.class, id));
		
	}
	
	public void delete(Product product) {
		entityManager.remove(product);
	}
	
	public List<Product> findAll() {
		Query query = entityManager.createQuery("SELECT a FROM Product a");
		return query.getResultList();
	}
	
	public List<Product> findAllBySubcategoryId(int id) {
		Query query = entityManager.createQuery("SELECT a FROM Product a WHERE a.productsubcategory.productsubcategoryid = :id");
		query.setParameter("id", id);
		return query.getResultList();
	}
	
	public List<Product> findAllByProductmodelId(int id) {
		Query query = entityManager.createQuery("SELECT a FROM Product a WHERE a.productmodel.productmodelid = :id");
		query.setParameter("id", id);
		return query.getResultList();
	}
	
	public List<Product> findAllByUnitMeasureCode(String code) {
		Query query = entityManager.createQuery("SELECT a FROM Product a WHERE a.unitmeasure1.unitmeasurecode = :code");
		query.setParameter("code", code);
		return query.getResultList();
	}
	
	
} //end of class
