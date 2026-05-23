package com.example.shop.product.Controller;

import com.example.shop.product.DTO.ProductDTO;
import com.example.shop.product.DTO.ProductListDTO;
import com.example.shop.product.Service.ProductServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@Tag(name = "상품 컨트롤러", description = "상품 CRUD를 수행")
public class ProductController {

    private final ProductServiceImpl productService;

    @PostMapping
    @Operation(summary = "상품 등록", description = "상품 이름, 가격, 수량을 받아 상품을 등록한다.")
    public ResponseEntity<Void> createProduct(@Valid @RequestBody ProductDTO productDTO) {

        Long productId = productService.createProduct(productDTO);

        // 생성한 상품의 URI 반환
        return ResponseEntity.created(URI.create("/products/"+ productId)).build();
    }

    @GetMapping
    @Operation(summary = "상품 리스트 조회", description = "등록된 모든 상품들의 정보를 반환한다.")
    public ResponseEntity<ProductListDTO> getAllProducts() {
        ProductListDTO products =  productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}")
    @Operation(summary = "개별 상품 상세 조회", description = "Id를 통해 한 상품의 상품 Id, 이름, 가격, 수량을 조회한다.")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long productId) {
        ProductDTO productDTO = productService.getProduct(productId);
        return ResponseEntity.ok(productDTO);
    }

    @PatchMapping("/{productId}")
    @Operation(summary = "상품 정보 수정", description = "Id를 통해 상품의 이름, 가격, 수량을 수정한다.")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long productId, @Valid @RequestBody ProductDTO productDTO) {

        ProductDTO fixedProduct = productService.updateProduct(productId, productDTO);

        return  ResponseEntity.ok(fixedProduct);
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "상품 삭제", description = "Id를 통해 상품을 삭제한다.")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

}
