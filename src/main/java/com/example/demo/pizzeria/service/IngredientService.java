package com.example.demo.pizzeria.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.pizzeria.model.Ingredient;
import com.example.demo.pizzeria.repo.IngredientRepository;

@Service
public class IngredientService {
	
	@Autowired
	private IngredientRepository repo;
	
	public List<Ingredient> findAllSortedByName(){
		return repo.findAll(Sort.by("name"));
	}
	
	public Ingredient getById(Integer id) {
		return repo.findById(id).get();
	}
	
	public Ingredient create(Ingredient ingredient) {
		return repo.save(ingredient);
	}
	
	public Ingredient update(Ingredient ingredient) {
		ingredient.setUpdatedAt(LocalDateTime.now());
		return repo.save(ingredient);
	}
	
	public void deleteById(Integer id) {
		repo.deleteById(id);
	}

}
