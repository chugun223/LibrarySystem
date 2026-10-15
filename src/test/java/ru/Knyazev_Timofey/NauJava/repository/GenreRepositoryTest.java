package ru.Knyazev_Timofey.NauJava.repository;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.Knyazev_Timofey.NauJava.domain.Genre;


import java.util.UUID;

@SpringBootTest
@Transactional
class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @Test
    void testFindByNameIgnoreCase() {
        String name = "Жанр-" + UUID.randomUUID();
        genreRepository.save(new Genre(name, "Описание", 5));

        Genre found = genreRepository.findByNameIgnoreCase(name.toLowerCase()).orElseThrow();

        Assertions.assertEquals(name, found.getName());
    }
}
