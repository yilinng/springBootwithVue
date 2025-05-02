package com.practice.mysql.bookapi.dto;

import java.util.List;

import lombok.Data;

@Data
public class ReservationInfo {
	private int id;
	private String title;
	private String create_date;
	private String update_date;
	private List<BookDto> books;
	private int price;
	private UserDto user;
}
