package ca.com.idealimport.service.saleorder.service.impl;

import ca.com.idealimport.common.constants.ErrorConstants;
import ca.com.idealimport.common.util.CommonUtils;
import ca.com.idealimport.config.exception.IdealException;
import ca.com.idealimport.config.exception.enums.IdealResponseErrorCode;
import ca.com.idealimport.service.saleorder.entity.SaleOrderStatus;
import ca.com.idealimport.service.saleorder.entity.dto.SaleOrderStatusDto;
import ca.com.idealimport.service.saleorder.entity.dto.SaleOrderStatusResponseDto;
import ca.com.idealimport.service.saleorder.mapper.SaleOrderStatusMapper;
import ca.com.idealimport.service.saleorder.repository.SaleOrderStatusRepository;
import ca.com.idealimport.service.saleorder.service.SaleOrderStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Deprecated
public class SaleOrderStatusServiceImpl implements SaleOrderStatusService {
    private final SaleOrderStatusRepository saleOrderStatusRepository;
    private final SaleOrderStatusMapper saleOrderStatusMapper;

    @Override
    @Transactional
    public SaleOrderStatusDto createStatus(SaleOrderStatusDto saleOrderStatusDto) {
        SaleOrderStatus saleOrderStatus = saleOrderStatusMapper.saleOrderStatusDtoToEntity(saleOrderStatusDto);
        saleOrderStatus.setSaleOrderStatusId(CommonUtils.getUUID(saleOrderStatusDto.saleOrderStatusId()));
        return saleOrderStatusMapper.saleOrderStatusEntityToDto(saleOrderStatusRepository.save(saleOrderStatus));
    }

    @Override
    public List<SaleOrderStatusResponseDto> findAllStatus() {
        return Optional.of(saleOrderStatusRepository.findAllByIsActiveTrue())
                .map(saleOrderStatusMapper::mapList)
                .orElse(List.of());

    }

    @Override
    public SaleOrderStatus findStatus(String statusId) {
        return saleOrderStatusRepository.findById(statusId)
                .orElseThrow(() -> new IdealException(IdealResponseErrorCode.NOT_FOUND,
                        String.format(ErrorConstants.SALE_ORDER_NOT_FOUND, statusId)));

    }
}
