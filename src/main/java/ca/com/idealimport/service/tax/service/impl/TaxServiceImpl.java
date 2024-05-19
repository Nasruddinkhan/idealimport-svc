package ca.com.idealimport.service.tax.service.impl;

import ca.com.idealimport.common.dto.TaxDto;
import ca.com.idealimport.common.mapper.TaxMapper;
import ca.com.idealimport.common.util.CommonUtils;
import ca.com.idealimport.config.exception.IdealException;
import ca.com.idealimport.config.exception.enums.IdealResponseErrorCode;
import ca.com.idealimport.service.tax.boundry.repository.TaxRepository;
import ca.com.idealimport.service.tax.entity.Tax;
import ca.com.idealimport.service.tax.service.TaxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaxServiceImpl implements TaxService {
    private final TaxMapper taxMapper;
    private final TaxRepository taxRepository;

    @Override
    @Transactional
    public TaxDto createTax(TaxDto taxDto) {
        log.info("TaxService.createTax start taxDto = {}", taxDto);
        final Tax tax = taxMapper.taxDtoToTax(taxDto);
        tax.setTaxId(CommonUtils.getUUID(tax.getTaxId()));
        tax.setIsActive(true);
        final TaxDto taxDt = taxMapper.taxToTaxDto(taxRepository.save(tax));
        log.info("TaxService.createTax start taxDt = {}", taxDt);
        return taxDt;
    }

    @Override
    public TaxDto findTaxById(String taxId) {
        log.info("TaxService.findTaxById start taxId = {}", taxId);
        final TaxDto taxDto = taxRepository
                .findByTaxIdAndIsActiveTrue(taxId)
                .map(taxMapper::taxToTaxDto)
                .orElseThrow(() -> new IdealException(IdealResponseErrorCode.NOT_FOUND, String.format("no record present for this id %s", taxId)));
        log.info("TaxService.findTaxById end taxId = {}", taxDto);
        return taxDto;
    }

    @Override
    public Tax findTax(String taxId) {
        log.info("TaxService.findTax start taxId = {}", taxId);
        final Tax tax = taxRepository
                .findByTaxIdAndIsActiveTrue(taxId)
                .orElseThrow(() -> new IdealException(IdealResponseErrorCode.NOT_FOUND, String.format("no record present for this id %s", taxId)));
        log.info("TaxService.findTax end taxId = {}", tax);
        return tax;
    }
    @Override
    public List<TaxDto> findAllTaxes() {
        log.info("TaxService.findAllTaxes start ");
        List<TaxDto> taxDtos = taxRepository.findAllByIsActiveTrue()
                .map(taxMapper::taxToTaxDto)
                .orElseGet(() -> List.of());
        log.info("TaxService.findAllTaxes end ", taxDtos);
        return taxDtos;
    }

    @Override
    public void deleteTaxByTaxId(String taxId) {
        findTaxById(taxId);
        taxRepository.deleteById(taxId);
    }
}
