package com.gonza.taller.service;

import com.gonza.taller.model.prod.Productcosthistory;

public interface ProductcosthistoryService {
	
	public void save(Productcosthistory productcosthistory, Integer productId);
	
	public void edit(Productcosthistory productcosthistory, Integer productId);

}
