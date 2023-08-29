package ru.croc.appointment.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.croc.appointment.dto.AppointmentForCommentDto;
import ru.croc.appointment.service.CommentService;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ControllerCommentTest {
    private ControllerComment controller;
    private CommentService commentService;

    @BeforeEach
    public void setUp() {
        commentService = mock(CommentService.class);
        controller = new ControllerComment(commentService);
    }

    @Test
    public void testAddComment_Success() {
        long appointmentId = 1L;
        String comment = "Тестовый комментарий";
        AppointmentForCommentDto appointmentDto = AppointmentForCommentDto.builder()
                .patientId(1L)
                .start(LocalDateTime.now())
                .comment("Тестовый комментарий")
                .build();
        when(commentService
                .addCommentToAppointment(eq(appointmentId), eq(comment)))
                .thenReturn(Optional.of(appointmentDto));

        ResponseEntity<?> response = controller.addComment(appointmentId, comment);

        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(appointmentDto, response.getBody());
    }

    @Test
    public void testAddComment_InvalidData() {
        long appointmentId = 1L;
        String comment = "Тестовый комментарий";

        when(commentService
                .addCommentToAppointment(eq(appointmentId), eq(comment)))
                .thenReturn(Optional.empty());

        ResponseEntity<?> response = controller.addComment(appointmentId, comment);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Incorrect date or id", response.getBody());
    }

    @Test
    public void testAddComment_NotFound() {
        long appointmentId = 1L;
        String comment = "Тестовый комментарий";

        when(commentService
                .addCommentToAppointment(eq(appointmentId), eq(comment)))
                .thenThrow(new RuntimeException());

        ResponseEntity<?> response = controller.addComment(appointmentId, comment);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
