package com.practice.mysql.bookapi.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ReservationInfo {
	private int id;
	private String title;
	private LocalDate create_date;
	private LocalDate update_date;
	private List<BookDto> books = new ArrayList<BookDto>();
	private int price;
	private UserDto user;
}
