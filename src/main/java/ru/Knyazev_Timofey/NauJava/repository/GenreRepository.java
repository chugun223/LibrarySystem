package ru.Knyazev_Timofey.NauJava.repository;


import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.Knyazev_Timofey.NauJava.domain.Genre;

@Repository
@RepositoryRestResource(path = "genres")
public interface GenreRepository extends CrudRepository<Genre, Long> {
    Optional<Genre> findByNameIgnoreCase(String name);
}
