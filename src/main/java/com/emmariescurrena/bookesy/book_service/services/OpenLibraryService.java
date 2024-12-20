package com.emmariescurrena.bookesy.book_service.services;

import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.emmariescurrena.bookesy.book_service.dtos.BookSearchResultDto;
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

    private record BookData(String key, Year first_publish_year) {};

    private record OpenLibraryResponseWithDocs(List<BookData> docs) {};
    private record OpenLibraryResponseWithWorks(List<BookData> works) {};

    @Override
    public Flux<BookSearchResultDto> searchBooksIds(
        String bookName,
        String authorName,
        Integer page
    ) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search.json")
                        .queryParam("q", bookName)
                        .queryParam("author", authorName)
                        .queryParam("page", page)
                        .queryParam("limit", 10)
                        .queryParam("fields", "key,first_publish_year")
                        .build())
                .retrieve()
                .bodyToMono(OpenLibraryResponseWithDocs.class)
                .flatMapMany(response -> {
                    if (response == null || response.docs == null) {
                        return Flux.empty();
                    }
                    return Flux.fromIterable(response.docs.stream()
                        .map(doc -> new BookSearchResultDto(
                                doc.key().substring("/works/".length()),
                                doc.first_publish_year()))
                        .collect(Collectors.toList()));
                });
    }

    @Override
    public Flux<BookSearchResultDto> searchBooksBySubject(String genre, Integer page) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(String.format("/subjects/%s.json", genre))
                        .queryParam("offset", page * 10 - 10)
                        .queryParam("limit", 10)
                        .build())
                .retrieve()
                .bodyToMono(OpenLibraryResponseWithWorks.class)
                .flatMapMany(response -> {
                    if (response == null || response.works == null) {
                        return Flux.empty();
                    }
                    return Flux.fromIterable(response.works.stream()
                        .map(doc -> new BookSearchResultDto(
                                doc.key().substring("/works/".length()),
                                doc.first_publish_year()))
                        .collect(Collectors.toList()));
                });
    }

    @Override
    public Mono<OpenLibraryBookDto> getBook(String bookId) {
        
        Mono<OpenLibraryBookDto> result = webClient
                                    .get()
                                    .uri(uriBuilder -> uriBuilder
                                    .path(String.format("/works/%s.json", bookId))
                                    .build())
                                    .retrieve()
                                    .bodyToMono(OpenLibraryBookDto.class);
        
        return result;
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
