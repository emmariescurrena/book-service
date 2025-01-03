package com.emmariescurrena.bookesy.book_service.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.emmariescurrena.bookesy.book_service.models.BookGenre;

import reactor.core.publisher.Flux;

@Repository
public interface BookGenreRepository extends ReactiveCrudRepository<BookGenre, Long> {
    Flux<BookGenre> findByGenreName(String genreName);

    @Query("SELECT genre_name FROM books_genres WHERE book_id = :bookId")
    Flux<String> findGenresByBookId(@Param("bookId") String bookId);
}
