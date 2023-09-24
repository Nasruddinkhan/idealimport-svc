package ca.com.idealimport.common.mapper;

import ca.com.idealimport.common.dto.TaxDto;
import ca.com.idealimport.service.tax.entity.Tax;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface TaxMapper {

    Tax taxDtoToTax(TaxDto taxDto);

    TaxDto taxToTaxDto(Tax tax);
}
