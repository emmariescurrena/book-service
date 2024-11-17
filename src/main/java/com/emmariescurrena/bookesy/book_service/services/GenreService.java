package com.emmariescurrena.bookesy.book_service.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emmariescurrena.bookesy.book_service.models.Genre;
import com.emmariescurrena.bookesy.book_service.repositories.GenreRepository;

@Service
public class GenreService {
    
    @Autowired
    GenreRepository genreRepository;

    public Genre findOrSaveGenre(Genre genre) {
        return genreRepository.findById(genre.getId())
                .orElseGet(() -> genreRepository.save(genre));
    }

    public Optional<Genre> getGenreById(Long id) {
        return genreRepository.findById(id);
    }

}
