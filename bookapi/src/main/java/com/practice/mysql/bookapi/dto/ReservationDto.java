package com.practice.mysql.bookapi.dto;

import java.time.LocalDate;
import java.util.List;

import com.practice.mysql.bookapi.model.Book;

import lombok.Data;

//import java.util.Date;

@Data
public class ReservationDto {
	private int id;
	private String title;
	private LocalDate createDate;
	private LocalDate updateDate;
	private List<Book> books;
	private int price;
	private int user_id;
}
