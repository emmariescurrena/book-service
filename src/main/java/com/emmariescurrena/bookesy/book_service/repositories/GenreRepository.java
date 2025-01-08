package com.emmariescurrena.bookesy.book_service.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.emmariescurrena.bookesy.book_service.models.Genre;

import reactor.core.publisher.Mono;

@Repository
public interface GenreRepository extends ReactiveCrudRepository<Genre, String> {

    @Query("SELECT * FROM genres WHERE name = $1")
    Mono<Genre> findByName(String name);

    @Query("INSERT INTO genres (name) VALUES (:name) ON CONFLICT (name) DO NOTHING RETURNING *")
    Mono<Genre> saveIfNotExists(@Param("name") String name);

}
