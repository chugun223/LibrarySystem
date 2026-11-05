package ru.Knyazev_Timofey.NauJava.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.Knyazev_Timofey.NauJava.enums.ReportStatus;

@Entity
@Table(name = "reports")
@Getter
@Setter
@NoArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private ReportStatus status;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    public Report(ReportStatus status) {
        this.status = status;
    }
}
