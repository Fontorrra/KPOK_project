package ru.croc.appointment.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.croc.appointment.domain.Appointment;
import ru.croc.appointment.domain.Vacation;
import ru.croc.appointment.dto.AppointmentRequestDto;
import ru.croc.appointment.dto.AppointmentResponseDto;
import ru.croc.appointment.repository.DataForPlanRepository;
import ru.croc.appointment.repository.VacationForAppointmentRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmptyAppointmentsService {
    private final DataForPlanRepository dataForPlanRepository;
    private final VacationForAppointmentRepository vacationForAppointmentRepository;

    @Value("${const.start-time}")
    private Integer startTime;
    @Value("${const.end-time}")
    private Integer endTime;
    @Value("${const.duration-per-patient}")
    private Integer duration;
    @Value("${const.lunch-start}")
    private Integer lunch;
    @Value("${const.weekend-days}")
    private Set<Integer> weekends;

    public EmptyAppointmentsService(DataForPlanRepository dataForPlanRepository, VacationForAppointmentRepository vacationForAppointmentRepository) {
        this.dataForPlanRepository = dataForPlanRepository;
        this.vacationForAppointmentRepository = vacationForAppointmentRepository;
    }

    public List<LocalDateTime> getEmptyTimes(Long idDoctor, LocalDate date) {
        if (date.isBefore(LocalDate.now())) return new ArrayList<>();
        if (weekends.contains(date.getDayOfWeek().getValue())) return new ArrayList<>();

        LocalDateTime startDate = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), startTime, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), endTime - 1, 59, 59);

        List<Vacation> vacations = vacationForAppointmentRepository.findByIdDoctor(idDoctor);

        for (Vacation vacation : vacations) {
            if (startDate.isAfter(vacation.getBeginDate()) && startDate.isBefore(vacation.getEndDate())) {
                return new ArrayList<>();
            }
        }

        List<Appointment> busyAppointments = dataForPlanRepository.findByIdDoctorAndDateTimeBetween(idDoctor, startDate, endDate);
        List<LocalDateTime> busyTimes = busyAppointments.stream().map(Appointment::getDateTime).collect(Collectors.toList());
        //старт обеда
        busyTimes.add(LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), lunch, 0, 0));
        List<LocalDateTime> ans = new ArrayList<>();

        int currentTime = startTime;
        while (currentTime < lunch) {
            LocalDateTime currentDate = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), currentTime, 0, 0);
            if (!busyTimes.contains(currentDate)) {
                ans.add(currentDate);
            }
            currentTime += duration;
        }
        currentTime = lunch + 1;
        while (currentTime < endTime) {
            LocalDateTime currentDate = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), currentTime, 0, 0);
            if (!busyTimes.contains(currentDate)) {
                ans.add(currentDate);
            }
            currentTime += duration;
        }
        return ans;
    }

    public AppointmentResponseDto checkAppointment(AppointmentRequestDto appointmentRequestDto){
        AppointmentResponseDto appointmentResponseDto = new AppointmentResponseDto(appointmentRequestDto.getDoctorId(),
                appointmentRequestDto.getPatientId(), appointmentRequestDto.getDateTime(), false);
        List<LocalDateTime> times = getEmptyTimes(appointmentRequestDto.getDoctorId(),
                LocalDate.from(appointmentRequestDto.getDateTime()));
        for (LocalDateTime time:
             times) {
            if (time.equals(appointmentRequestDto.getDateTime())){
                addAppointment(appointmentRequestDto);
                appointmentResponseDto.setConfirmation(true);
                return appointmentResponseDto;
            }
        }

        return appointmentResponseDto;

    }
    public void addAppointment(AppointmentRequestDto appointmentRequestDto){
        Appointment appointment = new Appointment();
        appointment.setIdPatient(appointmentRequestDto.getPatientId());
        appointment.setIdDoctor(appointmentRequestDto.getDoctorId());
        appointment.setDateTime(appointmentRequestDto.getDateTime());
        appointment.setComment("");
        appointment.setConfirmation(true);
        dataForPlanRepository.save(appointment);

    }
}
