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
import com.gonza.taller.service.ProductService;
import com.gonza.taller.service.ProductcategoryService;
import com.gonza.taller.service.ProductcosthistoryService;
import com.gonza.taller.service.ProductsubcategoryService;


@Controller
@RequestMapping("productcosthistory")
public class ProductcosthistoryController implements ProductcosthistoryControllerI{
	
	private ProductService productService;
	private ProductcosthistoryService productcosthistoryService;

	@Autowired
	public ProductcosthistoryController(ProductService productService, ProductcosthistoryService productcosthistoryService) {

		this.productService = productService;
		this.productcosthistoryService = productcosthistoryService;
	}
	
	@Override
	@GetMapping
	public String index(Model model) {
		model.addAttribute("productcosthistories", productcosthistoryService.findAll());
		return "productcosthistory/index";
	}
	

	@Override
	@GetMapping("/add")
	public String addProductcosthistory(Model model) {
		model.addAttribute("productcosthistory", new Productcosthistory());
		model.addAttribute("products", productService.findAll());
		

		return "productcosthistory/add";
	}
	
	@Override
	@PostMapping("/add")
	public String saveProductcosthistory(@Validated @ModelAttribute Productcosthistory productcosthistory, BindingResult bindingResult, Model model,
			@RequestParam(value = "action", required = true) String action) {

		if (!action.equals("Cancelar")) {
			model.addAttribute("products", productService.findAll());
			
			if (bindingResult.hasErrors()) {
				return "productcosthistory/add";
			} else if (productcosthistory.getEnddate().isAfter(LocalDate.now())) {
				model.addAttribute("dateError", true);
				return "productcosthistory/add";
			}
			productcosthistoryService.save(productcosthistory, productcosthistory.getProduct().getProductid());
		}
		return "redirect:/productcosthistory/";
	}
	
	@Override
	@GetMapping("/edit/{id}")
	public String showEditProductcosthistory(@PathVariable("id") int id, Model model) {
		Optional<Productcosthistory> productcosthistory = productcosthistoryService.findById(id);
		if (productcosthistory == null)
			throw new IllegalArgumentException("Invalid productcosthistory Id:" + id);
		model.addAttribute("productcosthistory", productcosthistory.get());

		model.addAttribute("products", productService.findAll());
		
		return "productcosthistory/edit";
	}
	
	@Override
	@PostMapping("/edit/{id}")
	public String editProductcosthistory(@PathVariable("id") int id, @RequestParam(value = "action", required = true) String action,
			Productcosthistory productcosthistory, BindingResult bindingResult, Model model) {
		if (!action.equals("Cancelar")) {
			
			if(bindingResult.hasErrors()) {
				model.addAttribute("productcosthistory", productcosthistory);

				model.addAttribute("products", productService.findAll());
				
				return "product/edit";
				
			}
			productcosthistory.setId(id);
			productcosthistoryService.edit(productcosthistory, productcosthistory.getProduct().getProductid());
			
			model.addAttribute("productcosthistories", productcosthistoryService.findAll());
		}
		return "redirect:/productcosthistory/";
	}

} //end of class






