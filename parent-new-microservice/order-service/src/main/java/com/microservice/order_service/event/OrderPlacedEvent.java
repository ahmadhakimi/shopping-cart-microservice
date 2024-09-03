package com.microservice.order_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class OrderPlacedEvent {
    private String orderNumber;
    private List<OrderLineItem> orderLineItemList;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderLineItem {
        private Long id;
        private String skuCode;
        private BigDecimal price;
        private Integer quantity;
    }
}
