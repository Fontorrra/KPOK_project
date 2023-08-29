package ru.croc.appointment.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentRequestDto {
    /**
     * Идентификатор.
     */
    Long id;
    /**
     * Идентификатор врача.
     */

    Long doctorId;
    /**
     * Идентификатор пациента.
     */
    Long patientId;
    /**
     * Время.
     */
    LocalDateTime dateTime;

}
