package com.emmariescurrena.bookesy.book_service.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.emmariescurrena.bookesy.book_service.models.BookAuthor;

import reactor.core.publisher.Flux;

@Repository
public interface BookAuthorRepository extends ReactiveCrudRepository<BookAuthor, Long> {
    Flux<BookAuthor> findByAuthorId(String authorId);

    @Query("SELECT author_id FROM books_authors WHERE book_id = :bookId")
    Flux<String> findAuthorsIdsByBookId(@Param("bookId") String bookId);
}
