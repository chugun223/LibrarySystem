package ru.Knyazev_Timofey.NauJava.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.Knyazev_Timofey.NauJava.domain.User;
import ru.Knyazev_Timofey.NauJava.dto.RegistrationDto;
import ru.Knyazev_Timofey.NauJava.enums.Role;
import ru.Knyazev_Timofey.NauJava.repository.UserRepository;
import org.springframework.ui.Model;

import java.time.LocalDate;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationDto", new RegistrationDto());
        return "registration";
    }

    @PostMapping
    public String registerUser(@ModelAttribute RegistrationDto dto, Model model) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            model.addAttribute("message", "пользователь с таким email уже существует");
            return "registration";
        }

        try {
            User user = new User(
                    dto.getFirstName(),
                    dto.getLastName(),
                    dto.getEmail(),
                    dto.getPhoneNumber(),
                    passwordEncoder.encode(dto.getPassword()),
                    Role.USER,
                    LocalDate.now()
            );
            userRepository.save(user);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("message", "ошибка регистрации " + e.getMessage());
            return "registration";
        }
    }
}
