package com.example.demo.pizzeria.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.pizzeria.model.SpecialOffer;
import com.example.demo.pizzeria.repo.SpecialOfferRepository;

@Service
public class SpecialOfferService {

	@Autowired
	private SpecialOfferRepository repository;
	
	public List<SpecialOffer> findAllSortedByName(){
		return repository.findAll(Sort.by("startDate"));
	}
	
	public SpecialOffer getById(Integer id) {
		return repository.findById(id).get();
	}
	
	public SpecialOffer create(SpecialOffer SpecialOffer) {
		return repository.save(SpecialOffer);
	}
	
	public SpecialOffer update(SpecialOffer SpecialOffer) {
		SpecialOffer.setUpdatedAt(LocalDateTime.now());
		return repository.save(SpecialOffer);
	}
	
	public void deleteById(Integer id) {
		repository.deleteById(id);
	}
}
