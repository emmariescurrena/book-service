package com.emmariescurrena.bookesy.book_service.dtos;

import java.util.List;

import com.emmariescurrena.bookesy.book_service.models.Author;
import com.emmariescurrena.bookesy.book_service.models.Genre;

import lombok.Data;

@Data
public class BookDetailsDto {
    
    private String id;
    private String title;
    private String description;
    private Integer publishedYear;
    private Integer coverId;
    private List<Author> authors;
    private List<Genre> genres;
    private Double averageRating;
    private Integer ratingCount;

}
