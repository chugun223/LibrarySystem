package ru.Knyazev_Timofey.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.Knyazev_Timofey.NauJava.domain.Book;
import ru.Knyazev_Timofey.NauJava.domain.User;
import ru.Knyazev_Timofey.NauJava.repository.BookRepository;
import ru.Knyazev_Timofey.NauJava.repository.UserRepository;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addBook(Long id, String title, String author) {
        bookRepository.create(new Book(id, title, author));
    }

    @Override
    public Book findBookById(Long id) {
        return bookRepository.read(id);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.delete(id);
    }

    @Override
    public void updateBook(Long id, String title, String author) {
        Book book = bookRepository.read(id);
        if (book != null) {
            book.setTitle(title);
            book.setAuthor(author);
            bookRepository.update(book);
        }
    }

    @Override
    public boolean takeBook(Long bookId, Long userId) {
        Book book = bookRepository.read(bookId);
        User user = userRepository.read(userId);

        if (book == null || user == null) return false;
        if (book.getIsAvailable() == false) return false;

        book.setIsAvailable(false);
        book.setHolderId(userId);
        bookRepository.update(book);
        return true;
    }

    @Override
    public boolean returnBook(Long bookId) {
        Book book = bookRepository.read(bookId);
        if (book == null || book.getHolderId() == null) return false;

        book.setIsAvailable(true);
        book.setHolderId(null);
        bookRepository.update(book);
        return true;
    }

    @Override
    public List<Book> findBooksByUser(Long userId) {
        return bookRepository.findByHolderId(userId);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }
}
