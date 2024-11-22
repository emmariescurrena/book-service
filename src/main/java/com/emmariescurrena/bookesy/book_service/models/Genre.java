package com.emmariescurrena.bookesy.book_service.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "GENRES")
public class Genre {
    
    @Id
    private String name;

    @ManyToMany(mappedBy = "genres")
    @JsonIgnore
    private List<Book> books = new ArrayList<>();

}
