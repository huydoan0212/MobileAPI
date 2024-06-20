package com.example.mobileapi.controller;

import com.example.mobileapi.dto.request.CartItemRequestDTO;
import com.example.mobileapi.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cartItem")
@RequiredArgsConstructor
public class CartItemController {
    private final CartItemService cartItemService;
    @PostMapping
    public int saveCartItem(@RequestBody CartItemRequestDTO cartItemRequestDTO){
        return cartItemService.saveCartItem(cartItemRequestDTO);
    }
}
