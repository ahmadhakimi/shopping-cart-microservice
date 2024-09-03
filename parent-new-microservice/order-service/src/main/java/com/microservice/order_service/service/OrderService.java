package com.microservice.order_service.service;

import com.microservice.order_service.dto.InventoryResponseDto;
import com.microservice.order_service.dto.OrderLineItemsDto;
import com.microservice.order_service.dto.OrderRequestDto;
import com.microservice.order_service.entity.OrderEntity;
import com.microservice.order_service.entity.OrderLineItems;
import com.microservice.order_service.event.OrderPlacedEvent;
import com.microservice.order_service.repository.OrderRepository;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final Tracer tracer;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public String placeOrder(OrderRequestDto request) {
        OrderEntity order = new OrderEntity();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = request.getOrderLineItemsDtoList()
                .stream().map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemsList()
                .stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        try {
            // Request inventory check for SKU Codes
            System.out.println("Requesting inventory check for SKU Codes: " + skuCodes);

            Span inventoryServiceLookup = tracer.nextSpan().name("InventoryServiceLookup");

            try(Tracer.SpanInScope inScope = tracer.withSpan(inventoryServiceLookup.start())){
                // Fetch inventory details (including available quantity)
                InventoryResponseDto[] inventoryResponseArray = webClientBuilder.build().get()
                        .uri("http://inventory-service/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                        .retrieve()
                        .bodyToMono(InventoryResponseDto[].class)
                        .doOnError(e -> log.error("Error fetching inventory details", e))
                        .block();
                if (inventoryResponseArray == null || inventoryResponseArray.length == 0) {
                    throw new IllegalStateException("Inventory response is empty or null.");
                }

                log.info("Received inventory response: {}", Arrays.toString(inventoryResponseArray));

                // Check if all products are in stock and if requested quantity is available
                boolean allProductsInStock = order.getOrderLineItemsList().stream().allMatch(orderLineItem -> {
                    InventoryResponseDto inventoryResponse = Arrays.stream(inventoryResponseArray)
                            .filter(response -> response.getSkuCode().equals(orderLineItem.getSkuCode()))
                            .findFirst()
                            .orElseThrow(null);

                    if(inventoryResponse == null) {
                        throw new IllegalArgumentException("SKU Code not found: " + orderLineItem.getSkuCode());
                    }

                    // Validate if enough quantity is available
                    if (orderLineItem.getQuantity() > inventoryResponse.getQuantity()) {
                        log.error("Not enough stock for SKU: {}. Requested: {}, Available: {}",
                                orderLineItem.getSkuCode(), orderLineItem.getQuantity(), inventoryResponse.getQuantity());
                        throw new IllegalArgumentException("Not enough stock for SKU: " + orderLineItem.getSkuCode() +
                                ". Requested: " + orderLineItem.getQuantity() + ", Available: " + inventoryResponse.getQuantity());
                    }

                    return inventoryResponse.isInStock();
                });

                // Place the order if all products are in stock
                if (allProductsInStock) {

                    OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent(
                            order.getOrderNumber(),
                            order.getOrderLineItemsList().stream()
                                    .map(lineItem -> new OrderPlacedEvent.OrderLineItem(
                                            lineItem.getId(),
                                            lineItem.getSkuCode(),
                                            lineItem.getPrice(),
                                            lineItem.getQuantity()))
                                    .toList()
                    );

//                    give notification by send kafka message
                    kafkaTemplate.send("notificationTopic", orderPlacedEvent);

                    orderRepository.save(order);
                    return "Order placed Successfully";
                } else {
                    throw new IllegalArgumentException("Product not in stock, please try again later");
                }
            } finally {
                inventoryServiceLookup.end();
            }


        } catch (Exception e) {
            throw new IllegalStateException("Error communicating with Inventory Service", e);
        }

    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.price());
        orderLineItems.setQuantity(orderLineItemsDto.quantity());
        orderLineItems.setSkuCode(orderLineItemsDto.skuCode());
        return orderLineItems;
    }
}
