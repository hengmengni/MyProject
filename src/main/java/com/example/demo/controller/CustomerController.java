package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CustomerResponse;
import com.example.demo.dto.ProductResponse;
import com.example.demo.model.Customer;
import com.example.demo.model.Product;
import com.example.demo.service.CustomerService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("customers")
    public ResponseEntity<List<CustomerResponse>> getCustomer() {
        final var getCustomers = customerService.getListCostomer();
        final var productresponse = getCustomers.stream().map(c -> new CustomerResponse(c.getId(), c.getName(),
                c.getSex(), c.getPhone(), convertCategoryResponses(c))).collect(Collectors.toList());
        return new ResponseEntity<>(productresponse, HttpStatus.OK);
    }

    @PostMapping("customers")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody Customer customer) {
        final var createCustomer = customerService.createCustomer(customer);
        final var response = new CustomerResponse(createCustomer.getId(), createCustomer.getName(),
                createCustomer.getSex(), createCustomer.getPhone(), convertCategoryResponses(createCustomer));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("customers/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> putCustomer(@PathVariable Integer id, @RequestBody Customer customer) {

        return new ResponseEntity<>(customerService.updateCustomer(id, customer), HttpStatus.OK);
    }

    @DeleteMapping("customers/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> deleteCustomer(@PathVariable Integer id) {
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    List<ProductResponse> convertCategoryResponses(Customer customer) {
        return customer
                .getProducts()
                .stream()
                .map(p -> new ProductResponse(p.getId(), p.getName(), p.getPrice(), p.getQty()))
                .collect(Collectors.toList());
    }

}
