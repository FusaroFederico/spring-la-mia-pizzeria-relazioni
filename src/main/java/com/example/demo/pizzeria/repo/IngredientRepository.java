package com.example.demo.pizzeria.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.pizzeria.model.Ingredient;

public interface IngredientRepository  extends JpaRepository<Ingredient, Integer>{
	
	// query particolari
	public List<Ingredient> findByNameContainingIgnoreCaseOrderByNameAsc(String name);
}
