package com.example.mobileapi.service.impl;

import com.example.mobileapi.dto.request.CartItemRequestDTO;
import com.example.mobileapi.dto.response.CartItemResponseDTO;
import com.example.mobileapi.model.CartItem;
import com.example.mobileapi.repository.CartItemRepository;
import com.example.mobileapi.service.CartItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartServiceImpl cartServiceImpl;
    private final ProductServiceImpl productServiceImpl;
    @Override
    public int saveCartItem(CartItemRequestDTO cartItem) {
        CartItem cartIt = CartItem.builder()
                .cart(cartServiceImpl.getByCartId(cartItem.getCartId()))
                .quantity(cartItem.getQuantity())
                .product(productServiceImpl.getById(cartItem.getProductId()))
                .build();
        return cartItemRepository.save(cartIt).getId();
    }

    @Override
    public CartItemResponseDTO getCartItem(int cartItemId) {
        CartItem cartItem = getByCartId(cartItemId);
        return CartItemResponseDTO.builder()
                .id(cartItem.getId())
                .productId(cartItem.getProduct().getId())
                .quantity(cartItem.getQuantity())
                .build();
    }

    @Override
    public void deleteCartItem(int cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    @Override
    public void updateCartItem(int id, CartItemRequestDTO cartItem) {
        CartItem cartI = getByCartId(id);
        cartI.setQuantity(cartItem.getQuantity());
        cartItemRepository.save(cartI);
    }
    public CartItem getByCartId(int cartItemId) {
        return cartItemRepository.findById(cartItemId).orElse(null);
    }
    public List<CartItemResponseDTO> getCartItemsByCartId(int cartId) {
        List<CartItem> cartItems = cartItemRepository.findByCartId(cartId);
        return cartItems.stream().map(cartItem -> CartItemResponseDTO.builder()
                .id(cartItem.getId())
                .productId(cartItem.getProduct().getId())
                .quantity(cartItem.getQuantity())
                .build()).collect(Collectors.toList());
    }
}
