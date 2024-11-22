package com.emmariescurrena.bookesy.book_service.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OpenLibraryAuthorDto extends ExternalAuthorApiDto {

    private String id;

    private String name;

    @JsonProperty("photoUrl")
    private String photoUrl;

    @JsonIgnore
    public String getId() {
        return id;
    }

    @JsonProperty("key")
    public void setId(String key) {
        this.id = key.substring("/authors/".length());
    }

    @JsonIgnore
    public String getPhotoUrl() {
        return photoUrl;
    }

    @JsonProperty("photos")
    public void setPhotoUrl(List<Integer> photos) {
        if (photos == null || photos.isEmpty()) {
            this.photoUrl = null;
        } else {
            this.photoUrl = String.format(
                "https://covers.openlibrary.org/a/id/%d-M.jpg/",
                photos.get(0)
            );
        }
    }
}

