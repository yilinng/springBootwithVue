package com.practice.mysql.bookapi.mapper;

import lombok.NoArgsConstructor;

import java.util.List;

import com.practice.mysql.bookapi.dto.UserDto;
import com.practice.mysql.bookapi.dto.UserReservationsResponse;
import com.practice.mysql.bookapi.dto.ReservationInfo;
import com.practice.mysql.bookapi.model.User;

@NoArgsConstructor
public class UserMapper {

	public static UserDto mapToDto(User user) {
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setUsername(user.getUsername());
		userDto.setEmail(user.getEmail());
		userDto.setRoles(user.getRoles());
		userDto.setBooks(user.getBooks());
		userDto.setReservations(user.getReservations());

		return userDto;
	}

	public static UserReservationsResponse mapToUserReservationsResponse(User user) {
		UserReservationsResponse userReservationsResponse = new UserReservationsResponse();

		List<ReservationInfo> reservationsInfo = ReservationMapper.mapElementsToReservationInfo(user.getReservations());

		userReservationsResponse.setId(user.getId());
		userReservationsResponse.setUsername(user.getUsername());

		userReservationsResponse.setEmail(user.getEmail());
		userReservationsResponse.setReservations(reservationsInfo);

		return userReservationsResponse;
	}
}
