 package com.emmariescurrena.bookesy.book_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emmariescurrena.bookesy.book_service.models.Author;
import com.emmariescurrena.bookesy.book_service.models.Book;
import com.emmariescurrena.bookesy.book_service.models.BookAuthor;
import com.emmariescurrena.bookesy.book_service.repositories.BookAuthorRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BookAuthorService {
    
    @Autowired
    private BookAuthorRepository bookAuthorRepository;

    public Flux<String> getAuthorsIdsByBookId(String bookId) {
        return bookAuthorRepository.findAuthorsIdsByBookId(bookId);
    }

    public Flux<String> getBooksIdsByAuthorId(String authorId) {
        return bookAuthorRepository.findByAuthorId(authorId)
                .map(BookAuthor::getBookId);
    }

    public Mono<Void> linkBookAndAuthor(Book book, Author author) {
        return bookAuthorRepository.save(new BookAuthor(book.getId(), author.getId())).then();
    }

}
