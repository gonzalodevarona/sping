package com.gonza.taller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gonza.taller.dao.Dao;
import com.gonza.taller.dao.ProductDAO;
import com.gonza.taller.model.prod.Product;
import com.gonza.taller.model.prod.Productcategory;
import com.gonza.taller.model.prod.Productcosthistory;
import com.gonza.taller.model.prod.Productsubcategory;
import com.gonza.taller.repository.ProductCategoryRepository;
import com.gonza.taller.repository.ProductSubCategoryRepository;
import com.gonza.taller.service.ProductcosthistoryService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Taller1Application.class)
@Rollback(false)
public class ProductDAOTests {
	
    @Autowired
    private ProductDAO productDAO;

   

    private Product product;

    @Autowired
    private ProductSubCategoryRepository productsubcategoryRepository;
    
    @Autowired
    private ProductCategoryRepository productcategoryRepository;
    
    @Autowired
    private ProductcosthistoryService productcosthistoryService;

    @Autowired
    public ProductDAOTests(ProductDAO productDAO, ProductSubCategoryRepository productsubcategoryRepository,
    		ProductCategoryRepository productcategoryRepository, ProductcosthistoryService productcosthistoryService) {
        this.productDAO = productDAO;
        this.productsubcategoryRepository = productsubcategoryRepository;
        this.productcategoryRepository = productcategoryRepository;
        this.productcosthistoryService = productcosthistoryService;
    }
    
    public void createProduct() {
    	product = new Product();
    	product.setName("Appetite for Destruction Tickets");
    	
    	product.setProductnumber("10110");
    	product.setWeight(1);
    	product.setSize(2);
    	product.setProductid(12);
		
    	product.setSellstartdate(LocalDate.of(2021, 10, 24));
    	product.setSellenddate(LocalDate.of(2021, 11, 24));
    	
		Productcategory pc1 = new Productcategory();
		Productsubcategory psc1 = new Productsubcategory();
		
	
		pc1.setName("Eventos");
		psc1.setName("Concierto de Rock");
		
	
		psc1.setProductcategory(pc1);
		product.setProductsubcategory(psc1);
		
	
		
		productsubcategoryRepository.save(psc1);
		productcategoryRepository.save(pc1);
    	
    }
    
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Test
    @Order(1)
    public void saveProductTest() {

    	createProduct();
        productDAO.save(product);
        // deberían haber 2 productos puesto que el primero fue guardado desde el commandlinerunner
        assertEquals(2, productDAO.findAll().size());
        

    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Test
    @Order(2)
    public void editProductTest() {

    	createProduct();
    	productDAO.save(product);
    	
    	
    	assertEquals("Appetite for Destruction Tickets", productDAO.findById(3).get().getName());
    	
    	Product matter = productDAO.findById(3).get();
    	
    	matter.setName("The Spaghetti Incident? Tickets");
    	
    	productDAO.update(matter);
    	
    	assertEquals("The Spaghetti Incident? Tickets", productDAO.findById(3).get().getName());
        
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Test
    @Order(3)
    public void findAllBySubcategoryIdTest() {
    	List<Product> list = productDAO.findAllBySubcategoryId(1);
    	assertEquals("RTX 3090", list.get(0).getName());
    	assertEquals(1, list.size());
    	
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Test
    @Order(3)
    public void findAllBySubcategoryIdBadTest() {
    	List<Product> list = productDAO.findAllBySubcategoryId(5000);
    	assertEquals(0, list.size());
    	
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Test
    @Order(4)
    public void findAllByUnitMeasureCodeTest() {
    	List<Product> list = productDAO.findAllByUnitMeasureCode(1);
    	assertEquals("RTX 3090", list.get(0).getName());
    	assertEquals(1, list.size());
    	
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Test
    @Order(5)
    public void findAllByUnitMeasureCodeBadTest() {
    	List<Product> list = productDAO.findAllByUnitMeasureCode(0);
    	
    	assertEquals(0, list.size());
    	
    }
    
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Test
    @Order(6)
    public void findAllByProductmodelIdTest() {
    	List<Product> list = productDAO.findAllByProductmodelId(2);
    	assertEquals("RTX 3090", list.get(0).getName());
    	assertEquals(1, list.size());
    	
    }
    
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Test
    @Order(7)
    public void findAllByProductmodelIdBadTest() {
    	List<Product> list = productDAO.findAllByProductmodelId(1);
    	assertEquals(0, list.size());
    	
    }
    
    
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Test
    @Order(8)
    public void deleteProductTest() {
    	createProduct();
    	productDAO.save(product);

        productDAO.delete(productDAO.findById(2).get());
        //deberia haber 1 producto porque el que cree previamente lo acabé de borrar
        assertEquals(1, productDAO.findAll().size());
       
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Test
    @Order(9)
    public void findMoreThan2ProductcosthistoryTest() {
    	createProduct();
    	
    	List<Productcosthistory> productcosthistories = new ArrayList<Productcosthistory>();
    	
    	product.setProductcosthistories(productcosthistories);
    	
		Productcosthistory pch1 = new Productcosthistory(); 
		pch1.setStandardcost(new BigDecimal("3"));
		pch1.setProduct(product);
		pch1.setEnddate(LocalDate.of(2021, 12, 21));
		
		Productcosthistory pch2 = new Productcosthistory(); 
		pch2.setStandardcost(new BigDecimal("2"));
		pch2.setProduct(product);
		pch2.setEnddate(LocalDate.of(2021, 10, 30));
		
		Productcosthistory pch3 = new Productcosthistory(); 
		pch3.setStandardcost(new BigDecimal("4"));
		pch3.setProduct(product);
		pch3.setEnddate(LocalDate.of(2021, 12, 28));
		
		product.addProductcosthistory(pch1);
		product.addProductcosthistory(pch2);
		product.addProductcosthistory(pch3);
		
		productDAO.save(product);
		
//		List<Product> test = productDAO.findAll();
//		
//		for(int i = 0; i<test.size();i++) {
//			System.out.println(test.get(i).getProductid()+test.get(i).getName());
//		}
		
		
		int code = productDAO.findById(3).get().getProductid();
		
		productcosthistoryService.save(pch1, code);
		productcosthistoryService.save(pch2, code);
		productcosthistoryService.save(pch3, code);
		
		List<Product> result = productDAO.findMoreThan2Productcosthistory();
		
		assertEquals(1, result.size());
		
		assertEquals("Appetite for Destruction Tickets", result.get(0).getName());
		
		
	
    	
       
    }
    
    
	
} //end of class