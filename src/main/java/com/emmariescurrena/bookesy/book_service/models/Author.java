package com.emmariescurrena.bookesy.book_service.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Table("authors")
public class Author implements Persistable<String> {
    
    @Id
    private String id;

    @Column("name")
    private String name;

    @Override
    @JsonIgnore
    public boolean isNew() {
        return true;
    }

}
