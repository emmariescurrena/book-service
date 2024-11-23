package com.emmariescurrena.bookesy.book_service.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.emmariescurrena.bookesy.book_service.models.Book;
import com.emmariescurrena.bookesy.book_service.repositories.BookRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    OpenLibraryService openLibraryService;

    @Autowired
    AuthorService authorService;

    @Autowired
    GenreService genreService;

    @Autowired
    BookAuthorService bookAuthorService;


    @Cacheable("books")
    public Mono<Book> findOrSaveBook(String bookId) {
        return Mono.justOrEmpty(bookRepository.findById(bookId))
            .switchIfEmpty(
                openLibraryService.getBook(bookId)
                    .flatMap(externalBookApiDto -> {
                        Book book = new Book();
                        BeanUtils.copyProperties(externalBookApiDto, book, "genres", "authorsIds");

                        return Flux.fromIterable(externalBookApiDto.getGenres() == null ? List.of() : externalBookApiDto.getGenres())
                                    .flatMap(genreService::findOrSaveGenre)
                                    .collectList()
                                    .flatMap(genres -> {
                                        book.setGenres(genres);
                                        return Mono.fromCallable(() -> bookRepository.save(book))
                                            .doOnNext(savedBook -> 
                                                bookAuthorService.linkAuthorsToBook(savedBook, externalBookApiDto.getAuthorsIds()));
                                    });
                    })
            );
    }

    public void deleteBook(Book book) {
        bookRepository.delete(book);
    }

}
