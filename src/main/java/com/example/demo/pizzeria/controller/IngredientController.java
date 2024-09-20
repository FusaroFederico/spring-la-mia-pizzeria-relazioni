package com.example.demo.pizzeria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
		model.addAttribute("title", "Ingredienti");
		model.addAttribute("ingredients", ingredientService.findAllSortedByName());
		
		return "/ingredients/index";
	}
	
	// CREATE
	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("title", "Aggiungi Ingrediente");
		model.addAttribute("pizzasList", pizzaService.findAllSortedByName());
		model.addAttribute("ingredient", new Ingredient());
		
		return "/ingredients/create";
	}
	
	@PostMapping ("/create")
	public String store(@Valid @ModelAttribute("ingredient") Ingredient formIngredient, 
					     BindingResult bindingResult,
					     Model model,
					     RedirectAttributes redirectAttributes) {
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("pizzasList", pizzaService.findAllSortedByName());
			return "/ingredients/create";
		}
		
		ingredientService.create(formIngredient);
	
		redirectAttributes.addFlashAttribute("successMessage", "L'Ingrediente '" + formIngredient.getName() + "' è stato aggiunto alla lista.");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/ingredients";
	}
	
	// EDIT
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		
		model.addAttribute("title", "Modifica Ingrediente");
		model.addAttribute("ingredient", ingredientService.getById(id));
		model.addAttribute("pizzasList", pizzaService.findAllSortedByName());
		
		return "/ingredients/edit";
	}
	
	@PostMapping("/edit/{id}")
	public String update(@Valid @ModelAttribute("ingredient") Ingredient ingredientToUpdate, 
						 Model model,
						 BindingResult bindingResult,
						 RedirectAttributes redirectAttributes) {
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("pizzasList", pizzaService.findAllSortedByName());
			return "/ingredients/edit";
		}
		
		ingredientService.update(ingredientToUpdate);
		
		redirectAttributes.addFlashAttribute("successMessage", "L'ingrediente '" + ingredientToUpdate.getName() + "' è stato aggiornato con successo.");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/ingredients";
	}
	
	// DELETE
	// post -> delete
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		
		String ingredientName = ingredientService.getById(id).getName();
		
		// remove pizzas association
		//ingredientService.getById(id).removePizzaAssociation();
		
		ingredientService.deleteById(id);
		
		redirectAttributes.addFlashAttribute("successMessage", "L'ingrediente '" + ingredientName + "' è stato eliminato con successo.");
		redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
		return "redirect:/ingredients";
	}
}
