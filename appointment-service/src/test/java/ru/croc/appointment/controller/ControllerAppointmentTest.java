package ru.croc.appointment.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.croc.appointment.dto.AppointmentDto;
import ru.croc.appointment.service.EmptyAppointmentsService;
import ru.croc.appointment.service.ServiceForPlan;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ControllerAppointmentTest {
    private ControllerAppointment controller;
    private EmptyAppointmentsService emptyAppointmentsService;
    private ServiceForPlan serviceForPlan;

    @BeforeEach
    public void setUp() {
        emptyAppointmentsService = mock(EmptyAppointmentsService.class);
        serviceForPlan = mock(ServiceForPlan.class);
        controller = new ControllerAppointment(emptyAppointmentsService, serviceForPlan);
    }
    @Test
    public void testDoctorPlan_Success() {
        long id = 1L;
        LocalDate dateTime = LocalDate.of(2023, 7, 26);
        List<AppointmentDto> appointmentTestDtos = new ArrayList<>();

        when(serviceForPlan
                .getAppointmentsByDay(any(LocalDateTime.class), eq(id)))
                .thenReturn(appointmentTestDtos);

        ResponseEntity<?> response = controller.doctorPlan(id, dateTime);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(appointmentTestDtos, response.getBody());
    }

    @Test
    public void testDoctorPlan_NotFound() {
        long id = 1L;
        LocalDate dateTime = LocalDate.of(2023, 7, 26);

        when(serviceForPlan
                .getAppointmentsByDay(any(LocalDateTime.class), eq(id)))
                .thenThrow(new RuntimeException());

        ResponseEntity<?> response = controller.doctorPlan(id, dateTime);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDoctorSchedule_Success() {
        long doctorId = 1L;
        LocalDate dateTime = LocalDate.of(2023,7, 26);
        List<LocalDateTime> schedule = new ArrayList<>();

        when(emptyAppointmentsService
                .getEmptyTimes(eq(doctorId), any(LocalDate.class)))
                .thenReturn(schedule);

        ResponseEntity<?> response = controller.doctorSchedule(doctorId, dateTime);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(schedule, response.getBody());
    }
    @Test
    public void testDoctorSchedule_NotFound() {
        long doctorId = 1L;
        LocalDate dateTime = LocalDate.of(2023, 7, 26);

        when(emptyAppointmentsService
                .getEmptyTimes(eq(doctorId), any(LocalDate.class)))
                .thenThrow(new RuntimeException());

        ResponseEntity<?> response = controller.doctorSchedule(doctorId, dateTime);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
