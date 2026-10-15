package ru.Knyazev_Timofey.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.Knyazev_Timofey.NauJava.domain.Loan;

import java.util.List;

@Repository
public interface LoanRepository extends CrudRepository<Loan, Long> {
}
