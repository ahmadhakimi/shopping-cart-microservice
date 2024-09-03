package com.microservice.order_service.dto;

import java.math.BigDecimal;

public record OrderLineItemsDto(
        Long id,
         String skuCode,
         BigDecimal price,
         Integer quantity) {
}
