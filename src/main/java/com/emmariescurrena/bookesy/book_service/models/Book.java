package com.emmariescurrena.bookesy.book_service.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name = "BOOKS")
public class Book {
    
    @Id
    private String id;

    @Column(nullable = false)
    @NotEmpty(message = "The title is required")
    private String title;

    private String subtitle;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDate publishedDate;

    private Integer coverId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "book_genres",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_name")
    )
    private List<Genre> genres = new ArrayList<>();

}
