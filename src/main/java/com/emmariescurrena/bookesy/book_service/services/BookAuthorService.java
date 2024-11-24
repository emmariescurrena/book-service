package com.emmariescurrena.bookesy.book_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emmariescurrena.bookesy.book_service.models.Author;
import com.emmariescurrena.bookesy.book_service.models.Book;
import com.emmariescurrena.bookesy.book_service.models.BookAuthor;
import com.emmariescurrena.bookesy.book_service.repositories.BookAuthorRepository;

import reactor.core.publisher.Flux;

@Service
public class BookAuthorService {
    
    @Autowired
    BookAuthorRepository bookAuthorRepository;

    @Autowired
    AuthorService authorService;

    public Flux<Author> getAuthorsByBookId(String bookId) {
        return Flux.defer(() -> Flux.fromIterable(bookAuthorRepository.findByBookId(bookId)))
                .map(BookAuthor::getAuthor);
    }

    public Flux<Book> getBooksByAuthorId(String authorId) {
        return Flux.defer(() -> Flux.fromIterable(bookAuthorRepository.findByAuthorId(authorId)))
                .map(BookAuthor::getBook);
    }

    public void linkBookAndAuthor(Book book, Author author) {
        BookAuthor bookAuthor = new BookAuthor(book, author);
        bookAuthorRepository.save(bookAuthor);
    }

}
