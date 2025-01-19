package com.debzium.debzium_master.service;


import com.debzium.debzium_master.entity.Customer;
import com.debzium.debzium_master.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.debezium.data.Envelope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(new Customer());
    }

    public Customer createCustomer(Customer customer) {
        // You can add additional validation or business logic here
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        // You can add additional validation or business logic here
        if (customerRepository.existsById(id)) {
            updatedCustomer.setCustomerId(id);
            return customerRepository.save(updatedCustomer);
        }
        return new Customer(); // or throw an exception indicating not found
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}