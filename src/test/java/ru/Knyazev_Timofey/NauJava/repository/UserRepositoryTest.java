package ru.Knyazev_Timofey.NauJava.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.Knyazev_Timofey.NauJava.domain.User;
import ru.Knyazev_Timofey.NauJava.enums.Role;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Test
    void testFindByLastNameAndFirstName() {
        String firstName = "Имя-" + UUID.randomUUID();
        String lastName = "Фамилия-" + UUID.randomUUID();
        userRepository.save(new User(firstName, lastName, "email", "+7", "password", Role.USER, LocalDate.now()));

        List<User> found = userRepository.findByLastNameAndFirstName(lastName, firstName);

        Assertions.assertEquals(1, found.size());
        Assertions.assertEquals(firstName, found.get(0).getFirstName());
    }

    @Test
    void testFindByRegistrationDateBetween() {
        LocalDate regDate = LocalDate.of(2024, 6, 15);
        String email = UUID.randomUUID() + "@test";
        userRepository.save(new User("Имя", "Фамилия", email, "+7", "password", Role.USER, regDate));

        List<User> found = userRepository.findByRegistrationDateBetween(LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 30));

        Assertions.assertFalse(found.isEmpty());
        Assertions.assertTrue(found.stream().anyMatch(u -> u.getEmail().equals(email)));
    }
}