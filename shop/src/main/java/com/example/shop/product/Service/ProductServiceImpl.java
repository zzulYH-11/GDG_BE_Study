package com.example.shop.product.Service;

import com.example.shop.common.exception.BadRequestException;
import com.example.shop.common.exception.NotFoundException;
import com.example.shop.common.message.ErrorMessage;
import com.example.shop.product.DTO.ProductDTO;
import com.example.shop.product.DTO.ProductListDTO;
import com.example.shop.product.Entity.Product;
import com.example.shop.product.Repository.JpaProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final JpaProductRepository productRepository;

    @Override
    @Transactional
    public Long createProduct(ProductDTO productDTO) {

        //이미 존재하는 상품인지 확인
        Product existingProduct = productRepository.findByName(productDTO.name());
        if(existingProduct != null){
            throw new BadRequestException(ErrorMessage.PRODUCT_ALREADY_EXIST);
        }

        //아니면 등록하고 Id를 반환
        Product product = new Product(productDTO.name(), productDTO.price(), productDTO.quantity());
        productRepository.save(product);
        return product.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductListDTO getAllProducts() {

        List<ProductDTO> productDTOList = productRepository.findAll().stream()
                        .map(ProductDTO::from)
                                .toList();

        return new ProductListDTO((productDTOList));
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO getProduct(Long productId) {

        //실제로 존재하는 product인지 분기처리
        Product product = productRepository.findById(productId);

        if(product == null){
            throw new NotFoundException(ErrorMessage.PRODUCT_NOT_EXIST);
        }

        return ProductDTO.from(product);
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {

        //실제로 존재하는 product인지 분기처리
        Product product = productRepository.findById(productId);

        if(productRepository.findById(productId) == null){
            throw new NotFoundException(ErrorMessage.PRODUCT_NOT_EXIST);
        }

        // 존재하는 상품이면 업데이트 ( 재고는 들어온 값으로 업데이트해준다)
        product.updateInfo(
                productDTO.name(),
                productDTO.price(),
                productDTO.quantity());

        return ProductDTO.from(product);
    }

    @Override
    @Transactional
    public void deleteProduct(Long productId) {
        //실제로 존재하는 product인지 분기처리
        Product product = productRepository.findById(productId);

        if(product == null){
            throw new NotFoundException(ErrorMessage.PRODUCT_NOT_EXIST);
        }
        productRepository.deleteById(productId);
    }
}
