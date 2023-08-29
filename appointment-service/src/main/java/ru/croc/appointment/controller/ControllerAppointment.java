package ru.croc.appointment.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.croc.appointment.dto.AppointmentDto;
import ru.croc.appointment.service.EmptyAppointmentsService;
import ru.croc.appointment.service.ServiceForPlan;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/appointment")
public class ControllerAppointment {
    private final EmptyAppointmentsService emptyAppointmentsService;
    private final ServiceForPlan serviceForPlan;

    @Autowired
    public ControllerAppointment(EmptyAppointmentsService emptyAppointmentsService, ServiceForPlan serviceForPlan) {
        this.emptyAppointmentsService = emptyAppointmentsService;
        this.serviceForPlan = serviceForPlan;
    }

    @RequestMapping(value = "/plan/{id}",
            method = GET)
    public ResponseEntity<?> doctorPlan(@PathVariable("id") long id, @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate dateTime) {

        try {
            List<AppointmentDto> plan = serviceForPlan.getAppointmentsByDay(dateTime.atStartOfDay(), id);
            System.out.println(plan);
            return new ResponseEntity<>(plan, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value = "/schedule",
            method = GET)
    public ResponseEntity<?> doctorSchedule(@RequestParam("id") long DoctorId, @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate dateTime) {

        try {
            List<LocalDateTime> schedule = emptyAppointmentsService.getEmptyTimes(DoctorId, dateTime);
            System.out.println(schedule);

            return new ResponseEntity<>(schedule, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}

