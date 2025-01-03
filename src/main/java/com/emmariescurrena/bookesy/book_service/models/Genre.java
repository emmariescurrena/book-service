package com.emmariescurrena.bookesy.book_service.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Table("genres")
public class Genre implements Persistable<String> {
    
    @Id
    @NotNull(message = "Name is required")
    private String name;

    @Override
    public String getId() {
        return name;
    }


    @Override
    @JsonIgnore
    public boolean isNew() {
        return true;
    }

}
