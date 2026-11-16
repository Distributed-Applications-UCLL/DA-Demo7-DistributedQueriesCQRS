package be.ucll.orderhistoryservice.dto;

import java.math.BigDecimal;

public record LimitedMenuItemDTO(
        Long id,
        String name,
        BigDecimal price,
        int quantity
){
}
