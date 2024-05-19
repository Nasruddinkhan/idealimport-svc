package ca.com.idealimport.service.tax.service;

import ca.com.idealimport.common.dto.TaxDto;
import ca.com.idealimport.service.tax.entity.Tax;

import java.util.List;


public interface TaxService {
     TaxDto createTax(TaxDto taxDto);
     TaxDto findTaxById(String taxId);

     List<TaxDto> findAllTaxes();

     void deleteTaxByTaxId(String taxId);

    Tax findTax(String taxId);
}
