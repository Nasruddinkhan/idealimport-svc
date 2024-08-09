package ca.com.idealimport.service.users.entity.dto;

import ca.com.idealimport.common.dto.DropDownDto;
import ca.com.idealimport.service.role.entity.dto.RoleResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {
    private Long userId;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNo;
    private Set<RoleResponseDto> roles;
    private Boolean isActive;

    private List<DropDownDto> additionalPermission;

}
