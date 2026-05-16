package com.example.shop.product.Service;

import com.example.shop.product.DTO.Product;
import com.example.shop.product.Repository.JpaProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final JpaProductRepository productRepository;

    @Override
    @Transactional
    public Long createProduct(Map<String, Object> params) {

        //Map에서 값 꺼내기
        String name = (String) params.get("name");
        int quantity = (int) params.get("quantity");
        int price = (int) params.get("price");

        //이미 존재하는 상품인지 확인
        Product existingProduct = productRepository.findByName(name);
        if(existingProduct != null){
            throw new RuntimeException (name + "은/는 이미 존재하는 상품입니다.");
        }

        //아니면 등록하고 Id를 반환
        Product product = new Product(name, price, quantity);
        productRepository.save(product);
        return product.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProduct(Long productId) {

        //실제로 존재하는 product인지 분기처리
        Product product = productRepository.findById(productId);

        if(product == null){
            throw new RuntimeException ("상품을 찾을 수 없습니다.");
        }

        return product;
    }

    @Override
    @Transactional
    public Product updateProduct(Long productId, Map<String, Object> params) {

        //실제로 존재하는 product인지 분기처리
        Product product = productRepository.findById(productId);

        if(product == null){
            throw new RuntimeException ("존재하지 않는 상품입니다.");
        }


        // 존재하는 상품이면 업데이트 ( 재고는 들어온 값으로 업데이트해준다)
        product.updateInfo(
                (String)params.get("name"),
                (int)params.get("price"),
                (int)params.get("quantity"));

        productRepository.save(product);

        return product;
    }

    @Override
    @Transactional
    public void deleteProduct(Long productId) {
        //실제로 존재하는 product인지 분기처리
        Product product = productRepository.findById(productId);

        if(product == null){
            throw new RuntimeException ("상품을 찾을 수 없습니다.");
        }
        productRepository.deleteById(productId);
    }
}
