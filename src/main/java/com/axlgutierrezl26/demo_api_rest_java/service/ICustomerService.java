package com.axlgutierrezl26.demo_api_rest_java.service;

import com.axlgutierrezl26.demo_api_rest_java.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface ICustomerService {

    List<Customer> getAll();

    Optional<Customer> getById(Long id);

    Customer save(Customer customer);

    Customer update(Long id, Customer customer);

    void delete(Long id);


}
