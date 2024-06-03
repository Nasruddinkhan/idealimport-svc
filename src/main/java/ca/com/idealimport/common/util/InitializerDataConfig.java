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
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
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

    @PostConstruct
    public void loadData() throws IOException {
        log.info("Initializing data loading...");
        ClassLoader classLoader = getClass().getClassLoader();
        loadOrderStatus(STATUS_FILE_PATH, classLoader);
        loadPermission(PERMISSION_PATH, classLoader);
        loadRole(ROLE_PATH, classLoader);
        loadUser(USER_PATH, classLoader);
        log.info("Data loading completed.");
    }

    private void loadUser(String filename, ClassLoader classLoader) throws IOException {
        List<UserResponseDto> allUser = userControl.findAllUser();
        if (Objects.isNull(allUser) || allUser.isEmpty()) {
            Set<Long> roles = roleControl.findAllRoles().stream().map(RoleResponseDto::roleId).collect(Collectors.toSet());
            final File file = CommonUtils.readFileFromResources(filename, classLoader);
            UserDto userDto = ConversionUtils.jsonFileToObject(file, UserDto.class);
            userDto = userDto.addRoles(roles);
                userControl.createUser(userDto, "Nk@admin1992");
            log.info("Added {}  user records to the database", userDto);

        }
    }

    private void loadRole(String filename, ClassLoader classLoader) throws IOException {
        final List<RoleResponseDto> allRoles = roleControl.findAllRoles();
        if (Objects.isNull(allRoles) || allRoles.isEmpty()) {
            final File file = CommonUtils.readFileFromResources(filename, classLoader);
            RoleDto roleDto = ConversionUtils.jsonFileToObject(file, RoleDto.class);
            Set<Long> longs = permissionControl.findAllPermission().stream()
                    .map(PermissionDto::permissionId).collect(Collectors.toSet());
            roleDto = roleDto.addPermissions(longs);
            RoleResponseDto role = roleControl.createRole(roleDto);
            log.info("Added {}  role records to the database", role);
        }
    }

    private void loadPermission(String filename, ClassLoader classLoader) throws IOException {
        final List<PermissionDto> allPermission = permissionControl.findAllPermission();
        if (Objects.isNull(allPermission) || allPermission.isEmpty()) {
            final File file = CommonUtils.readFileFromResources(filename, classLoader);
            PermissionDto permissionDto = ConversionUtils.jsonFileToObject(file, PermissionDto.class);
            PermissionDto dto = permissionControl.createPermission(permissionDto);
            log.info("Added {}  permission records to the database", dto);
        }

    }

    public void loadOrderStatus(String filename, ClassLoader classLoader) throws IOException {

        final List<SaleOrderStatusResponseDto> allStatus = statusService.findAllStatus();
        if (Objects.isNull(allStatus) || allStatus.isEmpty()) {
            final File file = CommonUtils.readFileFromResources(filename, classLoader);
            final List<SaleOrderStatusDto> statusList = ConversionUtils.jsonFileToList(file, SaleOrderStatusResponse.class)
                    .stream().map(SaleOrderStatusResponse::getStatusDto)
                    .map(statusService::createStatus)
                    .toList();
            log.info("Added {} status records to the database", statusList.size());
        }

    }
}
