package ru.Knyazev_Timofey.NauJava.repository;

import ru.Knyazev_Timofey.NauJava.domain.Book;
import java.util.List;

public interface BookRepositoryCustom {
    List<Book> findByAuthorLastName(String lastName);

    List<Book> findByGenreName(String genreName);

    List<Book> findByPublicationYearBetweenAndIsAvailableTrue(Integer from, Integer to);

    List<Book> findAllAvailableWithAuthorAndGenre();
}