package com.gonza.taller.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class IndexController implements IndexControllerI{

	
	@Override
	@GetMapping
	public String index() {
		return "admin/index";
	}
	
	

	

}//end of class
