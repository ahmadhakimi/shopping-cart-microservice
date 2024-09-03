package com.microservice.product_service.controller;

import com.microservice.product_service.dto.ProductDtoRequest;
import com.microservice.product_service.dto.ProductDtoResponse;
import com.microservice.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/products")

public class ProductController {
    private final ProductService productService;

    @PostMapping()
//    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductDtoResponse> createProduct (@RequestBody ProductDtoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(request));
    }

    public ResponseEntity<ProductDtoResponse> viewProduct (@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.viewProduct(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductDtoResponse>> listProduct () {
        return ResponseEntity.status(HttpStatus.OK).body(productService.allProducts());
    }
}
