package com.emmariescurrena.bookesy.book_service.dtos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OpenLibraryBookDto extends ExternalBookApiDto {

    @JsonProperty("key")
    private String bookId;

    private String title;

    private String description;
    
    @JsonProperty("first_publish_date")
    private LocalDate publishDate;

    private List<Integer> covers;
    
    @JsonProperty("subjects")
    private List<String> genres;
    
    private List<Map<String, String>> authors;

    @Override
    public List<String> getAuthorsIds() {
        List<String> authorsIds = new ArrayList<>();
        for (Map<String, String> author: authors) {
            authorsIds.add(author.get("key"));
        }
        return authorsIds;
    }

    @Override
    public Integer getCoverId() {
        return covers.get(0);
    }

}
