package ait.shop.service.interfaces;

import ait.shop.model.dto.CustomerDTO;
import ait.shop.model.dto.ProductDTO;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerService {

    CustomerDTO saveCustomer(CustomerDTO customer);

    List<CustomerDTO> getAllActiveCustomers();

    CustomerDTO getActiveCustomerById(Long id);

    CustomerDTO updateCustomer(CustomerDTO customer);

    CustomerDTO deleteCustomerById(Long id);

    CustomerDTO deleteCustomerByName(String name);

    CustomerDTO restoreCustomerById(Long id);

    long getAllActiveCustomerCount();

    BigDecimal getTotalBucketPriceFromActiveCustomers();

    BigDecimal getAverageBucketPriceFromActiveCustomers(Long customerId);

    void addProductToCustomersCart(Long customerId, Long productId);

    ProductDTO deleteProductFromCustomersBucket(Long customerId, Long productId);

    List<ProductDTO> deleteAllProductsFromActiveCustomerBucket(Long customerId);

}
