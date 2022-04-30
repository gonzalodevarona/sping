package com.gonza.taller.repository;

import org.springframework.data.repository.CrudRepository;

import com.gonza.taller.model.prod.Productcategory;
import com.gonza.taller.model.prod.Productcosthistory;
import com.gonza.taller.model.prod.ProductcosthistoryPK;

public interface ProductCostHistoryRepository extends CrudRepository<Productcosthistory,Integer>{

}
