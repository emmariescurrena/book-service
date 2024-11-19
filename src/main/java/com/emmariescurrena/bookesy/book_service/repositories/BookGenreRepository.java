package com.emmariescurrena.bookesy.book_service.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emmariescurrena.bookesy.book_service.models.BookGenre;

@Repository
public interface BookGenreRepository extends JpaRepository<BookGenre, Long> {
    List<BookGenre> findByGenre_Id(Long genreId);
    List<BookGenre> findByBook_Id(String bookId);
}
