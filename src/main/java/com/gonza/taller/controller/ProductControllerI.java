package com.gonza.taller.controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.gonza.taller.model.prod.Product;

public interface ProductControllerI {
	public String index(Model model);
	public String addProduct(Model model);
	public String saveProduct(Product product, BindingResult bindingResult, Model model, String action);
	public String showEditProduct(int id, Model model);
	public String editProduct(int id, String action, Product product, BindingResult bindingResult, Model model); 
}
