package com.gonza.taller.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gonza.taller.model.prod.Product;
import com.gonza.taller.model.prod.Productcosthistory;
import com.gonza.taller.model.prod.Productinventory;
import com.gonza.taller.service.LocationService;
import com.gonza.taller.service.ProductService;
import com.gonza.taller.service.ProductcategoryService;
import com.gonza.taller.service.ProductcosthistoryService;
import com.gonza.taller.service.ProductinventoryService;
import com.gonza.taller.service.ProductsubcategoryService;


@Controller
@RequestMapping("productinventory")
public class ProductinventoryController implements ProductinventoryControllerI{
	
	private ProductService productService;
	private LocationService locationService;
	private ProductinventoryService productinventoryService;

	@Autowired
	public ProductinventoryController(ProductService productService, LocationService locationService,
	ProductinventoryService productinventoryService) {

		this.productService = productService;
		this.locationService = locationService;
		this.productinventoryService = productinventoryService;
	}
	
	@Override
	@GetMapping
	public String index(Model model) {
		model.addAttribute("productinventories", productinventoryService.findAll());
		return "productinventory/index";
	}
	

	@Override
	@GetMapping("/add")
	public String addProductinventory(Model model) {
		model.addAttribute("productinventory", new Productinventory());
		model.addAttribute("products", productService.findAll());
		model.addAttribute("locations", locationService.findAll());
		

		return "productinventory/add";
	}
	
	@Override
	@PostMapping("/add")
	public String saveProductinventory(@Validated @ModelAttribute Productinventory productinventory, BindingResult bindingResult, Model model,
			@RequestParam(value = "action", required = true) String action) {

		if (!action.equals("Cancelar")) {
			model.addAttribute("products", productService.findAll());
			model.addAttribute("locations", locationService.findAll());
			
			if (bindingResult.hasErrors()) {
				return "productinventory/add";
			} 
			productinventoryService.save(productinventory, productinventory.getProduct().getProductid(), productinventory.getLocation().getLocationid());
		}
		return "redirect:/productinventory/";
	}
	
	@Override
	@GetMapping("/edit/{id}")
	public String showEditProductinventory(@PathVariable("id") int id, Model model) {
		Optional<Productinventory> productinventory = productinventoryService.findById(id);
		if (productinventory == null)
			throw new IllegalArgumentException("Invalid productinventory Id:" + id);
		model.addAttribute("productinventory", productinventory.get());

		model.addAttribute("products", productService.findAll());
		model.addAttribute("locations", locationService.findAll());
		
		return "productinventory/edit";
	}
	
	@Override
	@PostMapping("/edit/{id}")
	public String editProductinventory(@PathVariable("id") int id, @RequestParam(value = "action", required = true) String action,
			Productinventory productinventory, BindingResult bindingResult, Model model) {
		if (!action.equals("Cancelar")) {
			
			if(bindingResult.hasErrors()) {
				model.addAttribute("productinventory", productinventory);

				model.addAttribute("products", productService.findAll());
				model.addAttribute("locations", locationService.findAll());
				
				return "productinventory/edit";
				
			}
			productinventory.setId(id);
			productinventoryService.edit(productinventory, productinventory.getProduct().getProductid(), productinventory.getLocation().getLocationid());
			
			model.addAttribute("productinventories", productinventoryService.findAll());
		}
		return "redirect:/productinventory/";
	}

} //end of class







