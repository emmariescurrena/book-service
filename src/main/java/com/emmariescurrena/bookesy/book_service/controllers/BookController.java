package com.emmariescurrena.bookesy.book_service.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.emmariescurrena.bookesy.book_service.dtos.BookDetailsDto;
import com.emmariescurrena.bookesy.book_service.services.AuthorService;
import com.emmariescurrena.bookesy.book_service.services.BookService;
import com.emmariescurrena.bookesy.book_service.services.BookTransactionService;
import com.emmariescurrena.bookesy.book_service.services.OpenLibraryService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



@RestController
@RequestMapping("/api/books")
public class BookController {
    
    @Autowired
    BookTransactionService bookTransactionService;

    @Autowired
    BookService bookService;

    @Autowired
    AuthorService authorService;

    @Autowired
    OpenLibraryService openLibraryService;
    
    @GetMapping("/search")
    public Flux<BookDetailsDto> searchBooks(
        @RequestParam Optional<String> query,
        @RequestParam Optional<String> author,
        @RequestParam Integer page
    ) {
        return openLibraryService.searchBooksIds(query, author, page)
                                .collectList()
                                .flatMapMany(bookTransactionService::findOrSaveBooks);
    }

    @GetMapping("/genres/search")
    public Flux<BookDetailsDto> searchBooksByGenre(
        @RequestParam String genre,
        @RequestParam Integer page
    ) {
        return openLibraryService.searchBooksBySubject(genre, page)
                                .collectList()
                                .flatMapMany(bookTransactionService::findOrSaveBooks);
    }

    @GetMapping("/{bookId}")
    public Mono<BookDetailsDto> getBook(@PathVariable String bookId) {
        return bookTransactionService.findBook(bookId);
    }
    
}