package com.example.mobileapi.repository;

import com.example.mobileapi.dto.response.CustomerResponseDTO;
import com.example.mobileapi.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Customer u WHERE u.username = :username")
    boolean existsByUsername(@Param("username") String username);

    @Query("SELECT u  from Customer u WHERE u.username = :username and u.password= :password ")
    Customer login(@Param("username") String username, @Param("password") String password);

    Customer findByUsername(String username);


}
