package com.example.mobileapi.repository;

import com.example.mobileapi.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByCartId(int cartId);
    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.product.id = :productId")
    CartItem findByCartIdAndProductId(@Param("cartId") int cartId, @Param("productId") int productId);
}
