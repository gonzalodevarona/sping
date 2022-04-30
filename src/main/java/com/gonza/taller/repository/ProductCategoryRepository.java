package com.gonza.taller.repository;

import org.springframework.data.repository.CrudRepository;

import com.gonza.taller.model.prod.Productcategory;
import com.gonza.taller.model.prod.Productinventory;

public interface ProductCategoryRepository   extends CrudRepository<Productcategory,Integer>{

}
