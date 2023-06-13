package ca.com.idealimport.service.mail.entity.dto;

import lombok.Builder;

import java.util.Map;
public record MailDTO(String mailTo, Map<String, Object> requiredData) {
    @Builder
    public MailDTO {}
}
