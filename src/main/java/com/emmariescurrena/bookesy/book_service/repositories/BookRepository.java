package com.emmariescurrena.bookesy.book_service.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.emmariescurrena.bookesy.book_service.models.Book;

@Repository
public interface BookRepository extends ReactiveCrudRepository<Book, String> {
    
}
