package sn.isi.l3gl.api.productapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.isi.l3gl.core.productcore.entity.Product;
import sn.isi.l3gl.core.productcore.services.ProductService;


import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor

public class ProductController {
    private final ProductService productService;


    // POST /api/products
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.createProduct(product));
    }

    // GET /api/products
    @GetMapping
    public ResponseEntity<List<Product>> listProducts() {
        return ResponseEntity.ok(productService.listProducts());
    }

    // PUT /api/products/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateQuantity(
            @PathVariable Long id,
            @RequestParam Integer quantity) {
        return ResponseEntity.ok(productService.updateQuantity(id, quantity));
    }

    // GET /api/products/low-stock/count
    @GetMapping("/low-stock/count")
    public ResponseEntity<Long> countLowStock() {
        return ResponseEntity.ok(productService.countLowStockProducts());
    }
}
