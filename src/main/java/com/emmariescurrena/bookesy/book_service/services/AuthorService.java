package com.emmariescurrena.bookesy.book_service.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emmariescurrena.bookesy.book_service.models.Author;
import com.emmariescurrena.bookesy.book_service.repositories.AuthorRepository;

@Service
public class AuthorService {
    
    @Autowired
    AuthorRepository authorRepository;

    public Author findOrSaveAuthor(Author author) {
        return authorRepository.findById(author.getId())
                .orElseGet(() -> authorRepository.save(author));
    }

    public Optional<Author> getAuthorById(String id) {
        return authorRepository.findById(id);
    }

}
