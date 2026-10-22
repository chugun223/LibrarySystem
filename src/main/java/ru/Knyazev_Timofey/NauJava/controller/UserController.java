package ru.Knyazev_Timofey.NauJava.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.Knyazev_Timofey.NauJava.domain.User;
import ru.Knyazev_Timofey.NauJava.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/name")
    public List<User> findByLastNameAndFirstName(
            @RequestParam String lastName,
            @RequestParam String firstName) {
        return userRepository.findByLastNameAndFirstName(lastName, firstName);
    }

    @GetMapping("/registration")
    public List<User> findByRegistrationDateBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return userRepository.findByRegistrationDateBetween(from, to);
    }
}
