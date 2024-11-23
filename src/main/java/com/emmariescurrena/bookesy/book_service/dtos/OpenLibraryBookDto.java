package com.emmariescurrena.bookesy.book_service.dtos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

    @JsonProperty("id")
    private String id;

    private String title;

    private String subtitle;

    private String description;

    @JsonProperty("publish_date")
    private LocalDate publishDate;

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
    public LocalDate getPublishDate() {
        return publishDate;
    }


    @JsonProperty("first_publish_date")
    public void setPublishDate(String publishDateString) {
        if (publishDateString == null) {
            this.publishDate = null;
            return;
        }

        // List of possible date formats
        List<DateTimeFormatter> formatters = List.of(
            DateTimeFormatter.ofPattern("MMMM d, yyyy"),  // e.g., "June 15, 1974"
            DateTimeFormatter.ofPattern("MMMM yyyy"),   // e.g., "June 1974"
            DateTimeFormatter.ofPattern("yyyy") // e.g., "1974"
        );

        for (DateTimeFormatter formatter : formatters) {
            try {
                this.publishDate = LocalDate.parse(publishDateString, formatter);
                return;
            } catch (DateTimeParseException ignored) {
                // Try the next pattern
            }
        }

        this.publishDate = null;
        System.err.println("Unable to parse publish date: " + publishDateString);
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
