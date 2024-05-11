package com.system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {

    @JsonProperty(value = "data")
    private Object data;

    @JsonProperty(value = "message")
    private String message = "Data successfully operated";

    @JsonProperty(value = "code")
    private int code;

    public String dataCreated()
    {
        return "Data Created Successfully";
    }
    public String dataRetrived()
    {
        return "Data Retrived Successfully";
    }
}
