package ru.Knyazev_Timofey.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.Knyazev_Timofey.NauJava.domain.Report;

@Repository
public interface ReportRepository extends CrudRepository<Report, Long> {
}
