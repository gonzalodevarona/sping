package com.gonza.taller.repository;

import org.springframework.data.repository.CrudRepository;

import com.gonza.taller.model.prod.Productcategory;
import com.gonza.taller.model.prod.Productinventory;
import com.gonza.taller.model.prod.Productsubcategory;

public interface ProductSubCategoryRepository  extends CrudRepository<Productsubcategory,Integer>{

}
