package ru.Knyazev_Timofey.NauJava.repository;

import ru.Knyazev_Timofey.NauJava.domain.Book;

import java.util.List;

public interface BookCrudRepository
{
    void create(Book book);
    Book read(Long id);
    void update(Book book);
    void delete(Long id);
}