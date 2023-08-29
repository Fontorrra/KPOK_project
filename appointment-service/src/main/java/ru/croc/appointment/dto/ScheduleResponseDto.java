package ru.croc.appointment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleResponseDto {
    /**
     * Id доктора
     */
    Long doctorId;
    /**
     * Список свободный приемов
     */
    List<LocalDateTime> schedule;

}
