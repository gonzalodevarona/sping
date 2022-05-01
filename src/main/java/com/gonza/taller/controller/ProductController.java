package com.gonza.taller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.gonza.taller.service.ProductServiceImp;

@Controller
@RequestMapping("product")
public class ProductController implements ProductControllerI{
	
	private ProductServiceImp productService;

	@Autowired
	public ProductController(ProductServiceImp productService) {

		this.productService = productService;
	}
	
	@Override
	@GetMapping
	public String index(Model model) {
		model.addAttribute("products", productService.findAll());
		return "product/index";
	}
	

//
//	@GetMapping("/templatesProduct/add-product")
//	public String listProduct(Model model) {
//		model.addAttribute("product", new Product());
//		model.addAttribute("subcategories", productService.findAllSubcategory());
//		System.out.println("subcategorias agregadas");
//
//		return "templatesProduct/add-product";
//	}
//
//	@PostMapping("/templatesProduct/add-product/")
//	public String saveProduct(@Validated @ModelAttribute Product product, BindingResult bindingResult, Model model,
//			@RequestParam(value = "action", required = true) String action) {
//
//		if (!action.equals("Cancel")) {
//			model.addAttribute("subcategories", productService.findAllSubcategory());
//			if (bindingResult.hasErrors()) {
//				return "templatesProduct/add-product";
//			} else if (product.getSellenddate().isBefore(product.getSellstartdate())) {
//				model.addAttribute("dateError", true);
//				return "templatesProduct/add-product";
//			}
//			productService.saveProduct(product, null, product.getProductsubcategory().getProductsubcategoryid());
//		}
//		return "redirect:/templatesProduct/";
//	}

} //end of class






