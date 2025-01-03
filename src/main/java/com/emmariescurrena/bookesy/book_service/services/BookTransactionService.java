package com.emmariescurrena.bookesy.book_service.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emmariescurrena.bookesy.book_service.dtos.BookDetailsDto;
import com.emmariescurrena.bookesy.book_service.dtos.BookSearchResultDto;
import com.emmariescurrena.bookesy.book_service.models.Author;
import com.emmariescurrena.bookesy.book_service.models.Book;
import com.emmariescurrena.bookesy.book_service.models.Genre;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BookTransactionService {

    @Autowired
    AuthorService authorService;

    @Autowired
    BookService bookService;
    
    @Autowired
    GenreService genreService;
    
    @Autowired
    BookAuthorService bookAuthorService;

    @Autowired
    BookGenreService bookGenreService;

    @Autowired
    OpenLibraryService openLibraryService;

    public Flux<BookDetailsDto> findOrSaveBooks(List<BookSearchResultDto> bookSearchResultDtos) {
        return Flux.fromIterable(bookSearchResultDtos)
                    .flatMap(this::findOrSaveBook);
    }

    private Mono<BookDetailsDto> findOrSaveBook(BookSearchResultDto bookSearchResultDto) {
        return bookService.findOrSaveBook(bookSearchResultDto)
            .flatMap(book ->
                Mono.zip(
                    bookAuthorService.getAuthorsIdsByBookId(book.getId())
                    .flatMap(authorService::findAuthor).collectList(),
                    bookGenreService.getGenresByBookId(book.getId())
                    .flatMap(genreService::findGenre).collectList()
                ).map(tuple -> buildBookDetailsDto(book, tuple.getT1(), tuple.getT2()))
            );
    }

    public Mono<BookDetailsDto> findBook(String bookId) {
        return bookService.findBook(bookId)
            .flatMap(book ->
                Mono.zip(
                    bookAuthorService.getAuthorsIdsByBookId(book.getId()).flatMap(authorService::findAuthor).collectList(),
                    bookGenreService.getGenresByBookId(book.getId()).flatMap(genreService::findGenre).collectList()
                ).map(tuple -> buildBookDetailsDto(book, tuple.getT1(), tuple.getT2()))
            );
    }

    private BookDetailsDto buildBookDetailsDto(Book book, List<Author> authors, List<Genre> genres) {
        BookDetailsDto bookDetailsDto = new BookDetailsDto();
        BeanUtils.copyProperties(book, bookDetailsDto);
        bookDetailsDto.setAuthors(authors);
        bookDetailsDto.setGenres(genres);
        return bookDetailsDto;
    }

}
