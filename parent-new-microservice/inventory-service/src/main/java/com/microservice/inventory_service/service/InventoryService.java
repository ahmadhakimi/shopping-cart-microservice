package com.microservice.inventory_service.service;

import com.microservice.inventory_service.dto.InventoryResponseDto;
import com.microservice.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor

public class InventoryService {

    private final InventoryRepository inventoryRepository;
    @Transactional(readOnly = true)
    @SneakyThrows
    public List<InventoryResponseDto> isInStock(List<String> skuCode) {
        return inventoryRepository.findBySkuCodeIn(skuCode)
                .stream()
                .map(inventory -> InventoryResponseDto.builder()
                        .skuCode(inventory.getSkuCode())
                        .quantity(inventory.getQuantity())
                        .isInStock(inventory.getQuantity() > 0)
                        .build())
                .toList();
    }


}
