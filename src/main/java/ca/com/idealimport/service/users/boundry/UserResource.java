package ca.com.idealimport.service.users.boundry;

import ca.com.idealimport.common.dto.DropDownDto;
import ca.com.idealimport.common.enums.AdditionalPermissionEnum;
import ca.com.idealimport.service.users.control.UserControl;
import ca.com.idealimport.service.users.entity.dto.ChangePasswordRequest;
import ca.com.idealimport.service.users.entity.dto.ChangePasswordResponse;
import ca.com.idealimport.service.users.entity.dto.UserDto;
import ca.com.idealimport.service.users.entity.dto.UserResponseDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/v1.0")
@Slf4j
@SecurityRequirement(name = "ideal-api")
public record UserResource(UserControl userControl) {

    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> findAllUsers(@RequestParam("page") int page,
                                                              @RequestParam("size") int size) {
        log.info("UserResource.findAllUsers start");
        final var allUser = userControl.findAllUser(page, size);
        log.info("UserResource.findAllUsers end");
        return new ResponseEntity<>(allUser, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody UserDto userDto) {
        log.info("UserResource.updateUser start");
        final var user = userControl.updateUser(userDto);
        log.info("UserResource.updateUser end");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/{user-Id}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable("user-Id") String userId) {
        log.info("UserResource.findUserById start");
        final var user = userControl.findUserById(userId);
        log.info("UserResource.findUserById end");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    //Change password

    @PostMapping("/change-password")
    public ResponseEntity<ChangePasswordResponse> changePassword(@RequestBody ChangePasswordRequest passwordRequest) {
        ChangePasswordResponse passwordResponse = userControl.changePassword(passwordRequest);
        return new ResponseEntity<>(passwordResponse, HttpStatus.OK);
    }

    @GetMapping("/additional-permission")
    public ResponseEntity<List<DropDownDto>> getAdditionalPermission() {
        return ResponseEntity.ok(AdditionalPermissionEnum.getAdditionalPermission().entrySet()
                .stream().map(e -> DropDownDto.builder()
                        .key(e.getKey())
                        .value(e.getValue())
                        .build()).toList());
    }

}
