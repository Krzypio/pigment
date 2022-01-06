package com.krzypio.security.repository;


import com.krzypio.security.models.Customer;
import com.krzypio.security.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
}
