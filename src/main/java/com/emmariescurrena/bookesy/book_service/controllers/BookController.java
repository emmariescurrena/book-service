package com.emmariescurrena.bookesy.book_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.emmariescurrena.bookesy.book_service.dtos.BookDetailsDto;
import com.emmariescurrena.bookesy.book_service.models.Author;
import com.emmariescurrena.bookesy.book_service.models.Book;
import com.emmariescurrena.bookesy.book_service.services.AuthorService;
import com.emmariescurrena.bookesy.book_service.services.BookService;
import com.emmariescurrena.bookesy.book_service.services.BookTransactionService;
import com.emmariescurrena.bookesy.book_service.services.OpenLibraryService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



@RestController
@RequestMapping("/api")
public class BookController {
    
    @Autowired
    BookTransactionService bookTransactionService;

    @Autowired
    BookService bookService;

    @Autowired
    AuthorService authorService;

    @Autowired
    OpenLibraryService openLibraryService;
    
    @GetMapping("/books/search")
    public Flux<BookDetailsDto> searchBooks(@RequestParam String query, @RequestParam Integer page) {
        return openLibraryService.searchBooksIds(query, page)
                                .collectList()
                                .flatMapMany(bookTransactionService::findOrSaveBooks);
    }

    @GetMapping("/books/{bookId}")
    public Mono<Book> getBook(@PathVariable String bookId) {
        return bookService.findOrSaveBook(bookId);
    }

    @GetMapping("/authors/{authorId}")
    public Mono<Author> getAuthor(@PathVariable String authorId) {
        return authorService.findOrSaveAuthor(authorId);
    }

}