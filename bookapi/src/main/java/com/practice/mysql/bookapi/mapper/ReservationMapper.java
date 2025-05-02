package com.practice.mysql.bookapi.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.practice.mysql.bookapi.dto.BookDto;
import com.practice.mysql.bookapi.dto.UserDto;
import com.practice.mysql.bookapi.dto.ReservationDto;
import com.practice.mysql.bookapi.dto.ReservationInfo;
import com.practice.mysql.bookapi.model.Book;
import com.practice.mysql.bookapi.model.Reservation;

public class ReservationMapper {
	public static ReservationDto mapToDto(Reservation reservation) {
		ReservationDto reservationDto = new ReservationDto();

		reservationDto.setId(reservation.getId());
		reservationDto.setTitle(reservation.getTitle());
		reservationDto.setCreateDate(reservation.getCreated_date());
		reservationDto.setUpdateDate(reservation.getUpdate_date());
		reservationDto.setUser_id(reservation.getUser().getId());
		reservationDto.setBooks(reservation.getBooks());

		reservationDto.setPrice(reservation.getPrice());
		return reservationDto;
	}

	public static Reservation mapToEntity(ReservationDto reservationDto) {
		Reservation reservation = new Reservation();

		LocalDate createDate = reservationDto.getCreateDate();
		LocalDate updateDate = reservationDto.getUpdateDate();

		reservation.setId(reservationDto.getId());
		reservation.setTitle(reservationDto.getTitle());
		reservation.setCreated_date(createDate);
		reservation.setUpdate_date(updateDate);

		// reservation.setUser(userDto);
		// reservation.setBook(bookDto);

		return reservation;
	}

	public static ReservationInfo mapToReservationInfo(Reservation reservation) {
		ReservationInfo reservationInfo = new ReservationInfo();

		List<BookDto> bookDto = new ArrayList<BookDto>();
		for (Book book : reservation.getBooks()) {

			bookDto.add(BookMapper.mapToDto(book));
		}

		UserDto userDto = UserMapper.mapToDto(reservation.getUser());

		reservationInfo.setId(reservation.getId());
		reservationInfo.setTitle(reservation.getTitle());
		reservationInfo.setCreate_date(reservation.getCreated_date().toString());
		reservationInfo.setUpdate_date(reservation.getUpdate_date().toString());
		reservationInfo.setBooks(bookDto);
		reservationInfo.setPrice(reservation.getPrice());
		reservationInfo.setUser(userDto);

		return reservationInfo;
	}

	public static List<ReservationInfo> mapElementsToReservationInfo(List<Reservation> reservationList) {
		return reservationList.stream().map((ReservationMapper::mapToReservationInfo)).collect(Collectors.toList());
	}
}
