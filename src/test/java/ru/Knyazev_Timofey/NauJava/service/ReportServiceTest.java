package ru.Knyazev_Timofey.NauJava.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thymeleaf.TemplateEngine;
import ru.Knyazev_Timofey.NauJava.domain.Report;
import ru.Knyazev_Timofey.NauJava.enums.ReportStatus;
import ru.Knyazev_Timofey.NauJava.repository.BookRepository;
import ru.Knyazev_Timofey.NauJava.repository.ReportRepository;
import ru.Knyazev_Timofey.NauJava.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {
    @Mock
    private ReportRepository reportRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private TemplateEngine templateEngine;

    @InjectMocks
    private ReportServiceImpl reportService;

    public Long createReport() {
        Report report = new Report(ReportStatus.CREATED);
        reportRepository.save(report);
        return report.getId();
    }


    @Test
    void createReportSuccess() {
        when(reportRepository.save(any(Report.class)))
                .thenAnswer(invocation -> {
                    Report report = invocation.getArgument(0);
                    report.setId(1L);
                    return report;
                });
        Long reportId = reportService.createReport();
        assertNotNull(reportId);
        assertEquals(1L, reportId);
        verify(reportRepository).save(any(Report.class));
    }


    @Test
    void getReportSuccess() {
        Report report = new Report(ReportStatus.COMPLETED);
        report.setId(1L);
        report.setContent("<html>Отчёт</html>");

        when(reportRepository.findById(1L)).thenReturn(Optional.of(report));

        Report result = reportService.getReport(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(ReportStatus.COMPLETED, result.getStatus());
        assertEquals("<html>Отчёт</html>", result.getContent());
        verify(reportRepository).findById(1L);
    }

    @Test
    void getReportNull() {
        when(reportRepository.findById(999L)).thenReturn(Optional.empty());
        Report result = reportService.getReport(999L);
        assertNull(result);
        verify(reportRepository).findById(999L);
    }


    @Test
    void generateReportAsyncSuccess() {
        Report report = new Report(ReportStatus.CREATED);
        report.setId(1L);
        when(reportRepository.findById(1L)).thenReturn(Optional.of(report));
        when(userRepository.count()).thenReturn(10L);
        when(bookRepository.findAllWithAuthorAndGenre()).thenReturn(List.of());
        when(templateEngine.process(eq("report"), any(org.thymeleaf.context.Context.class))).thenReturn("<html>Отчёт сформирован</html>");

        reportService.generateReportAsync(1L).join();
        assertEquals(ReportStatus.COMPLETED, report.getStatus());

        assertEquals("<html>Отчёт сформирован</html>", report.getContent());

        verify(reportRepository).findById(1L);
        verify(userRepository).count();
        verify(bookRepository).findAllWithAuthorAndGenre();
        verify(templateEngine).process(eq("report"), any(org.thymeleaf.context.Context.class));
        verify(reportRepository).save(report);
    }


    @Test
    void generateReportAsyncError() {

        Report report = new Report(ReportStatus.CREATED);
        report.setId(1L);

        when(reportRepository.findById(1L)).thenReturn(Optional.of(report));
        when(userRepository.count()).thenReturn(10L);
        when(bookRepository.findAllWithAuthorAndGenre()).thenReturn(List.of());
        when(templateEngine.process(eq("report"), any(org.thymeleaf.context.Context.class))).thenThrow(new RuntimeException("Ошибка шаблона"));

        reportService.generateReportAsync(1L).join();

        assertEquals(ReportStatus.ERROR, report.getStatus());
        assertEquals("Ошибка при формировании отчёта: Ошибка шаблона", report.getContent());

        verify(reportRepository).save(report);
    }


    @Test
    void generateReportAsyncNotExists() {
        when(reportRepository.findById(999L)).thenReturn(Optional.empty());

        reportService.generateReportAsync(999L).join();

        verify(reportRepository).findById(999L);
        verifyNoInteractions(userRepository, bookRepository, templateEngine);
        verify(reportRepository, never()).save(any(Report.class));
    }
}
