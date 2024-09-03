package com.microservice.order_service.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class InventoryResponseDto {
    private String skuCode;
    private boolean isInStock;
    private Integer quantity;
}
