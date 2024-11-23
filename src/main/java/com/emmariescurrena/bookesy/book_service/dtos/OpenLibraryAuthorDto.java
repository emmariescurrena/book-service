package com.emmariescurrena.bookesy.book_service.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OpenLibraryAuthorDto extends ExternalAuthorApiDto {

    private String id;

    private String name;

    @JsonIgnore
    public String getId() {
        return id;
    }

    @JsonProperty("key")
    public void setId(String key) {
        this.id = key.substring("/authors/".length());
    }


}

