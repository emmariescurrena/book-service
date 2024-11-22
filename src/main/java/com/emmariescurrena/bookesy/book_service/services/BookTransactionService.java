package com.emmariescurrena.bookesy.book_service.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emmariescurrena.bookesy.book_service.dtos.BookDetailsDto;
import com.emmariescurrena.bookesy.book_service.models.Author;
import com.emmariescurrena.bookesy.book_service.models.Book;

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
    OpenLibraryService openLibraryService;

    @Transactional
    public Flux<BookDetailsDto> findOrSaveBooks(List<String> bookIds) {
        return Flux.fromIterable(bookIds)
                    .flatMap(this::findOrSaveBook);
    }

    private Mono<BookDetailsDto> findOrSaveBook(String bookId) {
        return bookService.findOrSaveBook(bookId)
                          .flatMap(book -> bookAuthorService.getAuthorsByBookId(book.getId())
                                                            .collectList()
                                                            .map(authors -> buildBookDetailsDto(book, authors)));
    }
    

    private BookDetailsDto buildBookDetailsDto(Book book, List<Author> authors) {
        BookDetailsDto bookDetailsDto = new BookDetailsDto();
        BeanUtils.copyProperties(book, bookDetailsDto);
        bookDetailsDto.setAuthors(authors);
        return bookDetailsDto;
    }

}
