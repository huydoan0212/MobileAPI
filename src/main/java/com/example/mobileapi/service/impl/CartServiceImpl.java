package com.example.mobileapi.service.impl;

import com.example.mobileapi.dto.request.CartRequestDTO;
import com.example.mobileapi.dto.response.CartItemResponseDTO;
import com.example.mobileapi.dto.response.CartResponseDTO;
import com.example.mobileapi.model.Cart;
import com.example.mobileapi.model.CartItem;
import com.example.mobileapi.repository.CartRepository;
import com.example.mobileapi.repository.CustomerRepository;
import com.example.mobileapi.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CustomerServiceImpl customerServiceImpl;
    private final ProductServiceImpl productServiceImpl;
    @Override
    public int saveCart(CartRequestDTO cartRequestDTO) {
        Cart cart = Cart.builder()
                .customer(customerServiceImpl.getCustomerById(cartRequestDTO.getCustomerId()))
                .build();
        return cartRepository.save(cart).getId();
    }

    @Override
    public CartResponseDTO getCart(int cartId) {
        Cart cart = getByCartId(cartId);
        if (cart == null) {
            return null; // Or throw an exception if preferred
        }

        List<CartItemResponseDTO> cartItemResponseDTOs = cart.getCartItems().stream()
                .map(this::convertToCartItemResponseDTO)
                .collect(Collectors.toList());

        return CartResponseDTO.builder()
                .id(cart.getId())
                .customerId(cart.getCustomer().getId())
                .cartItems(cartItemResponseDTOs)
                .build();
    }

    public Cart getByCartId(int cartId) {
        return cartRepository.findById(cartId).orElse(null);
    }

    private CartItemResponseDTO convertToCartItemResponseDTO(CartItem cartItem) {
        return CartItemResponseDTO.builder()
                .id(cartItem.getId())
                .productResponseDTO(productServiceImpl.getProductById(cartItem.getProduct().getId()))
                .quantity(cartItem.getQuantity())
                .build();
    }
    @Override
    public CartResponseDTO getCartByCustomerId(int customerId) {
        Cart cart = getByCustomerId(customerId);
        if (cart == null) {
            return null; // Or throw an exception if preferred
        }

        List<CartItemResponseDTO> cartItemResponseDTOs = cart.getCartItems().stream()
                .map(this::convertToCartItemResponseDTO)
                .collect(Collectors.toList());

        return CartResponseDTO.builder()
                .id(cart.getId())
                .customerId(cart.getCustomer().getId())
                .cartItems(cartItemResponseDTOs)
                .build();
    }
    public Cart getByCustomerId(int customerId) {
        return cartRepository.findByCustomerId(customerId).orElse(null);
    }


}
