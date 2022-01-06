package com.krzypio.security;

import com.krzypio.security.models.Customer;
import com.krzypio.security.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@EntityScan(basePackages = "com.krzypio.security")
@Component
public class CustomerRepositoryCommandLineRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(CustomerRepositoryCommandLineRunner.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        Customer customer = new Customer("user@op.pl", "pass", "ROLE_USER");
        Customer savedCustomer = customerRepository.save(customer);
        log.info("New Customer is created: " + savedCustomer);
    }
}
