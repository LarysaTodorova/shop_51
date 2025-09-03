package ait.shop.service;

import ait.shop.model.entity.Customer;
import ait.shop.model.entity.Product;
import ait.shop.service.interfaces.CustomerService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Override
    public Customer saveCustomer(Customer customer) {
        return null;
    }

    @Override
    public List<Customer> getAllActiveCustomers() {
        return List.of();
    }

    @Override
    public Customer getCustomerById(Long id) {
        return null;
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        return null;
    }

    @Override
    public Customer deleteCustomerById(Long id) {
        return null;
    }

    @Override
    public Customer deleteCustomerByName(String name) {
        return null;
    }

    @Override
    public Customer restoreCustomerById(Long id) {
        return null;
    }

    @Override
    public long getAllActiveCustomerCount() {
        return 0;
    }

    @Override
    public BigDecimal getTotalBucketPriceFromActiveCustomers(Long customerId) {
        return null;
    }

    @Override
    public BigDecimal getAverageBucketPriceFromActiveCustomers(Long customerId) {
        return null;
    }

    @Override
    public Product addProductInCustomersBucketIfActive(Long customerId, Long productId) {
        return null;
    }

    @Override
    public Product deleteProductFromCustomersBucket(Long customerId, Long productId) {
        return null;
    }

    @Override
    public void deleteAllProductsFromActiveCustomersBucket(Long customerId) {

    }
}
