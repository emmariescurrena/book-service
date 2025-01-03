package com.emmariescurrena.bookesy.book_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emmariescurrena.bookesy.book_service.exceptions.NotFoundException;
import com.emmariescurrena.bookesy.book_service.models.Author;
import com.emmariescurrena.bookesy.book_service.repositories.AuthorRepository;

import reactor.core.publisher.Mono;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    OpenLibraryService openLibraryService;

    public Mono<Author> findOrSaveAuthor(String authorId) {
        return authorRepository.findById(authorId)
            .switchIfEmpty(
                openLibraryService.getAuthor(authorId)
                    .flatMap(externalAuthorApiDto -> {
                        return authorRepository.saveIfNotExists(
                            externalAuthorApiDto.getId(),
                            externalAuthorApiDto.getName()
                        );
                    })
            );
    }

    public Mono<Author> findAuthor(String authorId) {
        return authorRepository.findById(authorId)
            .switchIfEmpty(Mono.error(new NotFoundException(String.format("Author with %s id was not found", authorId))));
    }

}
