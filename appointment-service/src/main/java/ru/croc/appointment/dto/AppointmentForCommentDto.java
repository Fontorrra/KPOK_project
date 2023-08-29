package ru.croc.appointment.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AppointmentForCommentDto {

    Long patientId;

    LocalDateTime start;

    String comment;

}
