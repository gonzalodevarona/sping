package com.gonza.taller.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class UserController implements UserControllerrI{

	
	@Override
	@GetMapping
	public String index() {
		return "user/index";
	}
	
	

	

}//end of class
