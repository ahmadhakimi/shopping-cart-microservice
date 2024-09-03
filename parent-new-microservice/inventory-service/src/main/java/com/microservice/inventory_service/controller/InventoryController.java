package com.microservice.inventory_service.controller;

import com.microservice.inventory_service.dto.InventoryResponseDto;
import com.microservice.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/inventory")
@Slf4j
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponseDto> isInStock(@RequestParam List<String> skuCode) {
        log.info("The product is indeed in stock");
        return inventoryService.isInStock(skuCode);
    }
}
