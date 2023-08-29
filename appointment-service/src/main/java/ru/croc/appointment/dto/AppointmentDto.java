package ru.croc.appointment.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AppointmentDto {
    /**
     * Id записи
     */
    Long appointmentId;

    /**
     * Начало приема.
     */
    LocalDateTime start;

    /**
     * Конец приема.
     */
    LocalDateTime end;

    /**
     * Комментарий к записи
     */
    String comment;

}
