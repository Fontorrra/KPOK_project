package ru.croc.totmrtest.general;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.croc.totmrtest.general.dto.MetaDataDto;
import ru.croc.totmrtest.general.dto.PersonnelTestDto;
import ru.croc.totmrtest.util.GetIdHelper;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PersonnelTest {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "http://personnel-service-8081:8081/api";
        RestAssured.useRelaxedHTTPSValidation();
    }

    @Test
    void createPersonnel() throws JsonProcessingException {
        //Specifications.installRequestSpecification(Specifications.requestSpec(URL));

        MetaDataDto metaData = MetaDataDto.builder()
                .type("Personnel")
                .IsNew(true)
                .build();
        PersonnelTestDto postDto = PersonnelTestDto.builder()
                .__metadata(metaData)
                .specialization(1)
                .clinicExperience(11)
                .experience(22)
                .additionalInfo("Cool")
                .firstName("Daniil")
                .lastName("Baranov")
                .fatherName("Yurii")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String body = "[" + objectMapper
                .setSerializationInclusion(JsonInclude.Include.NON_NULL).writeValueAsString(postDto) + "]";
//
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
        String response = given()
                .get("/Personnel")
                .then()
                .statusCode(200)
                .extract().body().asString();

        //приняли список
        List<PersonnelTestDto> personnelTestDtos = from(response).getList("result", PersonnelTestDto.class);

        //достаем подходящий по id
        PersonnelTestDto getDto = personnelTestDtos.stream()
                .filter(dto -> dto.getId().equals(Long.valueOf(id)))
                .findFirst()
                .orElse(null);

        //проверки
        assertNotNull(getDto);
        assertEquals(postDto.getClinicExperience(), getDto.getClinicExperience());
        assertEquals(postDto.getFirstName(), getDto.getFirstName());

    }

}
