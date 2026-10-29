package ru.Knyazev_Timofey.NauJava.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.Knyazev_Timofey.NauJava.domain.Book;
import ru.Knyazev_Timofey.NauJava.repository.BookRepository;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/author")
    public List<Book> findByAuthorLastName(@RequestParam String lastName) {
        return bookRepository.findByAuthorLastName(lastName);
    }

    @GetMapping("/genre")
    public List<Book> findByGenreName(@RequestParam String genreName) {
        return bookRepository.findByGenreName(genreName);
    }

    @GetMapping("/year")
    public List<Book> findByPublicationYearBetween(
            @RequestParam Integer from,
            @RequestParam Integer to) {
        return bookRepository.findByPublicationYearBetweenAndIsAvailableTrue(from, to);
    }

    @GetMapping("/available")
    public List<Book> findAllAvailable() {
        return bookRepository.findAllAvailableWithAuthorAndGenre();
    }
}
