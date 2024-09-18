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

import com.example.demo.pizzeria.model.SpecialOffer;
import com.example.demo.pizzeria.service.SpecialOfferService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/specialOffers")
public class SpecialOfferController {
		
	@Autowired
	private SpecialOfferService specialOfferService;
	
	
	// POST -> store specialOffer relative to specific pizza
	@PostMapping ("/create")
	public String store(@Valid @ModelAttribute("specialOffer") SpecialOffer formSpecialOffer, 
					     BindingResult bindingResult,
					     RedirectAttributes redirectAttributes,
					     Model model) {
		
		if (bindingResult.hasErrors()) {
			return "/specialOffers/create";
		}
		
		specialOfferService.create(formSpecialOffer);
		
		redirectAttributes.addFlashAttribute("successMessage", "L'offerta speciale '" + formSpecialOffer.getTitle() + "' è stata inserita correttamente.");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/pizzas/" + formSpecialOffer.getPizza().getId();
	}
	
	// get -> edit()
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		
		model.addAttribute("title", "Modifica Offerta Speciale");
		model.addAttribute("specialOffer", specialOfferService.getById(id));
		
		return "/specialOffers/edit";
	}
	
	// post -> update()
	@PostMapping("/edit/{id}")
	public String update(@Valid @ModelAttribute("specialOffer") SpecialOffer updatedOffer, 
						 BindingResult bindingResult,
						 RedirectAttributes redirectAttributes) {
		
		if (bindingResult.hasErrors()) {
			return "/specialOffers/edit";
		}
		specialOfferService.update(updatedOffer);
		
		redirectAttributes.addFlashAttribute("successMessage", "L'offerta '" + updatedOffer.getTitle() + "' è stata aggiornata con successo.");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/pizzas/" + updatedOffer.getPizza().getId();
	}
	
	// post -> delete
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id, 
			             RedirectAttributes redirectAttributes) {
		
		Integer pizzaId = specialOfferService.getById(id).getPizza().getId();
		String deletedOfferTitle = specialOfferService.getById(id).getTitle();
		specialOfferService.deleteById(id);
		
		redirectAttributes.addFlashAttribute("successMessage", "L'offerta '" + deletedOfferTitle + "' è stata eliminata correttamente.");
		redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
		return "redirect:/pizzas/" + pizzaId;
	}
}
