package com.emmariescurrena.bookesy.book_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emmariescurrena.bookesy.book_service.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    
}
