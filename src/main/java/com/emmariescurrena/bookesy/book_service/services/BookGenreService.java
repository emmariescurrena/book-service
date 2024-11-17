package com.emmariescurrena.bookesy.book_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emmariescurrena.bookesy.book_service.models.Book;
import com.emmariescurrena.bookesy.book_service.models.BookGenre;
import com.emmariescurrena.bookesy.book_service.models.Genre;
import com.emmariescurrena.bookesy.book_service.repositories.BookGenreRepository;

@Service
public class BookGenreService {
    
    @Autowired
    BookGenreRepository bookGenreRepository;

    public void linkBookAndGenre(Book book, Genre genre) {
        BookGenre bookGenre = new BookGenre(book, genre);
        bookGenreRepository.save(bookGenre);
    }


}
