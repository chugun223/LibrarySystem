package ru.Knyazev_Timofey.NauJava.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.Knyazev_Timofey.NauJava.domain.Report;
import ru.Knyazev_Timofey.NauJava.service.ReportService;

import java.util.Map;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> createReport() {
        Long reportId = reportService.createReport();
        reportService.generateReportAsync(reportId);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(Map.of("reportId", reportId));
    }

    @GetMapping(value = "/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> getReport(@PathVariable Long id) {
        Report report = reportService.getReport(id);

        if (report == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.TEXT_HTML)
                    .body("<html><body><h1>Отчёт не найден</h1></body></html>");
        }

        return switch (report.getStatus()) {
            case COMPLETED -> ResponseEntity.ok()
                    .contentType(MediaType.TEXT_HTML)
                    .body(report.getContent());

            case CREATED -> ResponseEntity.status(HttpStatus.ACCEPTED)
                    .contentType(MediaType.TEXT_HTML)
                    .body("<html><body><h1>Отчёт ещё формируется</h1></body></html>");

            case ERROR -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.TEXT_HTML)
                    .body("<html><body><h1>Ошибка при формировании отчёта</h1><p>"
                            + report.getContent() + "</p></body></html>");
        };
    }
}
