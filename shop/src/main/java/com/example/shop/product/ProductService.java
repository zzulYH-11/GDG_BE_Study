package com.example.shop.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    //@Transactional
    public Long createProduct(ProductCreateRequest request) {

        //이미 존재하는 상품인지 확인
        Product existingProduct = productRepository.findByName(request.getProductName());
        if(existingProduct != null){
            throw new RuntimeException ("이미 존재하는 상품입니다." + request.getProductName());
        }

        //아니면 등록
        Product product = new Product(request.getProductName(), request.getPrice(), request.getStock());
        productRepository.save(product);
        return product.getId();
    }

    //@Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    //@Transactional(readOnly = true)
    public Product getProduct(Long productId) {

        //실제로 존재하는 product인지 분기처리
        Product product = productRepository.findById(productId);

        if(product == null){
            throw new RuntimeException ("상품을 찾을 수 없습니다.");
        }

        return product;
    }

    //@Transactional
    public void updateProduct(Long productId, productUpdateRequest request) {

        //실제로 존재하는 product인지 분기처리
        Product product = productRepository.findById(productId);

        if(product == null){
            throw new RuntimeException ("상품을 찾을 수 없습니다.");
        }

        product.updateInfo(request.getProductName(), request.getPrice(), request.getStock());
    }

    //@Transactional
    public void deleteProduct(Long productId) {
        //실제로 존재하는 product인지 분기처리
        Product product = productRepository.findById(productId);

        if(product == null){
            throw new RuntimeException ("상품을 찾을 수 없습니다.");
        }
        productRepository.deleteById(productId);
    }
}
