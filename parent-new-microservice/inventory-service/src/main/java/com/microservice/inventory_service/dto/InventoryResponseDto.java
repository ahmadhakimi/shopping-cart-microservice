package com.microservice.inventory_service.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InventoryResponseDto {
    private String skuCode;
    private boolean isInStock;
    private Integer quantity;
}
