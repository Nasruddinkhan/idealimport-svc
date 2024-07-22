package ca.com.idealimport.service.customer.entity.dto;

import ca.com.idealimport.common.dto.DropDownDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;


@Builder
public record CustomerDto(@JsonProperty("customer_id") Long customerId,
                          @JsonProperty("customer_name") String customerName,
                          @JsonProperty("company_name") String companyName,
                          @JsonProperty("email") String email,
                          @JsonProperty("fax") String faxNo,
                          @JsonProperty("phone_no") String phoneNo,
                          @JsonProperty("parties") List<DropDownDto> parties,
                          @JsonProperty("balance") BigDecimal balance,
                          @JsonProperty("address") String address,
                          @JsonProperty("remark") String remarks) {
}
