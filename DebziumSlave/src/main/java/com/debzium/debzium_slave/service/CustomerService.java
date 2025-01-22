package com.debzium.debzium_slave.service;

import com.debzium.debzium_slave.entity.Customer;
import com.debzium.debzium_slave.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.debezium.data.Envelope;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CustomerService {
    private static final Logger log = LogManager.getLogger(CustomerService.class);
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

    public void replicateData(Map<String, Object> customerData, Envelope.Operation operation) {
        final ObjectMapper mapper = new ObjectMapper();
        final Customer customer = mapToCustomer(customerData);
       log.info(customer);
        if (Envelope.Operation.DELETE == operation) {
           log.info("delete the record");
            customerRepository.deleteById(customer.getCustomerId());
        } else {
           log.info("inserting the record");
            customerRepository.save(customer);
        }
    }

    public static Customer mapToCustomer(Map<String, Object> map) {
        Customer customer = new Customer();

        if (map.containsKey("customer_id") && map.get("customer_id") instanceof Number) {
            customer.setCustomerId(((Number) map.get("customer_id")).longValue());
        }

        if (map.containsKey("customer_name") && map.get("customer_name") instanceof String) {
            customer.setCustomerName((String) map.get("customer_name"));
        }

        if (map.containsKey("city") && map.get("city") instanceof String) {
            customer.setCity((String) map.get("city"));
        }

        if (map.containsKey("country") && map.get("country") instanceof String) {
            customer.setCountry((String) map.get("country"));
        }

        return customer;
    }
}
