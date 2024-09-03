package com.microservice.order_service.dto;

import com.microservice.order_service.entity.OrderLineItems;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {
    List<OrderLineItemsDto> orderLineItemsDtoList;
}
