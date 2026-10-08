package ru.Knyazev_Timofey.NauJava.service;

import ru.Knyazev_Timofey.NauJava.domain.Book;

import java.util.List;

public interface BookService {
    void addBook(Long id, String title, String author);
    Book findBookById(Long id);
    void deleteBook(Long id);
    void updateBook(Long id, String title, String author);
    boolean takeBook(Long bookId, Long userId);
    boolean returnBook(Long bookId);
    List<Book> findBooksByUser(Long userId);
    List<Book> findAll();
}
