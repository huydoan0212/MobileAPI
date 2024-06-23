package com.example.mobileapi.service;

import com.example.mobileapi.dto.request.ProductRequestDTO;
import com.example.mobileapi.dto.response.ProductResponseDTO;
import com.example.mobileapi.model.Product;

import java.util.List;

public interface ProductService {
    int saveProduct(ProductRequestDTO productRequestDTO);
    void updateProduct(int id, ProductRequestDTO productRequestDTO);
    void deleteProduct(int id);
    List<ProductResponseDTO> getAllProducts();
    ProductResponseDTO getProductById(int id);
    List<ProductResponseDTO> findByCategoryId(Integer categoryId);
    List<ProductResponseDTO> findByNameContainingIgnoreCase(String name);


}
