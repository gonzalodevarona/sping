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

import com.gonza.taller.model.prod.Location;
import com.gonza.taller.service.LocationService;

@Controller
@RequestMapping("location")
public class LocationController implements LocationControllerI{
	
	private LocationService locationService;

	@Autowired
	public LocationController(LocationService locationService) {

		this.locationService = locationService;
	
	}
	
	@Override
	@GetMapping
	public String index(Model model) {
		model.addAttribute("locations", locationService.findAll());
		return "location/index";
	}
	

	@Override
	@GetMapping("/add")
	public String addLocation(Model model) {
		model.addAttribute("location", new Location());
		return "location/add";
	}
	
	@Override
	@PostMapping("/add")
	public String saveLocation(@Validated @ModelAttribute Location location, BindingResult bindingResult, Model model,
			@RequestParam(value = "action", required = true) String action) {

		if (!action.equals("Cancelar")) {
			
			if (bindingResult.hasErrors()) {
				return "location/add";
			} 
			locationService.save(location);
		}
		return "redirect:/location/";
	}
	
	@Override
	@GetMapping("/edit/{id}")
	public String showEditLocation(@PathVariable("id") int id, Model model) {
		Optional<Location> location = locationService.findById(id);
		if (location == null)
			throw new IllegalArgumentException("Invalid location Id:" + id);
		model.addAttribute("location", location.get());

		return "location/edit";
	}
	
	@Override
	@PostMapping("/edit/{id}")
	public String editLocation(@PathVariable("id") int id, @RequestParam(value = "action", required = true) String action,
			Location location, BindingResult bindingResult, Model model) {
		if (!action.equals("Cancelar")) {
			
			if(bindingResult.hasErrors()) {
				model.addAttribute("location", location);

				
				
				return "location/edit";
				
			}
			location.setLocationid(id);
			locationService.edit(location);
			
			model.addAttribute("locations", locationService.findAll());
		}
		return "redirect:/location/";
	}
	


} //end of class
