package com.emmariescurrena.bookesy.book_service.dtos;

import java.util.List;

public record OpenLibraryResponseWithWorks(
    List<OpenLibraryBookDataDto> works
) {};

