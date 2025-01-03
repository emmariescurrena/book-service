package com.emmariescurrena.bookesy.book_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emmariescurrena.bookesy.book_service.models.Book;
import com.emmariescurrena.bookesy.book_service.models.BookGenre;
import com.emmariescurrena.bookesy.book_service.models.Genre;
import com.emmariescurrena.bookesy.book_service.repositories.BookGenreRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BookGenreService {

    @Autowired
    private BookGenreRepository bookGenreRepository;

    public Flux<String> getGenresByBookId(String bookId) {
        return bookGenreRepository.findGenresByBookId(bookId);
    }

    public Flux<String> getBooksByGenreId(String genreName) {
        return bookGenreRepository.findByGenreName(genreName)
                .map(BookGenre::getBookId);
    }

    public Mono<Void> linkBookAndGenre(Book book, Genre genre) {
        return bookGenreRepository.save(new BookGenre(book.getId(), genre.getName())).then();
    }

}
