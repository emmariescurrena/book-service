package com.emmariescurrena.bookesy.book_service.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Table("books")
public class Book implements Persistable<String>{
    
    @Id
    private String id;

    @Column("title")
    @NotEmpty(message = "The title is required")
    private String title;

    @Column("description")
    private String description;

    @Column("published_year")
    private Integer publishedYear;

    @Column("cover_id")
    private Integer coverId;

    @Column("average_rating")
    private Double averageRating;

    @Column("rating_count")
    private Integer ratingCount;

    @Override
    public boolean isNew() {
        return true;
    }

}
