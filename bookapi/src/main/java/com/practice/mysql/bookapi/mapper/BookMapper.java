package com.practice.mysql.bookapi.mapper;

import com.practice.mysql.bookapi.dto.BookDto;
import com.practice.mysql.bookapi.model.Book;

public class BookMapper {

  public static BookDto mapToDto(Book book) {
    BookDto bookDto = new BookDto();
    bookDto.setId(book.getId());
    bookDto.setTitle(book.getTitle());
    bookDto.setAuthor(book.getAuthor());
    bookDto.setCreate_date(book.getCreate_date());
    bookDto.setUpdate_date(book.getUpdate_date());
    bookDto.setPrice(book.getPrice());
    bookDto.setNumber(book.getNumber());
    bookDto.setUser_id(book.getUser().getId());
    return bookDto;
  }

  public static Book mapToEntity(BookDto bookDto) {
    Book book = new Book();
    book.setId(bookDto.getId());
    book.setTitle(bookDto.getTitle());
    book.setAuthor(bookDto.getAuthor());
    book.setPrice(bookDto.getPrice());
    book.setNumber(bookDto.getNumber());
    book.setCreate_date(bookDto.getCreate_date());
    book.setUpdate_date(bookDto.getUpdate_date());
    // book.setUser(bookDto.getUser_id());
    return book;
  }

}
