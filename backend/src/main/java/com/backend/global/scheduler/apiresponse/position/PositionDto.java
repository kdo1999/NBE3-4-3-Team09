package com.backend.global.scheduler.apiresponse.position;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PositionDto {

    @JsonProperty("title")
    public String title;

    @JsonProperty("job-code")
    public JobCodeDto jobCode;

    @JsonProperty("experience-level")
    public ExperienceLevelDto experienceLevel;

    @JsonProperty("required-education-level")
    public RequireEducateDto requireEducate;


}
