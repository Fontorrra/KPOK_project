package ru.croc.totmrtest.general;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.croc.totmrtest.general.dto.AppointmentTestDto;
import ru.croc.totmrtest.general.dto.MetaDataDto;
import ru.croc.totmrtest.util.GetIdHelper;

import java.time.LocalDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class AppointmentTest {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "http://appointment-service-8083:8083/api";
        RestAssured.useRelaxedHTTPSValidation();
    }


    @Test
    void createAppointment() throws JsonProcessingException {
        //Specifications.installRequestSpecification(Specifications.requestSpec(URL));

        MetaDataDto metaData = MetaDataDto.builder()
                .type("Appointment")
                .IsNew(true)
                .build();
        AppointmentTestDto postDto = AppointmentTestDto.builder()
                .__metadata(metaData)
                .idDoctor(127L)
                .idPatient(456L)
                .dateTime(LocalDateTime.of(2023, 7, 26, 12, 0).toString())
                .confirmation(false)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String body = "[" + objectMapper
                .setSerializationInclusion(JsonInclude.Include.NON_NULL).writeValueAsString(postDto) + "]";

        //отправляем на сервер
        String testDto = given()
                .contentType("application/json")
                .body(body)
                .when()
                .post("/_store")
                .then()
                .statusCode(201)
                .extract().body().asString();

        Long id = GetIdHelper.getId(testDto);

        //принимаем с сервера
        String responce = given()
                .get("/Appointment")
                .then()
                .statusCode(200)
                .extract().body().asString();

        //приняли список
        List<AppointmentTestDto> appointmentList = from(responce).getList("result", AppointmentTestDto.class);

        //достаем подходящий по id
        AppointmentTestDto getDto = appointmentList.stream()
                .filter(dto -> dto.getId().equals(Long.valueOf(id)))
                .findFirst()
                .orElse(null);

        //проверки
        assertNotNull(getDto);
        assertEquals(postDto.getIdPatient(), getDto.getIdPatient());
        assertEquals(postDto.getIdDoctor(), getDto.getIdDoctor());



    }

}
