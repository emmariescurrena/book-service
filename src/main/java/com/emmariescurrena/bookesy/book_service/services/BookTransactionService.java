package com.emmariescurrena.bookesy.book_service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emmariescurrena.bookesy.book_service.models.Author;
import com.emmariescurrena.bookesy.book_service.models.Book;
import com.emmariescurrena.bookesy.book_service.models.Genre;

@Service
public class BookTransactionService {
    
    @Autowired
    AuthorService authorService;

    @Autowired
    BookService bookService;
    
    @Autowired
    GenreService genreService;
    
    @Autowired
    BookGenreService bookGenreService;
    
    @Autowired
    BookAuthorService bookAuthorService;

    @Transactional
    public void saveBookWithDetails(Book book, List<Author> authors, List<Genre> genres) {
        // Save the book
        Book savedBook = bookService.saveBook(book);

        // Save authors and link to book
        for (Author author : authors) {
            Author savedAuthor = authorService.findOrSaveAuthor(author);
            bookAuthorService.linkBookAndAuthor(savedBook, savedAuthor);
        }

        // Save genres and link to book
        for (Genre genre : genres) {
            Genre savedGenre = genreService.findOrSaveGenre(genre);
            bookGenreService.linkBookAndGenre(savedBook, savedGenre);
        }
    }

}
