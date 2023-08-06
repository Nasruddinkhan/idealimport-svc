package ca.com.idealimport.service.mail.entity.dto;

import lombok.Builder;

import java.util.Map;
@Builder

public record MailDTO(String mailTo, Map<String, Object> requiredData) {
}
