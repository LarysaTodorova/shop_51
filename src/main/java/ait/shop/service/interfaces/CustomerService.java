package ait.shop.service.interfaces;

import ait.shop.model.entity.Customer;
import ait.shop.model.entity.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerService {

    Customer saveCustomer(Customer customer);

    List<Customer> getAllActiveCustomers();

    Customer getCustomerById(Long id);

    Customer updateCustomer(Long id, Customer customer);

    Customer deleteCustomerById(Long id);

    Customer deleteCustomerByName(String name);

    Customer restoreCustomerById(Long id);

    long getAllActiveCustomerCount();

    BigDecimal getTotalBucketPriceFromActiveCustomers(Long customerId);

    BigDecimal getAverageBucketPriceFromActiveCustomers(Long customerId);

    Product addProductInCustomersBucketIfActive(Long customerId, Long productId);

    Product deleteProductFromCustomersBucket(Long customerId, Long productId);

    void deleteAllProductsFromActiveCustomersBucket(Long customerId);

}
