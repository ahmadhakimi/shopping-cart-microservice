package com.microservice.product_service.dto;

import java.math.BigDecimal;

public record ProductDtoRequest(String name, String description, BigDecimal price) {
}
