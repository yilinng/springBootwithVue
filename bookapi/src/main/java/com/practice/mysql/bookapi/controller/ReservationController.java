package com.practice.mysql.bookapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.practice.mysql.bookapi.dto.ReservationDto;
import com.practice.mysql.bookapi.dto.ReservationsResponse;
import com.practice.mysql.bookapi.model.Reservation;
import com.practice.mysql.bookapi.service.ReservationService;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

  @Autowired
  private ReservationService reservationService;

  @GetMapping("/")
  public ResponseEntity<ReservationsResponse> getReservations(
      @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
      @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
    return new ResponseEntity<>(reservationService.getAllReservations(pageNo, pageSize), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ReservationDto> reservationDetail(@PathVariable int id) {
    return new ResponseEntity<>(reservationService.getReservationById(id), HttpStatus.OK);
  }

  @PostMapping("/")
  public ResponseEntity<ReservationDto> createReservation(@RequestBody Reservation reservation) {
    return new ResponseEntity<>(reservationService.createReservation(reservation), HttpStatus.CREATED);
  }

  @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('EMPLOYEE')")
  @PutMapping("/{id}")
  public ResponseEntity<ReservationDto> updateReservation(@RequestBody ReservationDto reservationDto,
      @RequestParam int reservationId) {
    ReservationDto response = reservationService.updateReservation(reservationDto, reservationId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteReservation(@PathVariable("id") int reservationId) {
    reservationService.deleteReservationById(reservationId);
    return new ResponseEntity<>("Reservation deleted", HttpStatus.OK);
  }

}
