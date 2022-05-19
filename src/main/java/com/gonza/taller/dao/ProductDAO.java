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
import com.gonza.taller.model.prod.Productcosthistory;
import com.gonza.taller.model.prod.Productinventory;
import com.gonza.taller.model.prod.Productsubcategory;



@Repository

public class ProductDAO implements Dao<Product>{
	
	@PersistenceContext
	private EntityManager entityManager;
		
	@Transactional
	@Override
	public void save(Product product) {
		entityManager.merge(product);
	}
	
	@Override
	public Optional<Product> findById(int id){
		
		return Optional.ofNullable(entityManager.find(Product.class, id));
		
	}
	
	@Override
	@Transactional
	public void delete(Product product) {
		entityManager.remove(product);
	}
	
	@Override
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
	
	public List<Product> findAllByUnitMeasureCode(int code) {
		Query query = entityManager.createQuery("SELECT a FROM Product a WHERE a.unitmeasure1.unitmeasurecode = :code");
		query.setParameter("code", code);
		return query.getResultList();
		
		
	}

	@Override
	@Transactional
	public void update(Product product) {
		entityManager.merge(product);
		
	}
	
	
	public List<Product> findMoreThan2Productcosthistory() {
		Query query = entityManager.createQuery("SELECT a "
				+ "FROM Product a "
				+ "WHERE SIZE(a.productcosthistories) >= 2");
		
		return query.getResultList();
	}
	
	
//	public List<Object[]> specialQueryOne(Productsubcategory subcategory) {
//		String jpql = "SELECT p,COUNT(pi) "
//				+ "FROM Product p, Productinventory pi "
//				+ "WHERE pi MEMBER OF p.productinventories "
//				+ "AND pi.product.productsubcategory = :subcategory "
//				+ "AND pi.quantity >= 1 "
//				+ "ORDER BY pi.location";
//		
//		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
//		return 	query.setParameter("subcategory", subcategory).getResultList();
//	}
	
	public List<Product> specialQueryOne(Productsubcategory subcategory) {
		Query query = entityManager.createQuery("SELECT p,COUNT(pi) "
				+ "FROM Product p, Productinventory pi "
				+ "WHERE pi MEMBER OF p.productinventories "
				+ "AND pi.product.productsubcategory = :subcategory "
				+ "AND pi.quantity >= 1 "
				+ "ORDER BY pi.location");
		query.setParameter("subcategory", subcategory);
		return query.getResultList();
	}
	
	
	
	
	
} //end of class
