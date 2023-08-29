package ru.croc.appointment.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.croc.appointment.domain.Vacation;

import java.util.List;

@Repository
public interface VacationForAppointmentRepository extends CrudRepository<Vacation, Long> {
    List<Vacation> findByIdDoctor(@Param("idDoctor") Long idDoctor);
}
