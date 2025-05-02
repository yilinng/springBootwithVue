package com.practice.mysql.bookapi.service;

import com.practice.mysql.bookapi.dto.ReservationDto;
import com.practice.mysql.bookapi.dto.ReservationsResponse;
import com.practice.mysql.bookapi.model.Reservation;

public interface ReservationService {

  ReservationDto createReservation(Reservation reservation);

  ReservationsResponse getAllReservations(int pageNo, int pageSize);

  ReservationDto getReservationById(int id);

  ReservationDto updateReservation(ReservationDto reservationDto, int id);

  void deleteReservationById(int id);
}
