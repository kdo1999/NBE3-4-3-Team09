package com.backend.global.scheduler.apiresponse.position;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class JobCodeDto {

    @JsonProperty("code")
    public String code;

    @JsonProperty("name")
    public String name;


}
