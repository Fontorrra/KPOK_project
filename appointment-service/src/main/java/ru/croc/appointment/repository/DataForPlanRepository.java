package ru.croc.appointment.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.croc.appointment.domain.Appointment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DataForPlanRepository extends CrudRepository<Appointment,Long> {
    Optional<Appointment> findByIdAndDateTimeAfter(@Param("id") long id, LocalDateTime dateTime);
    List<Appointment> findByIdDoctorAndDateTimeBetween(@Param("idDoctor") Long idDoctor,LocalDateTime dateTimeStart,LocalDateTime dateTimeEnd);
}
