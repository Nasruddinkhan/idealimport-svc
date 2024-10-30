package ca.com.idealimport.common.util;

import ca.com.idealimport.service.permissions.control.PermissionControl;
import ca.com.idealimport.service.permissions.entity.dto.PermissionDto;
import ca.com.idealimport.service.role.control.RoleControl;
import ca.com.idealimport.service.role.entity.dto.RoleDto;
import ca.com.idealimport.service.role.entity.dto.RoleResponseDto;
import ca.com.idealimport.service.saleorder.entity.dto.SaleOrderStatusDto;
import ca.com.idealimport.service.saleorder.entity.dto.SaleOrderStatusResponse;
import ca.com.idealimport.service.saleorder.entity.dto.SaleOrderStatusResponseDto;
import ca.com.idealimport.service.saleorder.service.SaleOrderStatusService;
import ca.com.idealimport.service.users.control.UserControl;
import ca.com.idealimport.service.users.entity.dto.UserDto;
import ca.com.idealimport.service.users.entity.dto.UserResponseDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static ca.com.idealimport.common.constants.MessageConstants.PERMISSION_PATH;
import static ca.com.idealimport.common.constants.MessageConstants.ROLE_PATH;
import static ca.com.idealimport.common.constants.MessageConstants.STATUS_FILE_PATH;
import static ca.com.idealimport.common.constants.MessageConstants.USER_PATH;
@RequiredArgsConstructor
@Configuration
@Slf4j
public class InitializerDataConfig {

    private final SaleOrderStatusService statusService;
    private final PermissionControl permissionControl;
    private final RoleControl roleControl;
    private final UserControl userControl;
    private final ResourceLoader resourceLoader;

    @Value("${default.password}")
    private String defaultPassword;

    @PostConstruct
    public void loadData() throws IOException {
        log.info("Initializing data loading...");
       // loadOrderStatus(STATUS_FILE_PATH);
        loadPermission(PERMISSION_PATH);
        loadRole(ROLE_PATH);
        loadUser(USER_PATH);
        log.info("Data loading completed.");
    }

    private void loadUser(String filename) throws IOException {
        List<UserResponseDto> allUser = userControl.findAllUser();
        if (Objects.isNull(allUser) || allUser.isEmpty()) {
            Set<Long> roles = roleControl.findAllRoles().stream().map(RoleResponseDto::roleId).collect(Collectors.toSet());
            try (InputStream inputStream = CommonUtils.readFileFromResources(filename, resourceLoader)) {
                UserDto userDto = ConversionUtils.jsonStreamToObject(inputStream, UserDto.class);
                userDto = userDto.addRoles(roles);
                userControl.createUser(userDto, defaultPassword);
                log.info("Added {} user records to the database", userDto);
            }
        }
    }

    private void loadRole(String filename) throws IOException {
        final List<RoleResponseDto> allRoles = roleControl.findAllRoles();
        if (Objects.isNull(allRoles) || allRoles.isEmpty()) {
            try (InputStream inputStream = CommonUtils.readFileFromResources(filename, resourceLoader)) {
                RoleDto roleDto = ConversionUtils.jsonStreamToObject(inputStream, RoleDto.class);
                Set<Long> longs = permissionControl.findAllPermission().stream()
                        .map(PermissionDto::permissionId).collect(Collectors.toSet());
                roleDto = roleDto.addPermissions(longs);
                RoleResponseDto role = roleControl.createRole(roleDto);
                log.info("Added {} role records to the database", role);
            }
        }
    }

    private void loadPermission(String filename) throws IOException {
        final List<PermissionDto> allPermission = permissionControl.findAllPermission();
        if (Objects.isNull(allPermission) || allPermission.isEmpty()) {
            try (InputStream inputStream = CommonUtils.readFileFromResources(filename, resourceLoader)) {
                PermissionDto permissionDto = ConversionUtils.jsonStreamToObject(inputStream, PermissionDto.class);
                PermissionDto dto = permissionControl.createPermission(permissionDto);
                log.info("Added {} permission records to the database", dto);
            }
        }
    }

    public void loadOrderStatus(String filename) throws IOException {
        final List<SaleOrderStatusResponseDto> allStatus = statusService.findAllStatus();
        if (Objects.isNull(allStatus) || allStatus.isEmpty()) {
            try (InputStream inputStream = CommonUtils.readFileFromResources(filename, resourceLoader)) {
                List<SaleOrderStatusDto> statusList = ConversionUtils.jsonStreamToList(inputStream, SaleOrderStatusResponse.class)
                        .stream().map(SaleOrderStatusResponse::getStatusDto)
                        .map(statusService::createStatus)
                        .collect(Collectors.toList());
                log.info("Added {} status records to the database", statusList.size());
            }
        }
    }
}