package ait.shop.controller;

import ait.shop.model.entity.Customer;
import ait.shop.model.entity.Product;
import ait.shop.service.interfaces.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
        return customerService.saveCustomer(customer);
    }

    @GetMapping("/{id}")
    public Customer getById(@PathVariable Long id) {
        return customerService.getActiveCustomerById(id);
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllActiveCustomers();
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer);
    }

    @DeleteMapping("/{customerId}")
    public Customer deleteCustomerById(@PathVariable("customerId") Long id) {
        return customerService.deleteCustomerById(id);
    }

    @DeleteMapping("/name")
    public Customer deleteCustomerByName(@RequestParam String name) {
        return customerService.deleteCustomerByName(name);
    }

    @PutMapping("/restore/{id}")
    public Customer restoreCustomer(@PathVariable Long id) {
        return customerService.restoreCustomerById(id);
    }

    @GetMapping("/active-count")
    public long getActiveCountCustomer() {
        return customerService.getAllActiveCustomerCount();
    }

    @GetMapping("/total-bucket-price/{id}")
    public BigDecimal getTotalBucketPrice(@PathVariable Long id) {
        return customerService.getTotalBucketPriceFromActiveCustomers(id);
    }

    @GetMapping("/average-bucket-price/{id}")
    public BigDecimal getAverageBucketPrice(@PathVariable Long id) {
        return customerService.getAverageBucketPriceFromActiveCustomers(id);
    }

    @PutMapping("/restore-bucket/{customerId}/{productId}")
    public Product addProductInCustomerBucket(@PathVariable Long customerId, @PathVariable Long productId) {
        return customerService.addProductInCustomersBucketIfActive(customerId, productId);
    }

    @DeleteMapping("/from-customer-bucket/{customerId}/{productId}")
    public Product deleteProductFromCustomerBucket(@PathVariable Long customerId, @PathVariable Long productId) {
        return customerService.deleteProductFromCustomersBucket(customerId, productId);
    }

    @DeleteMapping("/products-from-customer-bucket/{customerId}")
    public List<Product> deleteProductsFromCustomerBucket(@PathVariable Long customerId) {
        return customerService.deleteAllProductsFromActiveCustomersBucket(customerId);
    }
}
