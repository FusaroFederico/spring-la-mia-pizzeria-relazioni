package com.example.demo.pizzeria.controller;

import java.util.List;
import java.util.Optional;

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
import com.example.demo.pizzeria.repo.PizzaRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {
	
	// repository field con autowired per d.i.
	@Autowired
	private PizzaRepository repo;
	
	@GetMapping
	public String index(Model model, @RequestParam(name = "name", required = false) String name) {
		model.addAttribute("title", "Menù");
		
		List<Pizza> pizzas;
		
		if ( name!=null && !name.isEmpty()) {
			model.addAttribute("name", name);
			pizzas = repo.findByNameContainingIgnoreCaseOrderByNameAsc(name);
			
		} else {
			pizzas = repo.findAll();
		}
		
		// li inserisco nel model
		model.addAttribute("pizzas", pizzas);
		
		return "/pizzas/index";
	}
	
	@GetMapping("/{id}")
	public String pizzaDetails(Model model, @PathVariable("id") Integer pizzaId) {
		model.addAttribute("title", "Dettagli Pizza");
		
		Optional<Pizza> pizzaOpt = repo.findById(pizzaId);
		Pizza pizza = null;
		if(pizzaOpt.isPresent()) {
			// Perform operations with the user object
		    pizza = pizzaOpt.get();
		}
		
		model.addAttribute("pizza", pizza);
		return "/pizzas/show";
	}
	
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
		
		repo.save(formPizza);
		
		redirectAttributes.addFlashAttribute("successMessage", "La pizza '" + formPizza.getName() + "' è stata aggiunta al menù.");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/pizzas";
	}
	
	// get -> edit()
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		
		model.addAttribute("title", "Modifica Pizza");
		model.addAttribute("pizza", repo.findById(id).get());
		
		return "/pizzas/edit";
	}
	
	// post -> update()
	@PostMapping("/edit/{id}")
	public String update(@Valid @ModelAttribute("pizza") Pizza updatedPizza, 
						 BindingResult bindingResult,
						 RedirectAttributes redirectAttributes) {
		
		if (bindingResult.hasErrors()) {
			return "/pizzas/edit";
		}
		
		repo.save(updatedPizza);
		
		redirectAttributes.addFlashAttribute("successMessage", "La pizza '" + updatedPizza.getName() + "' è stata aggiornata con successo.");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/pizzas";
	}
	
	// post -> delete
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		
		String pizzaName = repo.findById(id).get().getName();
		
		repo.deleteById(id);
		
		redirectAttributes.addFlashAttribute("successMessage", "La pizza '" + pizzaName + "' è stata eliminata dal menù.");
		redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
		return "redirect:/pizzas";
	}
}
