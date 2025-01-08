package com.emmariescurrena.bookesy.book_service.dtos;

import java.util.List;

public record OpenLibraryResponseWithDocs(
    List<OpenLibraryBookDataDto> docs
) {};
