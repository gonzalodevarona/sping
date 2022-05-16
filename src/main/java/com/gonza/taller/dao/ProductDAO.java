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
	
	public List<Product> findAllByUnitMeasureCode(String code) {
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
		Query query = entityManager.createNativeQuery("SELECT Product.productnumber, Product.name Product.productid COUNT(Productcosthistory.id) AS NumberOfProductCostHistory\n"
				+ "FROM (Productcosthistory\n"
				+ "INNER JOIN Product ON Productcosthistory.id = Product.productcosthistories.id)\n"
				+ "GROUP BY Product.name\n"
				+ "WHERE COUNT(Productcosthistory.id) > 1;");
		return query.getResultList();
	}
	
//	Query query = entityManager.createQuery("SELECT a.productnumber, a.name , a.productid, COUNT(a.productid) AS NumberOfProductCostHistory "
//			+ "FROM (Productcosthistory "
//			+ "INNER JOIN Product a ON Productcosthistory.product.productid = a.productid) "
//			+ "GROUP BY a.name "
//			+ "WHERE COUNT(a.productid) > 1;");
//	
//	
	
//	public List<Product> findByProductSumInventory_orderByLocation(Productsubcategory subcategory) {
//		String jpql = "SELECT pro" + " FROM producto pro " + "WHERE pro.productinventories  "
//				+ "IN ( SELECT productinventory pir" + " FROM pro.productinventories  "
//				+ "WHERE  pir.product.productsubcategory = :subcategory" + "AND pir.quantity>= 1"
//				+ " ORDER BY pir.location";
//		
//		return entityManagerProduct.createQuery(jpql).getResultList();
//
//	}
	
	
	
} //end of class
