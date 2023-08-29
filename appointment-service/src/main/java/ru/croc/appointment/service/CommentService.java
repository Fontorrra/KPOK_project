package ru.croc.appointment.service;

import org.springframework.stereotype.Service;
import ru.croc.appointment.domain.Appointment;
import ru.croc.appointment.dto.AppointmentForCommentDto;
import ru.croc.appointment.repository.DataForPlanRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentService {

    private final DataForPlanRepository repository;

    public CommentService(DataForPlanRepository repository) {
        this.repository = repository;
    }

    public Optional<AppointmentForCommentDto> addCommentToAppointment(long appointmentId, String comment) {
        Appointment appointment = repository.findByIdAndDateTimeAfter(appointmentId, LocalDateTime.now()).orElse(null);
        if (appointment == null) {
            return Optional.empty();
        }
        appointment.setComment(comment);
        AppointmentForCommentDto dto = AppointmentForCommentDto.builder()
                .patientId(appointment.getIdPatient())
                .comment(appointment.getComment())
                .start(appointment.getDateTime())
                .build();
        repository.save(appointment);
        return Optional.of(dto);
    }

}
