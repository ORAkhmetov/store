package ru.practicum.store.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.store.dto.CreateProductDto;
import ru.practicum.store.service.ProductService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/import-product")
public class ImportProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Void> importProduct(@RequestBody List<CreateProductDto> dto) {
        dto.forEach(productService::create);
        return ResponseEntity.ok().build();
    }
}
