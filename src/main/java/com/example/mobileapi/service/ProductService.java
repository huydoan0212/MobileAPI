package com.example.mobileapi.service;

import com.example.mobileapi.dto.request.ProductRequestDTO;
import com.example.mobileapi.dto.response.ProductResponseDTO;

import java.util.List;

public interface ProductService {
    int saveProduct(ProductRequestDTO productRequestDTO);
    void updateProduct(int id, ProductRequestDTO productRequestDTO);
    void deleteProduct(int id);
    List<ProductResponseDTO> getAllProducts();
    ProductResponseDTO getProductById(int id);
}
