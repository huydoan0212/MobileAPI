package com.example.mobileapi.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductResponseDTO {
    private int id;
    private String name;
    private String img;
    private double price;
    private String categoryName;
    private String detail;
}
