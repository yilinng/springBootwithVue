package com.practice.mysql.bookapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.practice.mysql.bookapi.dto.ReservationDto;
import com.practice.mysql.bookapi.dto.ReservationsResponse;
import com.practice.mysql.bookapi.exception.BookNotFoundException;

import com.practice.mysql.bookapi.exception.ReservationNotFoundException;

import com.practice.mysql.bookapi.mapper.ReservationMapper;
import com.practice.mysql.bookapi.model.Book;

import com.practice.mysql.bookapi.model.Reservation;
import com.practice.mysql.bookapi.repository.BookRepository;
import com.practice.mysql.bookapi.repository.UserRepository;
import com.practice.mysql.bookapi.security.IAuthenticationFacade;
import com.practice.mysql.bookapi.repository.ReservationRepository;

import com.practice.mysql.bookapi.service.ReservationService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private IAuthenticationFacade authenticationFacade;

	@Override
	public ReservationDto createReservation(Reservation reservation) {

		System.out.println("createReservation " + reservation);

		int price = 0;

		// List<Book> findBooks = new ArrayList<Book>();
		for (Book book : reservation.getBooks()) {
			// findBooks.add(book);
			price += book.getPrice();
			// System.out.println("createReservation book " + book);

		}

		reservation.setTitle(reservation.getTitle());

		LocalDate createDate = LocalDate.now();
		reservation.setCreated_date(createDate);

		reservation.setUser(userRepository.findByEmail(getUser().getUsername()));
		reservation.setPrice(price);
		reservation.setBooks(reservation.getBooks());

		Reservation newReservation = reservationRepository.save(reservation);
		return ReservationMapper.mapToDto(newReservation);
	}

	@Override
	public ReservationsResponse getAllReservations(int pageNo, int pageSize) {
		PageRequest pageable = PageRequest.of(pageNo, pageSize);
		Page<Reservation> reservations = reservationRepository.findAll(pageable);
		List<Reservation> listOfReservations = reservations.getContent();
		List<ReservationDto> content = listOfReservations.stream().map(ReservationMapper::mapToDto)
				.collect(Collectors.toList());

		ReservationsResponse reservationsResponse = new ReservationsResponse();
		reservationsResponse.setContent(content);
		reservationsResponse.setPageNo(reservations.getNumber());
		reservationsResponse.setPageSize(reservations.getSize());
		reservationsResponse.setTotalElements(reservations.getTotalElements());
		reservationsResponse.setTotalPages(reservations.getTotalPages());
		reservationsResponse.setLast(reservations.isLast());
		return reservationsResponse;
	}

	@Override
	public ReservationDto getReservationById(int id) {
		Reservation reservation = reservationRepository.findById(id)
				.orElseThrow(() -> new ReservationNotFoundException("Reservation could not be found"));
		return ReservationMapper.mapToDto(reservation);
	}

	@Override
	public ReservationDto updateReservation(ReservationDto reservationDto, int id) {
		Reservation reservation = reservationRepository.findById(id)
				.orElseThrow(() -> new ReservationNotFoundException("Reservation could not be found"));

		LocalDate updateDate = LocalDate.now();

		reservation.setTitle(reservationDto.getTitle());
		reservation.setCreated_date(reservationDto.getCreateDate());
		reservation.setUpdate_date(updateDate);
		reservation.setBooks(reservation.getBooks());

		Reservation updatedReservation = reservationRepository.save(reservation);

		return ReservationMapper.mapToDto(updatedReservation);
	}

	@Override
	public void deleteReservationById(int id) {
		Reservation reservation = reservationRepository.findById(id)
				.orElseThrow(() -> new ReservationNotFoundException("Reservation could not be found"));
		reservationRepository.delete(reservation);
	}

	// https://medium.com/@techphile/integration-tests-with-spring-security-af172626631b
	private User getUser() {
		User user = (User) authenticationFacade.getAuthentication().getPrincipal();
		// Authentication authentication = authenticationFacade.getAuthentication();

		// String username = authentication.getName();

		// System.out.println("getUser username");

		// System.out.println(username);

		/*
		 * User user = userRepository
		 * .findByUsernameOrEmail(username, username)
		 * .orElseThrow(() -> new
		 * UsernameNotFoundException("User not found with username or email: "));
		 */
		return user;
	}

}
