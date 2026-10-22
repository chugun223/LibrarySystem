package ru.Knyazev_Timofey.NauJava.controller;

import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.Knyazev_Timofey.NauJava.domain.Genre;
import ru.Knyazev_Timofey.NauJava.repository.GenreRepository;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {

    private final GenreRepository genreRepository;

    public GenreController(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @GetMapping("/name")
    public Optional<Genre> findByNameIgnoreCase(@RequestParam String name) {
        return genreRepository.findByNameIgnoreCase(name);
    }
}