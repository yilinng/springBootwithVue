package com.practice.mysql.bookapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.practice.mysql.bookapi.dto.BookDto;
import com.practice.mysql.bookapi.dto.BooksResponse;

import com.practice.mysql.bookapi.model.Book;

import com.practice.mysql.bookapi.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

  @Autowired
  private BookService bookService;

  @GetMapping("/")
  public ResponseEntity<BooksResponse> getBooks(
      @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
      @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
    return new ResponseEntity<>(bookService.getAllBooks(pageNo, pageSize), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<BookDto> bookDetail(@PathVariable int id) {
    return new ResponseEntity<>(bookService.getBookById(id), HttpStatus.OK);
  }

  // https://docs.spring.io/spring-security/reference/servlet/authorization/method-security.html
  @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('EMPLOYEE')")
  @PostMapping("/")
  public ResponseEntity<BookDto> createBook(@RequestBody Book book) {
    return new ResponseEntity<>(bookService.createBook(book), HttpStatus.CREATED);
  }

  @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('EMPLOYEE')")
  @PutMapping("/{id}")
  public ResponseEntity<BookDto> updateBook(
      @RequestBody BookDto bookDto, @PathVariable("id") int bookId) {
    BookDto response = bookService.updateBook(bookDto, bookId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('EMPLOYEE')")
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteBook(@PathVariable("id") int bookId) {
    bookService.deleteBookById(bookId);
    return new ResponseEntity<>("Book deleted", HttpStatus.OK);
  }

  // https://stackoverflow.com/questions/495426/restful-url-design-how-to-query-using-or-between-parameters
  // https://stackoverflow.com/questions/13715811/requestparam-vs-pathvariable
  @GetMapping("/search/")
  ResponseEntity<BooksResponse> searchBook(
      @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
      @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
      @RequestParam("txt") String txt) {

    return new ResponseEntity<>(bookService.searchBook(pageNo, pageSize, txt), HttpStatus.OK);
  }

}
