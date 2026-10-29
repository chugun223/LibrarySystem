package ru.Knyazev_Timofey.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.Knyazev_Timofey.NauJava.domain.Loan;

import java.util.List;

@Repository
@RepositoryRestResource(path = "loans")
public interface LoanRepository extends CrudRepository<Loan, Long> {
}
