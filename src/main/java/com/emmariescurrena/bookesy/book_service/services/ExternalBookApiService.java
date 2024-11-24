package com.emmariescurrena.bookesy.book_service.services;

import com.emmariescurrena.bookesy.book_service.dtos.BookSearchResultDto;
import com.emmariescurrena.bookesy.book_service.dtos.ExternalAuthorApiDto;
import com.emmariescurrena.bookesy.book_service.dtos.ExternalBookApiDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ExternalBookApiService {
    Flux<BookSearchResultDto> searchBooksIds(String bookName, Integer page);
    Mono<? extends ExternalBookApiDto> getBook(String bookId);
    Mono<? extends ExternalAuthorApiDto> getAuthor(String authorId);
}

