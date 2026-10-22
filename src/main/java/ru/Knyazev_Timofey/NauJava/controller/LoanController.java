package ru.Knyazev_Timofey.NauJava.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.Knyazev_Timofey.NauJava.repository.AuthorRepository;
import ru.Knyazev_Timofey.NauJava.repository.LoanRepository;

@RestController
@RequestMapping("/loans")
public class LoanController {
    private final LoanRepository loanRepository;

    public LoanController(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }
}