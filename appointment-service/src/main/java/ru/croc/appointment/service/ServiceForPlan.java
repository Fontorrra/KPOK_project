package ru.croc.appointment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.croc.appointment.domain.Appointment;
import ru.croc.appointment.dto.AppointmentDto;
import ru.croc.appointment.repository.DataForPlanRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceForPlan {
    private final DataForPlanRepository repository;

    @Value("${const.duration-per-patient}")
    private Integer duration;

    @Autowired
    public ServiceForPlan(DataForPlanRepository repository) {
        this.repository = repository;
    }

    public List<AppointmentDto> getAppointmentsByDay(LocalDateTime date, Long doctorId) {
        List<Appointment> appointments = repository.findByIdDoctorAndDateTimeBetween(doctorId, date, date.plusDays(1));
        List<AppointmentDto> resultList = new ArrayList<>();
        for (Appointment appointment : appointments) {
            AppointmentDto dto = AppointmentDto.builder()
                    .appointmentId(appointment.getId())
                    .start(appointment.getDateTime())
                    .end(appointment.getDateTime().plusHours(duration))
                    .comment(appointment.getComment())
                    .build();
            resultList.add(dto);
        }
        return resultList;
    }
}
