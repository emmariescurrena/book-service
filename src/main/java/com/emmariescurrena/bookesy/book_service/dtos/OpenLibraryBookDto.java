package com.emmariescurrena.bookesy.book_service.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OpenLibraryBookDto extends ExternalBookApiDto {

    private String id;

    private String title;

    private String subtitle;

    private String description;

    private Integer coverId;

    private List<String> genres;

    private List<String> authorsIds;

    @JsonIgnore
    public String getId() {
        return id;
    }

    @JsonProperty("key")
    public void setId(String key) {
        this.id = key.substring("/works/".length());
    }

    @Override
    public String getTitle() {
        return subtitle != null && !subtitle.isEmpty() ? title + ": " + subtitle : title;
    }

    @JsonProperty("description")
    public void setDescription(JsonNode descriptionNode) {
        if (descriptionNode == null) {
            this.description = null;
        } else if (descriptionNode.isTextual()) {
            this.description = descriptionNode.asText();
        } else {
            this.description = descriptionNode.get("value").asText();
        }
    }

    @JsonIgnore
    public Integer getCoverId() {
        return coverId;
    }

    @JsonProperty("covers")
    public void setCoverId(List<Integer> covers) {
        if (covers == null || covers.isEmpty()) {
            this.coverId = null;
        } else {
            this.coverId = covers.get(0);
        }
    }

    @JsonIgnore
    public List<String> getGenres() {
        return genres;
    }

    @JsonProperty("subjects")
    public void setGenres(List<String> genres) {
        this.genres = genres != null ? genres : new ArrayList<>();
    }

    @JsonIgnore
    public List<String> getAuthorsIds() {
        return authorsIds;
    }

    @JsonProperty("authors")
    public void setAuthorsIds(List<Map<String, Object>> authors) {
        this.authorsIds = new ArrayList<>();
        if (authors == null) {
            return;
        }
        for (Map<String, Object> authorEntry : authors) {
            if (!authorEntry.containsKey("author")) {
                continue;
            }
            Object authorObj = authorEntry.get("author");
            if (authorObj instanceof Map == false) {
                continue;
            }
            @SuppressWarnings("unchecked")
            Map<String, String> author = (Map<String, String>) authorObj;
            if (author == null || !author.containsKey("key")) {
                continue;
            }
            String authorKey = author.get("key").substring("/authors/".length());
            this.authorsIds.add(authorKey);
        }
    }

}
