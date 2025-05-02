package com.practice.mysql.bookapi.dto;

import java.util.List;

import lombok.Data;

@Data
public class BooksResponse {

  private List<BookDto> content;
  private int pageNo;
  private int pageSize;
  private long totalElements;
  private int totalPages;
  private boolean last;
}
