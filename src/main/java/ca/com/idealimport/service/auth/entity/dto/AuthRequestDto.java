package ca.com.idealimport.service.auth.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public record AuthRequestDto(@Schema(example = "X56758KHNA", description = "this filed to use user pass his/her email id or username") String userName,
                             @Schema(example = "eb3c1499-7182-418c-aafc-46652402ca7f", description = "this filed to use user pass his/her email id or username") String password) {
}
