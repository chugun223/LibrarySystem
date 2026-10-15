package ru.Knyazev_Timofey.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.Knyazev_Timofey.NauJava.domain.User;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByLastNameAndFirstName(String lastName, String firstName);

    List<User> findByRegistrationDateBetween(java.time.LocalDate from, java.time.LocalDate to);
}