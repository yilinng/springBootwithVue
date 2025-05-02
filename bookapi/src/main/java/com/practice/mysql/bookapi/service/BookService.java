package com.practice.mysql.bookapi.service;

import com.practice.mysql.bookapi.dto.BookDto;
import com.practice.mysql.bookapi.dto.BooksResponse;

import com.practice.mysql.bookapi.model.Book;

public interface BookService {

  BookDto createBook(Book book);

  BooksResponse getAllBooks(int pageNo, int pageSize);

  BookDto getBookById(int id);

  BookDto updateBook(BookDto bookDto, int id);

  void deleteBookById(int id);

  BooksResponse searchBook(int pageNo, int pageSize, String txt);

}
