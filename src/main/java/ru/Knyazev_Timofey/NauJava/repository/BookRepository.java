package ru.Knyazev_Timofey.NauJava.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.Knyazev_Timofey.NauJava.domain.Book;

import java.util.List;

@Repository
@RepositoryRestResource(path = "books")
public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findByAuthorLastName(String lastName);

    List<Book> findByGenreName(String genreName);

    List<Book> findByPublicationYearBetweenAndIsAvailableTrue(Integer from, Integer to);

    @Query("SELECT b FROM Book b JOIN FETCH b.author JOIN FETCH b.genre WHERE b.isAvailable = true")
    List<Book> findAllAvailableWithAuthorAndGenre();

    @Query("SELECT b FROM Book b JOIN FETCH b.author JOIN FETCH b.genre")
    List<Book> findAllWithAuthorAndGenre();
}
