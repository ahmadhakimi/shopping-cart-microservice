package com.microservice.product_service.dto;

import java.math.BigDecimal;

public record ProductDtoResponse(String id, String name, String description, BigDecimal price) {
}
