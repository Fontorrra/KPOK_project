package ru.croc.appointment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.croc.appointment.dto.AppointmentRequestDto;
import ru.croc.appointment.dto.AppointmentResponseDto;
import ru.croc.appointment.dto.ScheduleRequestDto;
import ru.croc.appointment.dto.ScheduleResponseDto;

import java.util.concurrent.CountDownLatch;

@Component
public class KafkaListeners {
    private final static Logger logger = LoggerFactory.getLogger(KafkaListeners.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final EmptyAppointmentsService emptyAppointmentsService;
    ObjectMapper objectMapper;

    @Value("${spring.kafka.topics.topic2}")
    String scheduleResponseTopic;

    @Value("${spring.kafka.topics.topic4}")
    String appointmentResponseTopic;

    private CountDownLatch latch = new CountDownLatch(1);
    private String payload;

    public KafkaListeners(KafkaTemplate<String, String> kafkaTemplate, EmptyAppointmentsService emptyAppointmentsService) {
        this.kafkaTemplate = kafkaTemplate;
        this.emptyAppointmentsService = emptyAppointmentsService;
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @KafkaListener(groupId = "${spring.kafka.group.id}", topics = "${spring.kafka.topics.topic1}")
    public void receiveEmptyTimeframe(ConsumerRecord<String, String> record) throws JsonProcessingException {
        ScheduleRequestDto requestDto;

        logger.info(record.value());
        requestDto = objectMapper.readValue(record.value() , ScheduleRequestDto.class);

        ScheduleResponseDto responseDto = new ScheduleResponseDto();

        responseDto.setSchedule(emptyAppointmentsService.getEmptyTimes(requestDto.getDoctorId(), requestDto.getDate()));
        responseDto.setDoctorId(requestDto.getDoctorId());
        logger.info("get dto with fields: " + " " + requestDto.getDoctorId() + " " + requestDto.getDate());
        kafkaTemplate.send(scheduleResponseTopic, objectMapper.writeValueAsString(responseDto));
        logger.info("response: " + objectMapper.writeValueAsString(responseDto));
    }

    @KafkaListener(groupId = "${spring.kafka.group.id}", topics = "${spring.kafka.topics.topic3}")
    public void receiveConfirmation(ConsumerRecord<String, String> record) throws JsonProcessingException {
        logger.info(record.toString());
        AppointmentRequestDto requestDto = new AppointmentRequestDto();
        logger.info(record.value() + " - полученное значение");
        requestDto = objectMapper.readValue(record.value(), AppointmentRequestDto.class);
        AppointmentResponseDto responseDto = emptyAppointmentsService.checkAppointment(requestDto);
        kafkaTemplate.send(appointmentResponseTopic, objectMapper.writeValueAsString(responseDto));
        logger.info("response: " + objectMapper.writeValueAsString(responseDto));
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public String getPayload() {
        return payload;
    }
}
