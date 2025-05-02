package com.practice.mysql.bookapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practice.mysql.bookapi.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

}
