package com.gonza.taller.controller;

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
import com.gonza.taller.service.ProductService;
import com.gonza.taller.service.ProductcategoryService;
import com.gonza.taller.service.ProductsubcategoryService;


@Controller
@RequestMapping("product")
public class ProductController implements ProductControllerI{
	
	private ProductService productService;
	private ProductcategoryService productcategoryService;
	private ProductsubcategoryService productsubcategoryService;

	@Autowired
	public ProductController(ProductService productService, ProductcategoryService productcategoryService,
			ProductsubcategoryService productsubcategoryService) {

		this.productService = productService;
		this.productcategoryService = productcategoryService;
		this.productsubcategoryService = productsubcategoryService;
	}
	
	@Override
	@GetMapping
	public String index(Model model) {
		model.addAttribute("products", productService.findAll());
		return "product/index";
	}
	

	@Override
	@GetMapping("/add")
	public String addProduct(Model model) {
		model.addAttribute("product", new Product());
		model.addAttribute("subcategories", productsubcategoryService.findAll());
		model.addAttribute("categories", productcategoryService.findAll());
		System.out.println("subcategorias agregadas");

		return "product/add";
	}
	
	@Override
	@PostMapping("/add")
	public String saveProduct(@Validated @ModelAttribute Product product, BindingResult bindingResult, Model model,
			@RequestParam(value = "action", required = true) String action) {

		if (!action.equals("Cancelar")) {
			model.addAttribute("subcategories", productsubcategoryService.findAll());
			model.addAttribute("categories", productcategoryService.findAll());
			if (bindingResult.hasErrors()) {
				return "product/add";
			} else if (product.getSellenddate().isBefore(product.getSellstartdate())) {
				model.addAttribute("dateError", true);
				return "product/add";
			}
			productService.save(product, product.getProductsubcategory().getProductcategory().getProductcategoryid(), product.getProductsubcategory().getProductsubcategoryid());
		}
		return "redirect:/product/";
	}
	
	@Override
	@GetMapping("/edit/{id}")
	public String showEditProduct(@PathVariable("id") int id, Model model) {
		Optional<Product> product = productService.findById(id);
		if (product == null)
			throw new IllegalArgumentException("Invalid product Id:" + id);
		model.addAttribute("product", product.get());

		model.addAttribute("subcategories", productsubcategoryService.findAll());
		
		return "product/edit";
	}
	
	@Override
	@PostMapping("/edit/{id}")
	public String editProduct(@PathVariable("id") int id, @RequestParam(value = "action", required = true) String action,
			Product product, BindingResult bindingResult, Model model) {
		if (!action.equals("Cancelar")) {
			
			if(bindingResult.hasErrors()) {
				model.addAttribute("product", product);

				model.addAttribute("subcategories", productsubcategoryService.findAll());
				
				return "product/edit";
				
			}
			product.setProductid(id);
			productService.edit(product, product.getProductsubcategory().getProductsubcategoryid());
			
			model.addAttribute("products", productService.findAll());
		}
		return "redirect:/product/";
	}

} //end of class






