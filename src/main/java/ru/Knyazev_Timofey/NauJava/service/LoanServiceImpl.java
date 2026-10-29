package ru.Knyazev_Timofey.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.Knyazev_Timofey.NauJava.domain.Book;
import ru.Knyazev_Timofey.NauJava.domain.Loan;
import ru.Knyazev_Timofey.NauJava.domain.User;
import ru.Knyazev_Timofey.NauJava.enums.LoanStatus;
import ru.Knyazev_Timofey.NauJava.repository.BookRepository;
import ru.Knyazev_Timofey.NauJava.repository.LoanRepository;
import ru.Knyazev_Timofey.NauJava.repository.UserRepository;

import java.time.LocalDate;

@Service
public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Autowired
    public LoanServiceImpl(LoanRepository loanRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional
    public Long createLoan(Long userId, Long bookId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("пользователь не был найден"));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("книга не была найдена"));

        if (!book.isAvailable()) {
            throw new IllegalStateException("книга уже выдана");
        }

        book.setAvailable(false);
        bookRepository.save(book);

        LocalDate loanDate = LocalDate.now();
        LocalDate dueDate = loanDate.plusDays(14);

        Loan loan = new Loan(user, book, loanDate, dueDate);
        loan.setStatus(LoanStatus.ACTIVE);
        Loan saved = loanRepository.save(loan);
        return saved.getId();
    }
}
