package ru.croc.totmrtest.general;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.croc.totmrtest.general.dto.MetaDataDto;
import ru.croc.totmrtest.general.dto.PatientTestDto;
import ru.croc.totmrtest.spec.Specifications;
import ru.croc.totmrtest.util.GetIdHelper;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
public class PatientTest {
    private final static String URL = "http://patient-service-8082:8082/";
    @Test
    void createPatient() throws JsonProcessingException {
        Specifications.installRequestSpecification(Specifications.requestSpec(URL));

        MetaDataDto metaData = MetaDataDto.builder()
                .type("Patient")
                .IsNew(true)
                .build();
        PatientTestDto postDto = PatientTestDto.builder()
                .__metadata(metaData)
                .firstName("NIKITA")
                .lastName("IVANOV")
                .fatherName("IVANOVICH")
                .address("WWWWWWWEWEW")
                .phone("321314534534")
                .age(45)
                .email("ttttt@yandex.ru")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        var body = "[" + objectMapper
                .setSerializationInclusion(JsonInclude.Include.NON_NULL).writeValueAsString(postDto) + "]";

        String testDto = given()
                .contentType("application/json")
                .body(body)
                .when()
                .post("api/_store")
                .then()
                .statusCode(201)
                .extract().body().asString();

        Long id = GetIdHelper.getId(testDto);

        String response = given()
                .get("api/Patient")
                .then()
                .statusCode(200)
                .extract().body().asString();

        //приняли список
        List<PatientTestDto> appointmentList = from(response).getList("result", PatientTestDto.class);

        //достаем подходящий по id
        PatientTestDto getDto = appointmentList.stream()
                .filter(dto -> dto.getId().equals(Long.valueOf(id)))
                .findFirst()
                .orElse(null);

        assertNotNull(getDto);
        assertEquals(postDto.getFirstName(), getDto.getFirstName());
        assertEquals(postDto.getLastName(), getDto.getLastName());
        assertEquals(postDto.getAge(), getDto.getAge());
        assertEquals(postDto.getPhone(), getDto.getPhone());
    }
}
