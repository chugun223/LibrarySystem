package ru.Knyazev_Timofey.NauJava.service;

import ru.Knyazev_Timofey.NauJava.domain.Report;
import java.util.concurrent.CompletableFuture;

public interface ReportService {
    Long createReport();
    Report getReport(Long id);
    CompletableFuture<Void> generateReportAsync(Long reportId);
}
