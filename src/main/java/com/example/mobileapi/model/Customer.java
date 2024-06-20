package com.example.mobileapi.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Table(name = "customers")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, name = "id")
    Integer id;

    @Column(nullable = false, name = "fullname")
    String fullname;

    @Column(nullable = false, name = "username")
    String username;

    @Column(unique = true, length = 100, nullable = false, name = "email")
    String email;

    @Column(nullable = false, name = "password")
    String password;

    @Column(nullable = false, name = "number_phone")
    String phone;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    Set<Cart> carts = new HashSet<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    Set<Order> orders = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        return id != null ? id.equals(customer.id) : customer.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}

