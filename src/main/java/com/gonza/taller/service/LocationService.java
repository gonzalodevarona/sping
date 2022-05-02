package com.gonza.taller.service;

import java.util.Optional;

import com.gonza.taller.model.prod.Location;
import com.gonza.taller.model.prod.Product;

public interface LocationService {
	
	public void save(Location location);
	public void edit(Location location);
	public Iterable<Location> findAll();
	public Optional<Location> findById(int id);
	public void delete(Location location);


}
