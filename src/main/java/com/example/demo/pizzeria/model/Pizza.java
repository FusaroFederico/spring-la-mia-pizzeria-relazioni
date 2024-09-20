package com.example.demo.pizzeria.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "pizzas")
public class Pizza {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotEmpty(message = "non può essere vuoto")
	@NotNull
	@Size(min=2, max=255, message = "il nome deve avere almeno 2 e massimo 255 caratteri")
	@Column(name="name", nullable=false)
	private String name;
	
	@NotEmpty(message = "non può essere vuoto")
	@NotNull
	@Size(min=10, max=255, message = "la descrizione deve avere almeno 10 e massimo 255 caratteri")
	@Column(name="description", nullable=false)
	private String description;
	
	@NotEmpty(message = "non può essere vuoto")
	@NotNull
	@Column(name="photo_url", nullable=false)
	private String photoUrl;
	
	@Column(name="created_at", nullable=true, updatable = false)
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@Column(name="updated_at", nullable=true)
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
	@NotNull(message = "non può essere vuoto")
	@DecimalMin(value = "0.01", message = "il prezzo non può essere inferiore a 0.01") //@Positive
	@DecimalMax(value = "100.00", message = "il prezzo non può essere superiore a 100")
	@Column(name="price", nullable=false)
	private Double price;
	
	@OneToMany(mappedBy = "pizza", cascade = { CascadeType.REMOVE })
	private List<SpecialOffer> specialOffers;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "ingredient_pizza", 
			   joinColumns = @JoinColumn(name = "pizza_id"), 
			   inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
	private List<Ingredient> ingredients;
	
	@Transient
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
	
	// date formatted 
	public String getFormattedCreatedAt(){
		if(createdAt != null) {
			return createdAt.format(dateFormatter);
		}
		return "";
	}
	public String getFormattedUpdatedAt(){
		if(updatedAt != null) {
			return updatedAt.format(dateFormatter);
		}
		return "";
	}
	
	// price formatted
	public String getStandardPrice(){
		return String.format("%.2f", this.price);
	}
	
	// getters + setters
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	public List<SpecialOffer> getSpecialOffers() {
		return specialOffers;
	}
	public void setSpecialOffers(List<SpecialOffer> specialOffers) {
		this.specialOffers = specialOffers;
	}
	public List<Ingredient> getIngredients() {
		return ingredients;
	}
	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	
	
}
