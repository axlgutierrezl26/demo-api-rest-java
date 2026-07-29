package com.axlgutierrezl26.demo_api_rest_java.controller;

import com.axlgutierrezl26.demo_api_rest_java.entity.Customer;
import com.axlgutierrezl26.demo_api_rest_java.service.ICustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final ICustomerService iCustomerService;

    public CustomerController(ICustomerService iCustomerService) {
        this.iCustomerService = iCustomerService;
    }

    @GetMapping
    public List<Customer> getAll() {
        return this.iCustomerService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getById(@PathVariable Long id) {
        return this.iCustomerService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Customer> save(@Valid @RequestBody Customer customer) {
        Customer customerCreated = this.iCustomerService.save(customer);
        return ResponseEntity.created(URI.create("/api/customers/" + customer.getId()))
                .body(customerCreated);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable Long id, @Valid @RequestBody Customer customer) {
        try {
            Customer customerUpdated = this.iCustomerService.update(id, customer);
            return ResponseEntity.ok(customerUpdated);

        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            this.iCustomerService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }


}
