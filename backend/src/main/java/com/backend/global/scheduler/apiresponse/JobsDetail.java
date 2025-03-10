package com.backend.global.scheduler.apiresponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class JobsDetail {

    @JsonProperty("job")
    public List<Job> jobList;

    @JsonProperty("total")
    public String total;
}
