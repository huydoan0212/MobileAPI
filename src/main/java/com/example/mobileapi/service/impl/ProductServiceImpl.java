package com.example.mobileapi.service.impl;

import com.example.mobileapi.dto.request.ProductRequestDTO;
import com.example.mobileapi.dto.response.ProductResponseDTO;
import com.example.mobileapi.model.Category;
import com.example.mobileapi.model.Product;
import com.example.mobileapi.repository.ProductRepository;
import com.example.mobileapi.service.CategoryService;
import com.example.mobileapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryServiceImpl categoryService;

    @Override
    public int saveProduct(ProductRequestDTO productRequestDTO) {
        Product product = Product.builder()
                .name(productRequestDTO.getName())
                .price(productRequestDTO.getPrice())
                .img(productRequestDTO.getImg())
                .category(categoryService.getById(productRequestDTO.getCategoryId()))
                .build();
        return productRepository.save(product).getId();
    }

    @Override
    public void updateProduct(int id, ProductRequestDTO productRequestDTO) {
        Product product = getById(id);
        product.setName(productRequestDTO.getName());
        product.setPrice(productRequestDTO.getPrice());
        product.setImg(productRequestDTO.getImg());
        product.setCategory(categoryService.getById(productRequestDTO.getCategoryId()));
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductResponseDTO> productResponseDTOS = new ArrayList<>();
        for (Product product : products) {
            productResponseDTOS.add(ProductResponseDTO.builder()
                    .categoryName(product.getCategory().getName())
                    .price(product.getPrice())
                    .name(product.getName())
                    .img(product.getImg())
                    .id(product.getId())
                    .build());
        }
        return productResponseDTOS;
    }

    @Override
    public ProductResponseDTO getProductById(int id) {
        Product product = getById(id);
        return ProductResponseDTO.builder()
                .categoryName(product.getCategory().getName())
                .price(product.getPrice())
                .name(product.getName())
                .img(product.getImg())
                .id(product.getId())
                .detail(product.getDetail())
                .build();
    }

    @Override
    public List<ProductResponseDTO> findByCategoryId(Integer categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        List<ProductResponseDTO> productResponseDTOS = new ArrayList<>();
        for (Product product : products) {
            productResponseDTOS.add(ProductResponseDTO.builder()
                    .categoryName(product.getCategory().getName())
                    .price(product.getPrice())
                    .name(product.getName())
                    .img(product.getImg())
                    .id(product.getId())
                    .build());
        }
        return productResponseDTOS;
    }

    @Override
    public List<ProductResponseDTO> findByNameContainingIgnoreCase(String name) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
        List<ProductResponseDTO> productResponseDTOS = new ArrayList<>();
        for (Product product : products) {
            productResponseDTOS.add(ProductResponseDTO.builder()
                    .categoryName(product.getCategory().getName())
                    .price(product.getPrice())
                    .name(product.getName())
                    .img(product.getImg())
                    .id(product.getId())
                    .build());
        }
        return productResponseDTOS;
    }

    public Product getById(int id) {
        return productRepository.findById(id).orElse(null);
    }
}
