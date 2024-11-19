package com.emmariescurrena.bookesy.book_service.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.emmariescurrena.bookesy.book_service.dtos.OpenLibraryAuthorDto;
import com.emmariescurrena.bookesy.book_service.dtos.OpenLibraryBookDto;

@Service
public class OpenLibraryService implements ExternalBookApiService {
    
    private String OPEN_LIBRARY_URL = "https://openlibrary.org";

    private final WebClient webClient;

    public OpenLibraryService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(OPEN_LIBRARY_URL).build();
    }

    private record Doc(String key) {};

    private record OpenLibraryResponse(List<Doc> docs) {};

    @Override
    public List<String> searchBooksIds(String query, Integer page) {
        OpenLibraryResponse response = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search.json")
                        .queryParam("q", query)
                        .queryParam("page", page)
                        .queryParam("limit", 20)
                        .queryParam("fields", "key")
                        .build())
                .retrieve()
                .bodyToMono(OpenLibraryResponse.class)
                .block();

        if (response == null || response.docs == null) {
            return List.of();
        }

        return response.docs().stream()
                .map(Doc::key)
                .collect(Collectors.toList());

    }

    @Override
    public OpenLibraryBookDto getBook(String bookId) {
        //TODO: Define getBook
        return new OpenLibraryBookDto();
    }

    @Override
    public OpenLibraryAuthorDto getAuthor(String authorId) {
        //TODO: Define getAuthor
        return new OpenLibraryAuthorDto();
    }

}
