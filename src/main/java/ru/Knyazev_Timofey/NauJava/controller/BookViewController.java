package ru.Knyazev_Timofey.NauJava.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.Knyazev_Timofey.NauJava.domain.Book;
import ru.Knyazev_Timofey.NauJava.repository.BookRepository;

import java.util.List;

@Controller
@RequestMapping("/view/books")
public class BookViewController {

    private final BookRepository bookRepository;

    public BookViewController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/list")
    public String bookListView(Model model) {
        List<Book> books = bookRepository.findAllWithAuthorAndGenre();
        model.addAttribute("books", books);
        return "bookList";
    }
}
