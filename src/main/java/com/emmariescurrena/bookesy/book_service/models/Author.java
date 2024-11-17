package com.emmariescurrena.bookesy.book_service.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "AUTHORS")
public class Author {
    
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    private String photoUrl;

}
