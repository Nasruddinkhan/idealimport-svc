package ca.com.idealimport.common.util;

import ca.com.idealimport.service.permissions.control.PermissionControl;
import ca.com.idealimport.service.permissions.entity.dto.PermissionDto;
import ca.com.idealimport.service.saleorder.entity.dto.SaleOrderStatusDto;
import ca.com.idealimport.service.saleorder.entity.dto.SaleOrderStatusResponse;
import ca.com.idealimport.service.saleorder.entity.dto.SaleOrderStatusResponseDto;
import ca.com.idealimport.service.saleorder.service.SaleOrderStatusService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static ca.com.idealimport.common.constants.MessageConstants.PERMISSION_PATH;
import static ca.com.idealimport.common.constants.MessageConstants.STATUS_FILE_PATH;

@RequiredArgsConstructor
@Configuration
@Slf4j
public class InitializerDataConfig {
    private final SaleOrderStatusService statusService;
    private final PermissionControl permissionControl;

    @PostConstruct
    public void loadData() throws IOException {
        log.info("Initializing data loading...");
        ClassLoader classLoader = getClass().getClassLoader();
        loadOrderStatus(STATUS_FILE_PATH, classLoader);
        loadPermission(PERMISSION_PATH, classLoader);
        log.info("Data loading completed.");
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
