package ru.croc.appointment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleRequestDto {
    /**
     * Id доктора
     */
    Long doctorId;
    /**
     * Дата расписания
     */
    LocalDate date;
}
