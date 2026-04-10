package com.example.shop.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    //상품 등록 -> Post , /products
    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody productCreateRequest request) {
            Long productId = productService.createProduct(request);
        return ResponseEntity.created(URI.create("/products/"+ productId)).build();
    }

    //상품 리스트 조회 -> Get , /products
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    //개별 상품 상세 조회 -> Get , /products{productsId}
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Long productId) {
        Product product = productService.getProduct(productId);
        return ResponseEntity.ok(product);
    }

    //상품 정보 수정 -> Patch , /Product/{productsID}
    @PatchMapping("/{productId}")
    public ResponseEntity<Void> updateProduct(@PathVariable Long productId, @RequestBody productUpdateRequest request) {
        productService.updateProduct(productId, request);
        return  ResponseEntity.ok().build();
    }

    //상품 삭제 -> Delete , /products/{productsID}
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

}
