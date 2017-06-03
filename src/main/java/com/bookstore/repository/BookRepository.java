package com.bookstore.repository;

import com.bookstore.domain.Book;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
 
@Service
public interface BookRepository extends CrudRepository<Book, Long>{

}
