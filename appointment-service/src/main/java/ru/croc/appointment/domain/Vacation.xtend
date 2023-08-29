package ru.croc.appointment.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import ru.croc.ctp.jxfw.core.generator.meta.XFWObject;

import java.time.LocalDateTime;


@XFWObject
@Table(name = "vacation_table")
public class Vacation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vacation_table_seq")
    @SequenceGenerator(name = "vacation_table_seq", sequenceName = "vacation_table_seq", allocationSize = 1)
    private Long id;
    /**
     * Идентификатор врача.
     */
    @Column(nullable = false)
    private Long idDoctor;

    /**
     * Время начала отпуска.
     */
    @Column(nullable = false)
    private LocalDateTime beginDate;

    /**
     * Время начала конец отпуска.
     */
    @Column(nullable = false)
    private LocalDateTime endDate;
}




