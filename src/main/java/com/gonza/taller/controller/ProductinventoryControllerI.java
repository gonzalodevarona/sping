package com.gonza.taller.controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.gonza.taller.model.prod.Productinventory;

public interface ProductinventoryControllerI {

	String index(Model model);

	String addProductinventory(Model model);

	String saveProductinventory(Productinventory productinventory, BindingResult bindingResult, Model model,
			String action);

	String showEditProductinventory(int id, Model model);

	String editProductinventory(int id, String action, Productinventory productinventory, BindingResult bindingResult,
			Model model);

}



