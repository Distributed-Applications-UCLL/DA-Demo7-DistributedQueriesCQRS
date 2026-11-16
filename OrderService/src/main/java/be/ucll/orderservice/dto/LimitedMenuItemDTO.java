package be.ucll.orderservice.dto;

import java.math.BigDecimal;

public record LimitedMenuItemDTO(
        Long id,
        String name,
        BigDecimal price,
        int quantity
){
}
