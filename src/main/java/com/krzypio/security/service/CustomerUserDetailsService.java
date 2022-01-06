package com.krzypio.security.service;

import com.krzypio.security.models.Customer;
import com.krzypio.security.models.CustomerUserDetails;
import com.krzypio.security.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Primary
@Service
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> customer = customerRepository.findByEmail(username);

        customer.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));

        return customer.map(CustomerUserDetails::new).get();
    }
}
