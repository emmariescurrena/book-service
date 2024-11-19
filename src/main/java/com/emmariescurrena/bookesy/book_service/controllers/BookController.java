package com.emmariescurrena.bookesy.book_service.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.emmariescurrena.bookesy.book_service.services.BookTransactionService;
import com.emmariescurrena.bookesy.book_service.services.OpenLibraryService;



@RestController
@RequestMapping("/api/books")
public class BookController {
    
    @Autowired
    BookTransactionService bookTransactionService;

    @Autowired
    OpenLibraryService openLibraryService;

    @GetMapping("/search")
    public ResponseEntity<List<String>> searchBooksIds(@RequestParam String query) {
        return ResponseEntity.ok(openLibraryService.searchBooksIds(query, 1));
    }

}
