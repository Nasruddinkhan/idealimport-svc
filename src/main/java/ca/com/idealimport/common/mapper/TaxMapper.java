package ca.com.idealimport.common.mapper;

import ca.com.idealimport.common.dto.TaxDto;
import ca.com.idealimport.service.tax.entity.Tax;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface TaxMapper {

    Tax taxDtoToTax(TaxDto taxDto);

    @Mapping(source = "taxRate1", target = "taxRate1", numberFormat = "$#.0000")
    TaxDto taxToTaxDto(Tax tax);

    List<TaxDto> taxToTaxDto(List<Tax> taxes);
}
