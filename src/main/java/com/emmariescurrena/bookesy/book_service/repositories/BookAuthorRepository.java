package com.emmariescurrena.bookesy.book_service.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emmariescurrena.bookesy.book_service.models.BookAuthor;

@Repository
public interface BookAuthorRepository extends JpaRepository<BookAuthor, Long> {
    List<BookAuthor> findByAuthorId(String authorId);
    List<BookAuthor> findByBookId(String bookId);
}
