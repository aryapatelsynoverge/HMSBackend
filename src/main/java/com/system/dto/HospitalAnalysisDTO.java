package com.system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HospitalAnalysisDTO {

    @JsonProperty(value = "hospital_name")
    private String hospitalName;

    @JsonProperty(value = "total_count")
    private long count;

}
