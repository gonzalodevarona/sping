package com.gonza.taller.controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.gonza.taller.model.prod.Location;
import com.gonza.taller.model.prod.Product;

public interface LocationControllerI {
	public String index(Model model);
	public String addLocation(Model model);
	public String saveLocation(Location location, BindingResult bindingResult, Model model, String action);
//	public String showEditLocation(int id, Model model);
//	public String editLocation(int id, String action, Product product, BindingResult bindingResult, Model model); 
}
