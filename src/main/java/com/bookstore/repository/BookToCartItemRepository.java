package com.bookstore.repository;

import org.springframework.data.repository.CrudRepository;

import com.bookstore.domain.BookToCartItem;

public interface BookToCartItemRepository extends CrudRepository<BookToCartItem, Long> {

}
