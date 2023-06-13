package ca.com.idealimport.common.mapper;

import ca.com.idealimport.service.mail.entity.dto.MailDTO;
import ca.com.idealimport.service.users.entity.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EmailMapper {
    public MailDTO convertRegisterUserDtoToMailDto(UserDto userDto, String password, String userName) {
        return MailDTO.builder()
                .mailTo(userDto.email())
                .requiredData(Map.of("userId",userName,"password", password, "email", userDto.email(), "name", userDto.firstName()+" "+userDto.lastName()))
                .build();
    }
}
