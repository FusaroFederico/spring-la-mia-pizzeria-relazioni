package com.example.demo.pizzeria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.pizzeria.model.Pizza;
import com.example.demo.pizzeria.model.SpecialOffer;
import com.example.demo.pizzeria.service.IngredientService;
import com.example.demo.pizzeria.service.PizzaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {
	
	@Autowired
	private PizzaService pizzaService; 
	@Autowired
	private IngredientService ingredientService; 
	
	@GetMapping
	public String index(Model model, @RequestParam(name = "name", required = false) String name) {
		model.addAttribute("title", "Menù");
		
		List<Pizza> pizzas;
		
		if ( name!=null && !name.isEmpty()) {
			model.addAttribute("name", name);
			pizzas = pizzaService.findListByName(name);
			
		} else {
			pizzas = pizzaService.findAllSortedByName();
		}
		
		model.addAttribute("pizzas", pizzas);
		
		return "/pizzas/index";
	}
	
	@GetMapping("/{id}")
	public String pizzaDetails(Model model, @PathVariable("id") Integer pizzaId) {
		model.addAttribute("title", "Dettagli Pizza");
		
		model.addAttribute("pizza", pizzaService.getById(pizzaId));
		return "/pizzas/show";
	}
	
	// GET -> create specialOffer relative to specific pizza
	@GetMapping("/{id}/specialOffer/create")
	public String createSpecialOffer(Model model, @PathVariable("id") Integer pizzaId) {
		
		model.addAttribute("title", "Crea nuova Offerta Speciale");
		
		SpecialOffer specialOffer = new SpecialOffer();
		specialOffer.setPizza(pizzaService.getById(pizzaId));
		model.addAttribute("specialOffer", specialOffer);
		
		return "/specialOffers/create";
	}
	
	// CREATE
	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("title", "Aggiungi Pizza");
		model.addAttribute("pizza", new Pizza());
		
		return "/pizzas/create";
	}
	
	@PostMapping ("/create")
	public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, 
					     BindingResult bindingResult,
					     RedirectAttributes redirectAttributes) {
		
		if (bindingResult.hasErrors()) {
			return "/pizzas/create";
		}
		
		pizzaService.create(formPizza);
		
		redirectAttributes.addFlashAttribute("successMessage", "La pizza '" + formPizza.getName() + "' è stata aggiunta al menù.");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/pizzas";
	}
	
	// get -> edit()
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		
		model.addAttribute("title", "Modifica Pizza");
		model.addAttribute("pizza", pizzaService.getById(id));
		model.addAttribute("ingredientsList", ingredientService.findAllSortedByName());
		
		return "/pizzas/edit";
	}
	
	// post -> update()
	@PostMapping("/edit/{id}")
	public String update(@Valid @ModelAttribute("pizza") Pizza updatedPizza, 
						 Model model,
						 BindingResult bindingResult,
						 RedirectAttributes redirectAttributes) {
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("ingredientsList", ingredientService.findAllSortedByName());
			return "/pizzas/edit";
		}
		
		pizzaService.update(updatedPizza);
		
		redirectAttributes.addFlashAttribute("successMessage", "La pizza '" + updatedPizza.getName() + "' è stata aggiornata con successo.");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/pizzas";
	}
	
	// post -> delete
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		
		String pizzaName = pizzaService.getById(id).getName();
		
		pizzaService.deleteById(id);
		
		redirectAttributes.addFlashAttribute("successMessage", "La pizza '" + pizzaName + "' è stata eliminata dal menù.");
		redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
		return "redirect:/pizzas";
	}
}
