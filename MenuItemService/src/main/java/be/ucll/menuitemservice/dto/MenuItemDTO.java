package be.ucll.menuitemservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record MenuItemDTO (
        Long id,
        String name,
        BigDecimal price,
        @JsonProperty("restaurant")
        Restaurant Restaurant){
}
