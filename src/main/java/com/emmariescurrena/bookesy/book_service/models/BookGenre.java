package com.emmariescurrena.bookesy.book_service.models;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("books_genres")
public class BookGenre {

    @Column("book_id")
    @NotNull(message = "The bookId is required")
    private String bookId;

    @Column("genre_name")
    @NotNull(message = "The genreName is required")
    private String genreName;

}
