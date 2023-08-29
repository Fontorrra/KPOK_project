package ru.croc.totmrtest.general.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MetaDataDto {
    private String type;
    private String ts;
    private Boolean IsNew;
    private Boolean IsUpdate;
    private Boolean isRemoved;
}
