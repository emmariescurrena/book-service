package com.emmariescurrena.bookesy.book_service.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OpenLibraryAuthorDto extends ExternalAuthorApiDto {

    @JsonProperty("key")
    private String id;

    private String name;

    @JsonProperty("photos")
    private List<Integer> photosIds;

    @Override
    public Integer getPhotoId() {
        return photosIds.get(0);
    }

}
