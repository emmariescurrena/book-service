package com.emmariescurrena.bookesy.book_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookSearchResultDto {
    private String bookId;
    private Integer publishedYear;
}
