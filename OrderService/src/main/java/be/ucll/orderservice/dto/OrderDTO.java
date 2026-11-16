package be.ucll.orderservice.dto;

import be.ucll.orderservice.persistence.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public record OrderDTO (
        Long id,
        @JsonProperty("customer")
        Customer customer,
        OrderStatus status,
        BigDecimal total,
        List<LimitedMenuItemDTO> items,
        @JsonProperty("restaurant")
        Restaurant restaurant
){
}
