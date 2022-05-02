package com.gonza.taller.controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.gonza.taller.model.prod.Productcosthistory;

public interface ProductcosthistoryControllerI {
	public String index(Model model); 

	public String addProductcosthistory(Model model);

	String saveProductcosthistory(Productcosthistory productcosthistory, BindingResult bindingResult, Model model,
			String action);

	String showEditProductcosthistory(int id, Model model);

	String editProductcosthistory(int id, String action, Productcosthistory productcosthistory,
			BindingResult bindingResult, Model model);
}
