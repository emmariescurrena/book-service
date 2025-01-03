package com.emmariescurrena.bookesy.book_service.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emmariescurrena.bookesy.book_service.dtos.BookSearchResultDto;
import com.emmariescurrena.bookesy.book_service.exceptions.NotFoundException;
import com.emmariescurrena.bookesy.book_service.models.Book;
import com.emmariescurrena.bookesy.book_service.repositories.BookRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private OpenLibraryService openLibraryService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private BookAuthorService bookAuthorService;

    @Autowired
    private BookGenreService bookGenreService;


    public Mono<Book> findOrSaveBook(BookSearchResultDto bookSearchResultDto) {
        return bookRepository.findById(bookSearchResultDto.getBookId())
            .switchIfEmpty(
                openLibraryService.getBook(bookSearchResultDto.getBookId())
                    .flatMap(externalBookApiDto -> {
                        Book book = new Book();
                        BeanUtils.copyProperties(externalBookApiDto, book, "genres", "authorsIds");
                        if (bookSearchResultDto.getPublishedYear() != null) {
                            book.setPublishedYear(bookSearchResultDto.getPublishedYear());
                        }

                        return bookRepository.save(book)
                            .flatMap(savedBook ->
                                linkAuthorsToBook(savedBook, externalBookApiDto.getAuthorsIds())
                                    .then(linkGenresToBook(savedBook, externalBookApiDto.getGenres()))
                                    .thenReturn(savedBook)
                            );

                    })
            );
    }

    private Mono<Void> linkAuthorsToBook(Book book, List<String> authorsIds) {
        if (authorsIds == null || authorsIds.isEmpty()) {
            return Mono.empty();
        }

        return Flux.fromIterable(authorsIds)
        .flatMap(authorService::findOrSaveAuthor)
        .flatMap(author -> bookAuthorService.linkBookAndAuthor(book, author))
        .then();
    }

    private Mono<Void> linkGenresToBook(Book book, List<String> genresNames) {
        if (genresNames == null || genresNames.isEmpty()) {
            return Mono.empty();
        }



        return Flux.fromIterable(genresNames)
        .flatMap(genreService::findOrSaveGenre)
        .flatMap(genre -> bookGenreService.linkBookAndGenre(book, genre))
        .then();
    }

    public Mono<Book> findBook(String bookId) {
        return bookRepository.findById(bookId)
            .switchIfEmpty(Mono.error(new NotFoundException(String.format("Book with %s id was not found", bookId))));
    }

    public void deleteBook(Book book) {
        bookRepository.delete(book);
    }

}
