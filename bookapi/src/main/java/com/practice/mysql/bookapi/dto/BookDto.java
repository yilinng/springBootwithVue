package com.practice.mysql.bookapi.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class BookDto {
	private int id;
	private String title;
	private String author;
	private String number;
	private int price;
	private LocalDate create_date;
	private LocalDate update_date;
	private int user_id;
	private int inventory;
}
