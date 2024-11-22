package com.emmariescurrena.bookesy.book_service.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emmariescurrena.bookesy.book_service.models.Genre;
import com.emmariescurrena.bookesy.book_service.repositories.GenreRepository;

import reactor.core.publisher.Mono;

@Service
public class GenreService {
    
    @Autowired
    GenreRepository genreRepository;

    public Mono<Genre> findOrSaveGenre(String genreName) {
        return Mono.defer(() -> {
            Optional<Genre> optionalGenre = genreRepository.findById(genreName);
            if (optionalGenre.isPresent()) {
                return Mono.just(optionalGenre.get());
            }

            Genre genre = new Genre();
            genre.setName(genreName);
            return Mono.fromCallable(() -> genreRepository.save(genre));
        });
    }

}
