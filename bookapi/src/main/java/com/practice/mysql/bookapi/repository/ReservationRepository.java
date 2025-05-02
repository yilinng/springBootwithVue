package com.practice.mysql.bookapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practice.mysql.bookapi.model.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

}
