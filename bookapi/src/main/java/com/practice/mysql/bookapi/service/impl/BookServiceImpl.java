package com.practice.mysql.bookapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.practice.mysql.bookapi.dto.BookDto;
import com.practice.mysql.bookapi.dto.BooksResponse;

import com.practice.mysql.bookapi.exception.BookNotFoundException;

import com.practice.mysql.bookapi.mapper.BookMapper;

import com.practice.mysql.bookapi.model.Book;

import com.practice.mysql.bookapi.repository.BookRepository;
import com.practice.mysql.bookapi.repository.UserRepository;
import com.practice.mysql.bookapi.security.IAuthenticationFacade;
import com.practice.mysql.bookapi.service.BookService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private IAuthenticationFacade authenticationFacade;

	@Override
	public BookDto createBook(Book book) {
		final String uuid = UUID.randomUUID().toString();
		book.setNumber(uuid);

		LocalDate createDate = LocalDate.now();
		book.setCreate_date(createDate);

		System.out.println("create book " + getUser());

		book.setUser(userRepository.findByEmail(getUser().getUsername()));

		Book newBook = bookRepository.save(book);
		return BookMapper.mapToDto(newBook);
	}

	@Override
	public BooksResponse getAllBooks(int pageNo, int pageSize) {
		PageRequest pageable = PageRequest.of(pageNo, pageSize);
		Page<Book> books = bookRepository.findAll(pageable);
		List<Book> listOfBooks = books.getContent();
		List<BookDto> content = listOfBooks.stream().map(BookMapper::mapToDto).collect(Collectors.toList());

		BooksResponse booksResponse = new BooksResponse();
		booksResponse.setContent(content);
		booksResponse.setPageNo(books.getNumber());
		booksResponse.setPageSize(books.getSize());
		booksResponse.setTotalElements(books.getTotalElements());
		booksResponse.setTotalPages(books.getTotalPages());
		booksResponse.setLast(books.isLast());
		return booksResponse;
	}

	@Override
	public BookDto getBookById(int id) {
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new BookNotFoundException("Book could not be found"));
		return BookMapper.mapToDto(book);
	}

	@Override
	public BookDto updateBook(BookDto bookDto, int id) {
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new BookNotFoundException("Book could not be found"));
		book.setTitle(bookDto.getTitle());
		book.setAuthor(bookDto.getAuthor());
		book.setPrice(bookDto.getPrice());

		LocalDate updateDate = LocalDate.now();
		book.setUpdate_date(updateDate);

		Book updateBook = bookRepository.save(book);

		return BookMapper.mapToDto(updateBook);
	}

	@Override
	public void deleteBookById(int id) {
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new BookNotFoundException("Book could not be found"));
		bookRepository.delete(book);
	}

	@Override
	public BooksResponse searchBook(int pageNo, int pageSize, String txt) {

		BooksResponse allBook = getAllBooks(pageNo, pageSize);

		// filter double quotes
		// https://stackoverflow.com/questions/19299788/how-to-replace-double-quotes-in-a-string-with-in-java
		String filterTxt = txt.replaceAll("\"", "");

		List<BookDto> filteredBook = new ArrayList<BookDto>();

		for (BookDto i : allBook.getContent()) {
			System.out.println(i.getTitle());

			if (i.getTitle().toLowerCase().contains(filterTxt.toLowerCase())) {
				filteredBook.add(i);
			}
		}

		allBook.setContent(filteredBook);
		allBook.setTotalElements(filteredBook.size());

		return allBook;

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
