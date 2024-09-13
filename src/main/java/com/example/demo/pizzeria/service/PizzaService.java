package com.example.demo.pizzeria.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.pizzeria.model.Pizza;
import com.example.demo.pizzeria.repo.PizzaRepository;

@Service
public class PizzaService {

	@Autowired
	private PizzaRepository repo;
	
	public List<Pizza> findAllSortedByName(){
		return repo.findAll(Sort.by("name"));
	}
	
	public List<Pizza> findListByName(String name){
		return repo.findByNameContainingIgnoreCaseOrderByNameAsc(name);
	}
	
	public Pizza getById(Integer id) {
		return repo.findById(id).get();
	}
	
	public Pizza create(Pizza pizza) {
		return repo.save(pizza);
	}
	
	public Pizza update(Pizza pizza) {
		pizza.setUpdatedAt(LocalDateTime.now());
		return repo.save(pizza);
	}
	
	public void deleteById(Integer id) {
		repo.deleteById(id);
	}
}
