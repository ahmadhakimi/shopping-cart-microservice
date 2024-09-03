package com.microservice.product_service.service;

import com.microservice.product_service.dto.ProductDtoRequest;
import com.microservice.product_service.dto.ProductDtoResponse;
import com.microservice.product_service.entity.ProductEntity;
import com.microservice.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public ProductDtoResponse createProduct (ProductDtoRequest request) {
        ProductEntity product = ProductEntity.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .build();

        ProductEntity save = productRepository.save(product);
        log.info("Created new product: {}", save);

        return mapToDto(save); 
    }

    public List<ProductDtoResponse> allProducts(){
        return productRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public ProductDtoResponse viewProduct(String id) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No such product id: " + id));

        return mapToDto(product);
    }

    private ProductDtoResponse mapToDto(ProductEntity save) {
        return new ProductDtoResponse(save.getId(), save.getName(), save.getDescription(), save.getPrice());
    }

}
