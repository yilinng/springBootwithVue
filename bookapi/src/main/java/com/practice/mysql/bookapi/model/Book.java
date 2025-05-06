package com.practice.mysql.bookapi.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "title")
	private String title;

	@Column(name = "author")
	private String author;

	@Column(unique = true, name = "number")
	private String number;

	@Column(name = "create_date")
	private LocalDate create_date;

	@Column(name = "update_date")
	private LocalDate update_date;

	@Column
	private int price;

	@Column
	private int inventory;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	// https://stackoverflow.com/questions/62623483/i-am-using-manytoone-onetomany-and-have-endless-loop-when-getting-data
	@ManyToMany(mappedBy = "books", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Reservation> reservations = new ArrayList<Reservation>();

}
