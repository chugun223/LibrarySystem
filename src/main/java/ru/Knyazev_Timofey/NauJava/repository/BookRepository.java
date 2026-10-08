package ru.Knyazev_Timofey.NauJava.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.Knyazev_Timofey.NauJava.domain.Book;

import java.util.List;

@Component
public class BookRepository implements BookCrudRepository{
    private final List<Book> bookContainer;

    @Autowired
    public BookRepository(List<Book> bookContainer) {
        this.bookContainer = bookContainer;
    }

    @Override
    public void create(Book book) {
        bookContainer.add(book);
    }

    @Override
    public Book read(Long id) {
        return bookContainer.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(Book book) {
        for (int i = 0; i < bookContainer.size(); i++) {
            if (bookContainer.get(i).getId().equals(book.getId())) {
                bookContainer.set(i, book);
                return;
            }
        }
    }

    @Override
    public void delete(Long id) {
        bookContainer.removeIf(b -> b.getId().equals(id));
    }

    public List<Book> findByHolderId(Long userId) {
        return bookContainer.stream()
                .filter(b -> userId.equals(b.getHolderId()))
                .toList();
    }

    public List<Book> findAll() {
        return bookContainer;
    }
}
