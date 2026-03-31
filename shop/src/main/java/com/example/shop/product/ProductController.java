package com.example.shop.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class ProductController {

    private final ProductService productService;

    // 상품(Item)에 들어갈 정보 - 상품명(productName), 가격(price), 재고량(stock)

    //상품 등록 -> Post , /items
    @PostMapping
    public ResponseEntity<Void> createItem(@RequestBody ItemCreateRequest request) {
            Long productId = productService.createItem(request);
        return ResponseEntity.created(URI.create("/products/"+ productId)).build();
    }

    //상품 리스트 조회 -> Get , /items
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    //개별 상품 상세 조회 -> Get , /items{itemsId}
    @GetMapping("/{itemId}")
    public ResponseEntity<Product> getProduct(@PathVariable Long itemId) {
        Product product = productService.getProduct(itemId);
        return ResponseEntity.ok(product);
    }

    //상품 정보 수정 -> Patch , /items/{itemsID}
    @PatchMapping("/{itemId}")
    public ResponseEntity<Void> updateItem(@PathVariable Long itemId, @RequestBody ItemUpdateRequest request) {
        productService.updateItem(itemId, request);
        return  ResponseEntity.ok().build();
    }

    //상품 삭제 -> Delete , /items/{itemsID}
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId) {
        productService.deleteItem(itemId);
        return ResponseEntity.noContent().build();
    }

}
