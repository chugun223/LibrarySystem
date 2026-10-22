package ru.Knyazev_Timofey.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.Knyazev_Timofey.NauJava.domain.Author;

import java.time.LocalDate;
import java.util.List;

@Repository
@RepositoryRestResource(path = "authors")
public interface AuthorRepository extends CrudRepository<Author, Long> {
}