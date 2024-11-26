package com.emmariescurrena.bookesy.book_service.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.emmariescurrena.bookesy.book_service.dtos.BookSearchResultDto;
import com.emmariescurrena.bookesy.book_service.exceptions.NotFoundException;
import com.emmariescurrena.bookesy.book_service.models.Book;
import com.emmariescurrena.bookesy.book_service.models.Genre;
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
    public Mono<Book> findOrSaveBook(BookSearchResultDto bookSearchResultDto) {
        return Mono.justOrEmpty(bookRepository.findById(bookSearchResultDto.getBookId()))
            .switchIfEmpty(
                openLibraryService.getBook(bookSearchResultDto.getBookId())
                    .flatMap(externalBookApiDto -> {
                        Book book = new Book();
                        BeanUtils.copyProperties(externalBookApiDto, book, "genres", "authorsIds");

                        if (bookSearchResultDto.getPublishedYear() != null) {
                            book.setPublishedYear(bookSearchResultDto.getPublishedYear());
                        }

                        Mono<List<Genre>> genresMono = Flux.fromIterable(
                                Optional.ofNullable(externalBookApiDto.getGenres()).orElse(List.of()))
                            .flatMap(genreService::findOrSaveGenre)
                            .collectList();

                        return genresMono.flatMap(genres -> {
                            book.setGenres(genres);
                            return Mono.defer(() -> Mono.just(bookRepository.save(book)))
                                .flatMap(savedBook -> {
                                    return linkAuthorsToBook(savedBook, externalBookApiDto.getAuthorsIds());
                                });
                        });
                    })
            );
    }

    private Mono<Book> linkAuthorsToBook(Book book, List<String> authorsIds) {
        if (authorsIds == null || authorsIds.isEmpty()) {
            return Mono.just(book);
        }

        return Flux.fromIterable(authorsIds)
            .flatMap(authorService::findOrSaveAuthor)
            .doOnNext(author -> bookAuthorService.linkBookAndAuthor(book, author))
            .then(Mono.just(book));
    }

    public Mono<Book> findBook(String bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()) {
            throw new NotFoundException(String.format("Book with %s id was not found", bookId));
        }

        return Mono.just(optionalBook.get());
    }

    public void deleteBook(Book book) {
        bookRepository.delete(book);
    }

}
