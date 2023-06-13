package ca.com.idealimport.service.customer.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.math.BigDecimal;

public record CustomerDto(@JsonProperty("customer_id") Long customerId,
                          @JsonProperty("customer_name") String customerName,
                          @JsonProperty("company_name") String companyName,
                          @JsonProperty("email") String email,
                          @JsonProperty("fax") String faxNo,
                          @JsonProperty("phone_no") String phoneNo,
                          @JsonProperty("balance") BigDecimal balance,
                          @JsonProperty("address") String address,
                          @JsonProperty("remark") String remarks) {
    @Builder
    public CustomerDto{
        throw new UnsupportedOperationException();
    }
}
