package ru.Knyazev_Timofey.NauJava.repository;


import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.Knyazev_Timofey.NauJava.domain.Genre;

import java.util.List;

@Repository
public interface GenreRepository extends CrudRepository<Genre, Long> {
    Optional<Genre> findByNameIgnoreCase(String name);
}
