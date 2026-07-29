package com.axlgutierrezl26.demo_api_rest_java.service.impl;

import com.axlgutierrezl26.demo_api_rest_java.entity.Customer;
import com.axlgutierrezl26.demo_api_rest_java.repository.ICustomerRepository;
import com.axlgutierrezl26.demo_api_rest_java.service.ICustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements ICustomerService {

    public final ICustomerRepository customerRepository;

    public CustomerServiceImpl(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getAll() {
        return this.customerRepository.findAll();
    }

    @Override
    public Optional<Customer>  getById(Long id) {
        return this.customerRepository.findById(id);
    }

    @Override
    public Customer save(Customer customer) {

        this.customerRepository.findByEmail(customer.getEmail()).ifPresent(
                cus -> {
                    throw new IllegalArgumentException("Exist Email " + cus.getEmail());
                }
        );

        return this.customerRepository.save(customer);
    }

    @Override
    public Customer update(Long id, Customer customer) {

        Customer customerUpdate = this.customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));


        this.customerRepository.findByEmail(customer.getEmail())
                .ifPresent(
                        cus -> {
                            if (!cus.getId().equals(customerUpdate.getId()))
                                throw new IllegalArgumentException("Exist Email in the customer " + cus.getName());
                        }
                );

        customerUpdate.setName(customer.getName());
        customerUpdate.setEmail(customer.getEmail());

        return this.customerRepository.save(customerUpdate);
    }

    @Override
    public void delete(Long id) {
        if (!this.customerRepository.existsById(id)) throw new IllegalArgumentException("Customer not found");

        this.customerRepository.findById(id)
                .ifPresent(
                        this.customerRepository::delete // cus -> {this.customerRepository.delete(cus)}
                );
    }
}
