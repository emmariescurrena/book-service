package com.emmariescurrena.bookesy.book_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emmariescurrena.bookesy.book_service.exceptions.NotFoundException;
import com.emmariescurrena.bookesy.book_service.models.Genre;
import com.emmariescurrena.bookesy.book_service.repositories.GenreRepository;

import reactor.core.publisher.Mono;

@Service
public class GenreService {
    
    @Autowired
    GenreRepository genreRepository;

    public Mono<Genre> findOrSaveGenre(String genreName) {
        return genreRepository.findByName(genreName)
                .switchIfEmpty(
                    genreRepository.saveIfNotExists(genreName)
                );
    }

    public Mono<Genre> findGenre(String genreId) {
        return genreRepository.findById(genreId)
            .switchIfEmpty(Mono.error(new NotFoundException(String.format("Author with %s id was not found", genreId))));
    }

}
