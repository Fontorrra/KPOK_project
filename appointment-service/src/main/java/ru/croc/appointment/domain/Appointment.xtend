package ru.croc.appointment.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import ru.croc.ctp.jxfw.core.generator.meta.XFWObject;

import java.time.LocalDateTime;

/**
 * Запись.
 */
@XFWObject
@Table(name = "appointment_table")
public class Appointment {
    /**
     * Идентификатор.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appointment_table_seq")
    @SequenceGenerator(name = "appointment_table_seq", sequenceName = "appointment_table_seq", allocationSize = 1)
    private Long id;
    /**
     * Идентификатор врача.
     */
    @Column(nullable = false)
    private Long idDoctor;
    /**
     * Идентификатор пациента.
     */
    @Column(nullable = false)
    private Long idPatient;
    /**
     * Время.
     */
    @Column(nullable = false)
    private LocalDateTime dateTime;
    /**
     * Подтверждение.
     */
    @Column(nullable = false)
    private Boolean confirmation;
    /**
     * Комментарий.
     */
    @Column(nullable = false)
    private String comment;
}
