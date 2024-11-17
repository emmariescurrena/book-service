package com.emmariescurrena.bookesy.book_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emmariescurrena.bookesy.book_service.models.Author;
import com.emmariescurrena.bookesy.book_service.models.Book;
import com.emmariescurrena.bookesy.book_service.models.BookAuthor;
import com.emmariescurrena.bookesy.book_service.repositories.BookAuthorRepository;

@Service
public class BookAuthorService {
    
    @Autowired
    BookAuthorRepository bookAuthorRepository;

    public void linkBookAndAuthor(Book book, Author author) {
        BookAuthor bookAuthor = new BookAuthor(book, author);
        bookAuthorRepository.save(bookAuthor);
    }

}
