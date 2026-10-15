package ru.Knyazev_Timofey.NauJava.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.Knyazev_Timofey.NauJava.domain.Author;
import ru.Knyazev_Timofey.NauJava.domain.Book;
import ru.Knyazev_Timofey.NauJava.domain.Genre;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@Transactional
class BookRepositoryTest {

    @Autowired private BookRepository bookRepository;
    @Autowired private AuthorRepository authorRepository;
    @Autowired private GenreRepository genreRepository;

    @Test
    void testFindByAuthorLastName() {
        String lastName = "Фамилия-" + UUID.randomUUID();
        Author author = authorRepository.save(new Author("Имя", lastName, "Биография", LocalDate.of(1900, 1, 1)));
        Genre genre = genreRepository.save(new Genre("Жанр-" + UUID.randomUUID(), "Описание", 10));

        bookRepository.save(new Book("Книга", 2000, author, genre));

        List<Book> found = bookRepository.findByAuthorLastName(lastName);

        Assertions.assertEquals(1, found.size());
        Assertions.assertEquals("Книга", found.get(0).getTitle());
    }

    @Test
    void testFindByGenreName() {
        String genreName = "Жанр-" + UUID.randomUUID();
        Author author = authorRepository.save(new Author("Имя", "Фамилия-" + UUID.randomUUID(), "Биография", LocalDate.of(1900, 1, 1)));
        Genre genre = genreRepository.save(new Genre(genreName, "Описание", 9));

        bookRepository.save(new Book("Книга", 2000, author, genre));

        List<Book> found = bookRepository.findByGenreName(genreName);

        Assertions.assertEquals(1, found.size());
        Assertions.assertEquals("Книга", found.get(0).getTitle());
    }

    @Test
    void testFindByPublicationYearBetweenAndIsAvailableTrue() {
        Author author = authorRepository.save(new Author("Имя", "Фамилия-" + UUID.randomUUID(), "Биография", LocalDate.of(1900, 1, 1)));
        Genre genre = genreRepository.save(new Genre("Жанр-" + UUID.randomUUID(), "Описание", 7));

        Book book = new Book("Книга", 2005, author, genre);
        book.setAvailable(true);
        bookRepository.save(book);

        List<Book> found = bookRepository.findByPublicationYearBetweenAndIsAvailableTrue(2000, 2010);

        Assertions.assertFalse(found.isEmpty());
        Assertions.assertTrue(found.stream().anyMatch(b -> b.getTitle().equals("Книга")));
    }

    @Test
    void testFindAllAvailableWithAuthorAndGenre() {
        Author author = authorRepository.save(new Author("Имя", "Фамилия-" + UUID.randomUUID(), "Биография", LocalDate.of(1900, 1, 1)));
        Genre genre = genreRepository.save(new Genre("Жанр-" + UUID.randomUUID(), "Описание", 8));

        Book book = new Book("Книга", 2000, author, genre);
        book.setAvailable(true);
        bookRepository.save(book);

        List<Book> found = bookRepository.findAllAvailableWithAuthorAndGenre();

        Assertions.assertFalse(found.isEmpty());
        found.forEach(b -> {
            Assertions.assertNotNull(b.getAuthor());
            Assertions.assertNotNull(b.getGenre());
        });
    }
}
