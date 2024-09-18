package com.example.demo.pizzeria.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.pizzeria.model.SpecialOffer;

public interface SpecialOfferRepository extends JpaRepository<SpecialOffer, Integer>{
	
	// query particolari
}
