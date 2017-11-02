package org.ipni.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticController {
	
	@GetMapping(path= "/contact")
	public String contact(Model model){
		model.addAttribute("staticPage", "navigate/tmpl/contact");
		return "static";
		
	}
	
	@GetMapping(path= "/about")
	public String about(Model model){
		model.addAttribute("staticPage", "navigate/tmpl/about");
		return "static";
		
	}
	
	@GetMapping(path= "/statistics")
	public String statistics(Model model){
		model.addAttribute("staticPage", "navigate/tmpl/statistics");
		return "static";
		
	}

}
