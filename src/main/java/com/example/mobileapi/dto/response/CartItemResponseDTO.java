package com.example.mobileapi.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CartItemResponseDTO {
    private int id;
    private int productId;
    private int quantity;
}
