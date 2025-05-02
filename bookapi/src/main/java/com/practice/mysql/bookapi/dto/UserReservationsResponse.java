package com.practice.mysql.bookapi.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserReservationsResponse {

	private int id;
	private String username;
	private String email;
	private List<ReservationInfo> reservations;
}
