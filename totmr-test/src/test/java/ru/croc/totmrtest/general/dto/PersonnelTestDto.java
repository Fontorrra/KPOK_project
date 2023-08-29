package ru.croc.totmrtest.general.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonnelTestDto {
    private MetaDataDto __metadata;
    private Long id;
    private String firstName;
    private String lastName;
    private String fatherName;
    private Integer experience;
    private Integer specialization;
    private Integer clinicExperience;
    private String additionalInfo;

}
