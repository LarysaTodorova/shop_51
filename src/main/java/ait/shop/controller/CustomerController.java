package ait.shop.controller;

import ait.shop.model.entity.Customer;
import ait.shop.service.interfaces.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    public final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public Customer saveCustomer(@RequestBody Customer customer) {
        return customer;
    }

    @GetMapping("/{id}")
    public Customer getById(@PathVariable Long id) {
        return null;
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return List.of();
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        return new Customer();
    }

    @DeleteMapping("/{customerId}")
    public Customer remove(@PathVariable("customerId") Long id) {
        return null;
    }
}
