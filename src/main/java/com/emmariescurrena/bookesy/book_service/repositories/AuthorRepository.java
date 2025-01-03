package com.emmariescurrena.bookesy.book_service.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.emmariescurrena.bookesy.book_service.models.Author;

import reactor.core.publisher.Mono;

@Repository
public interface AuthorRepository extends ReactiveCrudRepository<Author, String> {
    @Query("INSERT INTO authors (id, name) VALUES (:id, :name) ON CONFLICT (id) DO NOTHING RETURNING *")
    Mono<Author> saveIfNotExists(@Param("id") String id, @Param("name") String name);
}
