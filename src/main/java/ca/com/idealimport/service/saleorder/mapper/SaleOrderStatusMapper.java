package ca.com.idealimport.service.saleorder.mapper;

import ca.com.idealimport.service.saleorder.entity.SaleOrderStatus;
import ca.com.idealimport.service.saleorder.entity.dto.SaleOrderStatusDto;
import ca.com.idealimport.service.saleorder.entity.dto.SaleOrderStatusResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface SaleOrderStatusMapper {
    SaleOrderStatus saleOrderStatusDtoToEntity(SaleOrderStatusDto saleOrderStatusDto);

    SaleOrderStatusDto saleOrderStatusEntityToDto(SaleOrderStatus saleOrderStatus);


    @Mapping(source = "saleOrderStatusId", target = "statusDto.saleOrderStatusId")
    @Mapping(source = "name", target = "statusDto.name")
    @Mapping(source = "description", target = "statusDto.description")
    @Mapping(source = "createdBy", target = "auditDto.createdBy")
    @Mapping(source = "lastModifiedBy", target = "auditDto.lastModifiedBy")
    @Mapping(source = "lastModifiedDate", target = "auditDto.lastModifiedDate")
    @Mapping(source = "createdDate", target = "auditDto.createdDate")
    SaleOrderStatusResponseDto saleOrderStatusToSaleOrderStatusResponseDto(SaleOrderStatus saleOrderStatus);

    default List<SaleOrderStatusResponseDto> mapList(List<SaleOrderStatus> entities) {
        return entities.stream().map(this::saleOrderStatusToSaleOrderStatusResponseDto).toList();
    }
}
