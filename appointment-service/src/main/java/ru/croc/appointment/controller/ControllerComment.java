package ru.croc.appointment.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.croc.appointment.dto.AppointmentDto;
import ru.croc.appointment.dto.AppointmentForCommentDto;
import ru.croc.appointment.service.CommentService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class ControllerComment {

    CommentService commentService;

    public ControllerComment(CommentService commentService) {
        this.commentService = commentService;
    }

    @RequestMapping(value = "/appointment/{appointmentId}/comment",
            method = POST)
    public ResponseEntity<?> addComment(@PathVariable("appointmentId") long appointmentId, @RequestBody String comment) {

        try {
            Optional<AppointmentForCommentDto> dto = commentService.addCommentToAppointment(appointmentId, comment);
            if (dto.isPresent()) {
                return new ResponseEntity<>(dto.get(), HttpStatus.CREATED);
            }
            else {
                return new ResponseEntity<>("Incorrect date or id" ,HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}
