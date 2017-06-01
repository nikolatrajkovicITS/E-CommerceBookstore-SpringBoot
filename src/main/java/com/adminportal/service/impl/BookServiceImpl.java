package com.adminportal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.adminportal.domain.Book;
import com.adminportal.repository.BookRepository;
import com.adminportal.service.BookService;

public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;
	
	public Book save(Book book) {
		return bookRepository.save(book);
	}
}
