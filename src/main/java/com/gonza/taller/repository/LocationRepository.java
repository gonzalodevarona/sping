package com.gonza.taller.repository;

import org.springframework.data.repository.CrudRepository;

import com.gonza.taller.model.prod.Location;
import com.gonza.taller.model.prod.Productinventory;

public interface LocationRepository extends CrudRepository<Location,Integer>{

}
