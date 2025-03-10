package com.backend.global.scheduler.apiresponse.position;

import com.backend.domain.jobposting.entity.ExperienceLevel;
import com.backend.global.scheduler.converter.EntityConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ExperienceLevelDto {

    @JsonProperty("code")
    public Integer code;

    @JsonProperty("min")
    public Integer min;

    @JsonProperty("max")
    public Integer max;

    @JsonProperty("name")
    public String name;

    public ExperienceLevel toEntity() {
        return EntityConverter.dtoToExperienceLevel(this);
    }


}
