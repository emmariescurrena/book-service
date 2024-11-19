package com.emmariescurrena.bookesy.book_service.services;

import java.util.List;

import com.emmariescurrena.bookesy.book_service.dtos.ExternalAuthorApiDto;
import com.emmariescurrena.bookesy.book_service.dtos.ExternalBookApiDto;

public interface ExternalBookApiService {

    public abstract List<String> searchBooksIds(String bookName, Integer page);
    public abstract ExternalBookApiDto getBook(String bookId);
    public abstract ExternalAuthorApiDto getAuthor(String authorId);

}
