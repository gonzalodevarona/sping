package com.gonza.taller.repository;


import org.springframework.data.repository.CrudRepository;

import com.gonza.taller.model.prod.Productcategory;
import com.gonza.taller.model.prod.Productinventory;
import com.gonza.taller.model.prod.Productmodel;
import com.gonza.taller.model.prod.Productsubcategory;

public interface ProductmodelRepository  extends CrudRepository<Productmodel,Integer>{

}