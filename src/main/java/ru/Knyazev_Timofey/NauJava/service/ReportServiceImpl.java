package ru.Knyazev_Timofey.NauJava.service;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ru.Knyazev_Timofey.NauJava.domain.Book;
import ru.Knyazev_Timofey.NauJava.domain.Report;
import ru.Knyazev_Timofey.NauJava.enums.ReportStatus;
import ru.Knyazev_Timofey.NauJava.repository.BookRepository;
import ru.Knyazev_Timofey.NauJava.repository.ReportRepository;
import ru.Knyazev_Timofey.NauJava.repository.UserRepository;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final TemplateEngine templateEngine;

    public ReportServiceImpl(ReportRepository reportRepository,
                             UserRepository userRepository,
                             BookRepository bookRepository,
                             TemplateEngine templateEngine) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.templateEngine = templateEngine;
    }

    @Override
    public Long createReport() {
        Report report = new Report(ReportStatus.CREATED);
        reportRepository.save(report);
        return report.getId();
    }

    @Override
    public Report getReport(Long id) {
        return reportRepository.findById(id).orElse(null);
    }

    @Override
    public CompletableFuture<Void> generateReportAsync(Long reportId) {
        return CompletableFuture.runAsync(() -> {
            Report report = reportRepository.findById(reportId).orElse(null);
            if (report == null) {
                return;
            }

            try {
                long totalStart = System.currentTimeMillis();

                AtomicLong userCount = new AtomicLong(0);
                AtomicLong userCountTime = new AtomicLong(0);
                Thread userThread = new Thread(() -> {
                    long start = System.currentTimeMillis();
                    userCount.set(userRepository.count());
                    userCountTime.set(System.currentTimeMillis() - start);
                });
                userThread.start();

                AtomicReference<List<Book>> booksRef = new AtomicReference<>();
                AtomicLong booksTime = new AtomicLong(0);
                Thread booksThread = new Thread(() -> {
                    long start = System.currentTimeMillis();
                    booksRef.set(bookRepository.findAllWithAuthorAndGenre());
                    booksTime.set(System.currentTimeMillis() - start);
                });
                booksThread.start();

                userThread.join();
                booksThread.join();
                long totalTime = System.currentTimeMillis() - totalStart;

                Context context = new Context();
                context.setVariable("userCount", userCount.get());
                context.setVariable("userCountTime", userCountTime);
                context.setVariable("books", booksRef.get());
                context.setVariable("booksTime", booksTime);
                context.setVariable("totalTime", totalTime);

                String html = templateEngine.process("report", context);

                report.setContent(html);
                report.setStatus(ReportStatus.COMPLETED);
                reportRepository.save(report);

            } catch (Exception e) {
                report.setStatus(ReportStatus.ERROR);
                report.setContent("Ошибка при формировании отчёта: " + e.getMessage());
                reportRepository.save(report);
            }
        });
    }
}