package com.gonza.taller.dao;

import java.util.List;
import java.util.Optional;


public interface Dao<T> {
	
	public void save(T t);
	
	public Optional<T> findById(int id);
	
	public void delete(T t);
	
	public void update(T t);
	
	public List<T> findAll();
	
}
