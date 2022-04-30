package com.gonza.taller.repository;

import org.springframework.data.repository.CrudRepository;

import com.gonza.taller.model.prod.Product;
import com.gonza.taller.model.prod.Productinventory;

public interface ProductRepository  extends CrudRepository<Product,Integer>{

}
