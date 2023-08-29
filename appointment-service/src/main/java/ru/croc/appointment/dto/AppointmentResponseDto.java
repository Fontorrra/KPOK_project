package ru.croc.appointment.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentResponseDto {
    /**
     * Id врача.
     */

    Long idDoctor;
    /**
     * Id пациента.
     */
    Long idPatient;
    /**
     * Время.
     */
    LocalDateTime dateTime;
    /**
     * записался или нет
     */
    boolean confirmation;
}
