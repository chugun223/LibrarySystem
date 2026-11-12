package ru.Knyazev_Timofey.NauJava.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.Knyazev_Timofey.NauJava.domain.Report;
import ru.Knyazev_Timofey.NauJava.enums.ReportStatus;
import ru.Knyazev_Timofey.NauJava.service.ReportService;
import org.springframework.context.annotation.Import;
import ru.Knyazev_Timofey.NauJava.config.TestSecurityConfig;
import java.util.concurrent.CompletableFuture;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestSecurityConfig.class)
class ReportControllerTest {
    @LocalServerPort
    private int port;

    @MockitoBean
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void createReportSuccess() {
        when(reportService.createReport()).thenReturn(1L);

        when(reportService.generateReportAsync(1L))
                .thenReturn(CompletableFuture.completedFuture(null));

        given()
                .when()
                .post("/reports")
                .then()
                .statusCode(202)
                .contentType(startsWith("application/json"))
                .body("reportId", equalTo(1));

        verify(reportService).createReport();
        verify(reportService).generateReportAsync(1L);
    }

    @Test
    void getReportStatusCompleted() {
        Report report = new Report(ReportStatus.COMPLETED);
        report.setId(1L);
        report.setContent("<html><body>Отчёт готов</body></html>");

        when(reportService.getReport(1L)).thenReturn(report);

        given()
                .when()
                .get("/reports/1")
                .then()
                .statusCode(200)
                .contentType(startsWith("text/html"))
                .body(equalTo("<html><body>Отчёт готов</body></html>"));

        verify(reportService).getReport(1L);
    }

    @Test
    void getReportStatusCreated() {
        Report report = new Report(ReportStatus.CREATED);
        report.setId(2L);

        when(reportService.getReport(2L)).thenReturn(report);

        given()
                .when()
                .get("/reports/2")
                .then()
                .statusCode(202)
                .contentType(startsWith("text/html"))
                .body(equalTo(
                        "<html><body><h1>Отчёт ещё формируется</h1></body></html>"
                ));

        verify(reportService).getReport(2L);
    }

    @Test
    void getReportStatusError() {
        Report report = new Report(ReportStatus.ERROR);
        report.setId(3L);
        report.setContent("Ошибка базы данных");

        when(reportService.getReport(3L)).thenReturn(report);

        given()
                .when()
                .get("/reports/3")
                .then()
                .statusCode(500)
                .contentType(startsWith("text/html"))
                .body(equalTo(
                        "<html><body><h1>Ошибка при формировании отчёта</h1>"
                                + "<p>Ошибка базы данных</p></body></html>"
                ));

        verify(reportService).getReport(3L);
    }

    @Test
    void getReportNotExist() {
        when(reportService.getReport(63634676543L)).thenReturn(null);

        given()
                .when()
                .get("/reports/63634676543")
                .then()
                .statusCode(404)
                .contentType(startsWith("text/html"))
                .body(equalTo(
                        "<html><body><h1>Отчёт не найден</h1></body></html>"
                ));

        verify(reportService).getReport(63634676543L);
    }
}

