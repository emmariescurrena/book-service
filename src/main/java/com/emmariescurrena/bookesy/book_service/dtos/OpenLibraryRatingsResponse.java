package com.emmariescurrena.bookesy.book_service.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

public class OpenLibraryRatingsResponse{

    @JsonProperty("summary")
    private Summary summary;

    @Data
    public static class Summary {
        @JsonProperty("average")
        private Double average;

        @JsonProperty("count")
        private Integer count;
    }

    public Double getAverageRating() {
        return (summary != null && summary.getAverage() != null) ? summary.getAverage() : 0.0;
    }

    public Integer getRatingCount() {
        return (summary != null && summary.getCount() != null) ? summary.getCount() : 0;
    }

};
