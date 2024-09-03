package com.microservice.order_service.controller;

import com.microservice.order_service.dto.OrderRequestDto;
import com.microservice.order_service.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/orders")

@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping()
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CompletableFuture<String> placeOrder(@RequestBody  OrderRequestDto request) {
        return CompletableFuture.supplyAsync(()-> orderService.placeOrder(request));
    }

    public CompletableFuture<String> fallbackMethod(OrderRequestDto requestDto, RuntimeException runtimeException) {
        return CompletableFuture.supplyAsync(()->"Something went wrong with the url...please wait for a while!");
    }
}
