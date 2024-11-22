package com.emmariescurrena.bookesy.book_service.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.emmariescurrena.bookesy.book_service.dtos.OpenLibraryAuthorDto;
import com.emmariescurrena.bookesy.book_service.dtos.OpenLibraryBookDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Flux<String> searchBooksIds(String bookName, Integer page) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search.json")
                        .queryParam("q", bookName)
                        .queryParam("page", page)
                        .queryParam("limit", 10)
                        .queryParam("fields", "key")
                        .build())
                .retrieve()
                .bodyToMono(OpenLibraryResponse.class)
                .flatMapMany(response -> {
                    if (response == null || response.docs == null) {
                        return Flux.empty();
                    }
                    return Flux.fromIterable(
                        response.docs.stream()
                            .map(doc -> doc.key().substring("/works/".length()))
                            .collect(Collectors.toList())
                    );
                });
    }

    @Override
    public Mono<OpenLibraryBookDto> getBook(String bookId) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(String.format("/works/%s.json", bookId))
                        .build())
                .retrieve()
                .bodyToMono(OpenLibraryBookDto.class);
    }

    @Override
    public Mono<OpenLibraryAuthorDto> getAuthor(String authorId) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(String.format("/authors/%s.json", authorId))
                        .build())
                .retrieve()
                .bodyToMono(OpenLibraryAuthorDto.class);
    }

}
