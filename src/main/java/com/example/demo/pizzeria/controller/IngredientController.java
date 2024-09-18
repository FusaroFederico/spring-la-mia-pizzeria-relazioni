package com.example.demo.pizzeria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.pizzeria.model.Ingredient;
import com.example.demo.pizzeria.service.IngredientService;
import com.example.demo.pizzeria.service.PizzaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {
	
	@Autowired
	private IngredientService ingredientService; 
	@Autowired
	private PizzaService pizzaService; 
	
	@GetMapping
	public String index(Model model) {
		model.addAttribute("title", "Lista Ingredienti");
		model.addAttribute("ingredients", ingredientService.findAllSortedByName());
		
		return "/ingredients/index";
	}
	
	// CREATE
	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("title", "Aggiungi Ingrediente");
		model.addAttribute("pizzas", pizzaService.findAllSortedByName());
		model.addAttribute("ingredient", new Ingredient());
		
		return "/ingredients/create";
	}
	
	@PostMapping ("/create")
	public String store(@Valid @ModelAttribute("ingredient") Ingredient formIngredient, 
					     BindingResult bindingResult,
					     RedirectAttributes redirectAttributes) {
		
		if (bindingResult.hasErrors()) {
			return "/ingredients/create";
		}
		
		ingredientService.create(formIngredient);
		
		redirectAttributes.addFlashAttribute("successMessage", "L'Ingrediente '" + formIngredient.getName() + "' è stato aggiunto alla lista.");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/ingredients";
	}
}
