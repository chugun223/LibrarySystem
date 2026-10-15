package ru.Knyazev_Timofey.NauJava.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.Knyazev_Timofey.NauJava.domain.*;
import ru.Knyazev_Timofey.NauJava.repository.*;
import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
@Transactional
class LoanServiceTest {
    @Autowired private LoanService loanService;
    @Autowired private LoanRepository loanRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private BookRepository bookRepository;
    @Autowired private AuthorRepository authorRepository;
    @Autowired private GenreRepository genreRepository;

    @Test
    void testCreateLoanSuccess() {
        User user = userRepository.save(new User("Имя", "Фамилия-" + UUID.randomUUID(), UUID.randomUUID() + "@test.ru", "+7", LocalDate.now()));
        Author author = authorRepository.save(new Author("Имя", "Фамилия-" + UUID.randomUUID(), "Биография", LocalDate.of(1900, 1, 1)));
        Genre genre = genreRepository.save(new Genre("Жанр-" + UUID.randomUUID(), "Описание", 10));
        Book book = new Book("Книга", 2000, author, genre);
        book.setAvailable(true);
        book = bookRepository.save(book);

        Long loanId = loanService.createLoan(user.getId(), book.getId());

        Assertions.assertNotNull(loanId);

        Loan loan = loanRepository.findById(loanId).orElseThrow();
        Assertions.assertEquals(user.getId(), loan.getUser().getId());
        Assertions.assertEquals(book.getId(), loan.getBook().getId());

        Book updatedBook = bookRepository.findById(book.getId()).orElseThrow();
        Assertions.assertFalse(updatedBook.isAvailable());
    }

    @Test
    void testCreateLoanOnAlreadyTakenBook() {
        User user = userRepository.save(new User("Имя", "Фамилия-" + UUID.randomUUID(), UUID.randomUUID() + "@test.ru", "+7", LocalDate.now()));
        Author author = authorRepository.save(new Author("Имя", "Фамилия-" + UUID.randomUUID(), "Биография", LocalDate.of(1900, 1, 1)));
        Genre genre = genreRepository.save(new Genre("Жанр-" + UUID.randomUUID(), "Описание", 9));
        Book book = new Book("Книга", 2000, author, genre);
        book.setAvailable(false);
        book = bookRepository.save(book);

        long loansBefore = loanRepository.count();
        final Long bookId = book.getId();
        final Long userId = user.getId();

        Assertions.assertThrows(IllegalStateException.class, () -> loanService.createLoan(userId, bookId));

        Assertions.assertEquals(loansBefore, loanRepository.count());

        Book unchanged = bookRepository.findById(bookId).orElseThrow();
        Assertions.assertFalse(unchanged.isAvailable());
    }

    @Test
    void testCreateLoanUserNotFound() {
        Author author = authorRepository.save(new Author("Имя", "Фамилия-" + UUID.randomUUID(), "Биография", LocalDate.of(1900, 1, 1)));
        Genre genre = genreRepository.save(new Genre("Жанр-" + UUID.randomUUID(), "Описание", 1));
        Book book = new Book("Книга", 2000, author, genre);
        book.setAvailable(true);
        Book savedBook = bookRepository.save(book);

        long loansBefore = loanRepository.count();
        long nonExistentUserId = -1;
        Assertions.assertThrows(IllegalArgumentException.class, () -> loanService.createLoan(nonExistentUserId, savedBook.getId()));

        Assertions.assertEquals(loansBefore, loanRepository.count());
    }
}
