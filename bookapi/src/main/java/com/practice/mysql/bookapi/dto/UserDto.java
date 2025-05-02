package com.practice.mysql.bookapi.dto;

import java.util.Set;

import com.practice.mysql.bookapi.model.Role;

import lombok.Data;

@Data
public class UserDto {

	private int id;
	private String username;
	private String email;
	private Set<Role> roles;
}
