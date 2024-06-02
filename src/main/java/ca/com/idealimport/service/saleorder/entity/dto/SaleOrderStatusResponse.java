package ca.com.idealimport.service.saleorder.entity.dto;

import ca.com.idealimport.common.dto.AuditDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaleOrderStatusResponse {
    private SaleOrderStatusDto statusDto;
    private AuditDto auditDto;
}
