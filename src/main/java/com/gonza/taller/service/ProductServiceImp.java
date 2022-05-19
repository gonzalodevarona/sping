package com.gonza.taller.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gonza.taller.dao.ProductDAO;
import com.gonza.taller.model.prod.Product;
import com.gonza.taller.model.prod.Productcategory;
import com.gonza.taller.model.prod.Productsubcategory;
import com.gonza.taller.repository.ProductCategoryRepository;
import com.gonza.taller.dao.ProductDAO;
import com.gonza.taller.repository.ProductSubCategoryRepository;



@Service
public class ProductServiceImp implements ProductService{
	
	private ProductDAO productDAO;
	
	private ProductCategoryRepository productCategoryRepository;
	
	private ProductSubCategoryRepository productSubCategoryRepository;
	
	

	public ProductServiceImp(ProductDAO ProductDAO,
			ProductCategoryRepository productCategoryRepository,
			ProductSubCategoryRepository productSubCategoryRepository){
		
		this.productDAO = ProductDAO;
		this.productCategoryRepository = productCategoryRepository;
		this.productSubCategoryRepository = productSubCategoryRepository;
		
	}
	
	@Override
	@Transactional
	public void save(Product product, Integer prCategoryId, Integer prSCategoryId) {
		
		Optional<Productcategory> productcategory = productCategoryRepository.findById(prCategoryId);
		Optional<Productsubcategory> productsubcategory = productSubCategoryRepository.findById(prSCategoryId);
		

		if (product == null) {
			throw new IllegalArgumentException("Error: The product must not be empty");
		} else if (productcategory.isEmpty()) {
			throw new IllegalArgumentException("Error: The product category must not be empty");
		} else if (productsubcategory.isEmpty()) {
			throw new IllegalArgumentException("Error: The product subcategory must not be empty");
			
		} else if (product.getProductnumber().isEmpty()) {
			throw new IllegalArgumentException("Error: The product number argument must not be empty");
		
		} else if (product.getSellenddate().isBefore(product.getSellstartdate())) {
			throw new IllegalArgumentException("Error: Product's sell end date must be greater than product's sell start date");
		
		} else if ( product.getWeight() <= 0){
			throw new IllegalArgumentException("Error: Product's weight must be greater than 0");
		
		} else if ( product.getSize() <= 0){
			throw new IllegalArgumentException("Error: Product's size must be greater than 0");
		
		}  else {
			
			
			//productsubcategory.get().setProductcategory(productcategory.get());
			product.setProductsubcategory(productsubcategory.get());
			
			
			productDAO.save(product);
		
		}
	}
	
	
	@Override
	@Transactional
	public void edit(Product product, Integer prCategoryId, Integer prSCategoryId) {
		
		Optional<Productcategory> productcategory = productCategoryRepository.findById(prCategoryId);
		Optional<Productsubcategory> productsubcategory = productSubCategoryRepository.findById(prSCategoryId);
		
		if (product == null) {
			throw new RuntimeException();
		} else if (productcategory.isEmpty()) {
			throw new RuntimeException();
		} else if (productsubcategory.isEmpty()) {
			throw new RuntimeException();
			
		} else if (product.getProductnumber().isEmpty()) {
			throw new IllegalArgumentException("Error: The product number argument must not be empty");
		
		} else if (product.getSellenddate().isBefore(product.getSellstartdate())) {
			throw new IllegalArgumentException("Error: Product's sell end date must be greater than product's sell start date");
		
		} else if ( product.getWeight() <= 0){
			throw new IllegalArgumentException("Error: Product's weight must be greater than 0");
		
		} else if ( product.getSize() <= 0){
			throw new IllegalArgumentException("Error: Product's size must be greater than 0");
		
		}  else {
			
			Optional<Product> p = productDAO.findById(product.getProductid());
			Product productEntity = p.get();
			
			productEntity.setProductsubcategory(productsubcategory.get());
			productEntity.getProductsubcategory().setProductcategory(productcategory.get());
			
			productEntity.setName(product.getName());
			productEntity.setColor(product.getColor());
			productEntity.setDaystomanufacture(product.getDaystomanufacture());
			productEntity.setDiscontinueddate(product.getDiscontinueddate());
			productEntity.setFinishedgoodsflag(product.getFinishedgoodsflag());
			productEntity.setListprice(product.getListprice());
			productEntity.setMakeflag(product.getMakeflag());
			productEntity.setModifieddate(product.getModifieddate());
			productEntity.setModifieddate(product.getModifieddate());
			productEntity.setProductline(product.getProductline());;	
			productEntity.setProductnumber(product.getProductnumber());
			productEntity.setReorderpoint(product.getReorderpoint());
			productEntity.setRowguid(product.getRowguid());
			productEntity.setRowguid(product.getRowguid());
			productEntity.setSize(product.getSize());
			productEntity.setWeight(product.getWeight());
			
			productEntity.setSellstartdate(product.getSellstartdate());
			productEntity.setSellenddate(product.getSellenddate());
			
			productDAO.save(productEntity);	
			

		}
		
	}
	@Override
	@Transactional
	public void edit(Product product, Integer prSCategoryId) {
		
		
		Optional<Productsubcategory> productsubcategory = productSubCategoryRepository.findById(prSCategoryId);
		
		if (product == null) {
			throw new RuntimeException();
		
		} else if (productsubcategory.isEmpty()) {
			throw new RuntimeException();
			
		} else if (product.getProductnumber().isEmpty()) {
			throw new IllegalArgumentException("Error: The product number argument must not be empty");
		
		} else if (product.getSellenddate().isBefore(product.getSellstartdate())) {
			throw new IllegalArgumentException("Error: Product's sell end date must be greater than product's sell start date");
		
		} else if ( product.getWeight() <= 0){
			throw new IllegalArgumentException("Error: Product's weight must be greater than 0");
		
		} else if ( product.getSize() <= 0){
			throw new IllegalArgumentException("Error: Product's size must be greater than 0");
		
		}  else {
			
			Optional<Product> p = productDAO.findById(product.getProductid());
			Product productEntity = p.get();
			
			productEntity.setProductsubcategory(productsubcategory.get());
			
			
			productEntity.setName(product.getName());
			productEntity.setColor(product.getColor());
			productEntity.setDaystomanufacture(product.getDaystomanufacture());
			productEntity.setDiscontinueddate(product.getDiscontinueddate());
			productEntity.setFinishedgoodsflag(product.getFinishedgoodsflag());
			productEntity.setListprice(product.getListprice());
			productEntity.setMakeflag(product.getMakeflag());
			productEntity.setModifieddate(product.getModifieddate());
			productEntity.setModifieddate(product.getModifieddate());
			productEntity.setProductline(product.getProductline());;	
			productEntity.setProductnumber(product.getProductnumber());
			productEntity.setReorderpoint(product.getReorderpoint());
			productEntity.setRowguid(product.getRowguid());
			productEntity.setRowguid(product.getRowguid());
			productEntity.setSize(product.getSize());
			productEntity.setWeight(product.getWeight());
			
			productEntity.setSellstartdate(product.getSellstartdate());
			productEntity.setSellenddate(product.getSellenddate());
			
			productDAO.save(productEntity);	
			

		}
		
	}
	
	@Override
	public Iterable<Product> findAll() {
		return productDAO.findAll();
	}
	

	
	@Override
	public Optional<Product> findById(int id){
		return productDAO.findById(id);
	}
	
	@Override
	@Transactional
	public void delete(Product product) {
		productDAO.delete(product);
		
	}
	
	

} //end of class
