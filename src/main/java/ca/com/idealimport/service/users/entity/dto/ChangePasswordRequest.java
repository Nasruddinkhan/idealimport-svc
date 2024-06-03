package ca.com.idealimport.service.users.entity.dto;

import lombok.Builder;

@Builder
public record ChangePasswordRequest(String newPassword, String confirmPassword) {
}