package com.emmariescurrena.bookesy.book_service.dtos;

import java.time.LocalDate;
import java.util.List;

import com.emmariescurrena.bookesy.book_service.models.Author;
import com.emmariescurrena.bookesy.book_service.models.Genre;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class BookDetailsDto {
    
    @Id
    private String id;

    @Column(nullable = false)
    @NotEmpty(message = "The title is required")
    private String title;

    private String subtitle;

    @Column(nullable = false)
    private String description;

    private LocalDate publishDate;

    private Integer coverId;

    private List<Author> authors;

    private List<Genre> genres;

}
