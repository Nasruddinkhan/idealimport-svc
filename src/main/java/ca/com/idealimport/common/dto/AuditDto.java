package ca.com.idealimport.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.util.Date;

@Builder
public record AuditDto(String createdBy,
                       @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
                       Date createdDate,
                       String lastModifiedBy,
                       @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
                       Date lastModifiedDate) {
}
