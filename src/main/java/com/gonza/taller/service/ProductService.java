package com.gonza.taller.service;

import com.gonza.taller.model.prod.Product;

public interface ProductService {
	
	public void save(Product product, Integer prCategoryId, Integer prSCategoryId);
	public void edit(Product product, Integer prCategoryId, Integer prSCategoryId);

}
