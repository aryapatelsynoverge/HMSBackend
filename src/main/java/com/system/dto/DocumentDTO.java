package com.system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDTO {

    @JsonProperty(value = "document_file")
    private String documentFile;

    @JsonProperty(value = "file_category")
    private String fileCategory;

    @JsonProperty(value = "file_name")
    private String fileName;

    @JsonProperty(value = "file_type")
    private String fileType;

    @JsonProperty(value = "file_date")
    @Temporal(TemporalType.DATE)
    private Date fileDate;

    @JsonProperty(value = "file_size")
    private double fileSize;

}
