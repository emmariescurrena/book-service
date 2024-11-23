package com.emmariescurrena.bookesy.book_service.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        return Mono.justOrEmpty(authorRepository.findById(authorId))
            .switchIfEmpty(
                openLibraryService.getAuthor(authorId)
                                .map(externalAuthorApiDto -> {
                                    Author author = new Author();
                                    BeanUtils.copyProperties(externalAuthorApiDto, author);
                                    return authorRepository.save(author);
                                }));

    }

}
