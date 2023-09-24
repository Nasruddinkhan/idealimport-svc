package ca.com.idealimport.service.tax.service.impl;

import ca.com.idealimport.common.dto.TaxDto;
import ca.com.idealimport.common.mapper.TaxMapper;
import ca.com.idealimport.common.util.CommonUtils;
import ca.com.idealimport.service.tax.boundry.repository.TaxRepository;
import ca.com.idealimport.service.tax.entity.Tax;
import ca.com.idealimport.service.tax.service.TaxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaxServiceImpl implements TaxService {
    private final TaxMapper taxMapper;
    private final TaxRepository taxRepository;

    @Override
    public TaxDto createTax(TaxDto taxDto) {
        log.info("TaxService.createTax start taxDto = {}", taxDto);
        final Tax tax = taxMapper.taxDtoToTax(taxDto);
        tax.setTaxId(CommonUtils.getUUID(tax.getTaxId()));
        final TaxDto taxDt = taxMapper.taxToTaxDto(taxRepository.save(tax));
        log.info("TaxService.createTax start taxDt = {}", taxDt);
        return taxDt;
    }
}
