package ait.shop.service;

import ait.shop.model.dto.CustomerDTO;
import ait.shop.model.dto.ProductDTO;
import ait.shop.model.entity.Customer;
import ait.shop.repository.CustomerRepository;
import ait.shop.service.interfaces.CustomerService;
import ait.shop.service.mapping.CustomerMappingService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final CustomerMappingService mapper;

    public CustomerServiceImpl(CustomerRepository repository, CustomerMappingService mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = mapper.mapDtoToEntity(customerDTO);
       // customer.setActive(true);
        return mapper.mapEntityToDto(repository.save(customer));
    }

    @Override
    public List<CustomerDTO> getAllActiveCustomers() {
        return repository.findAll().stream()
                .filter(Customer::isActive)
                .map(mapper::mapEntityToDto)
                .toList();
    }

    @Override
    public CustomerDTO getActiveCustomerById(Long id) {
        Customer customer = repository.findById(id).orElse(null);
        if (customer == null || !customer.isActive()) return null;

        return mapper.mapEntityToDto(customer);
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        return null;
    }

    @Override
    public CustomerDTO deleteCustomerById(Long id) {
        return null;
    }

    @Override
    public CustomerDTO deleteCustomerByName(String name) {
        return null;
    }

    @Override
    public CustomerDTO restoreCustomerById(Long id) {
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
    public ProductDTO addProductInCustomersBucketIfActive(Long customerId, Long productId) {
        return null;
    }

    @Override
    public ProductDTO deleteProductFromCustomersBucket(Long customerId, Long productId) {
        return null;
    }

    @Override
    public List<ProductDTO> deleteAllProductsFromActiveCustomersBucket(Long customerId) {
        return List.of();
    }

}
