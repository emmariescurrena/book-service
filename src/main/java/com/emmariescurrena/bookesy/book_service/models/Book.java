package com.emmariescurrena.bookesy.book_service.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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

    private String description;

    private LocalDate publishDate;

    private String coverUrl;

}
