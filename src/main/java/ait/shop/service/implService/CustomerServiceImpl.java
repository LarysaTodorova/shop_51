package ait.shop.service.implService;

import ait.shop.model.dto.CustomerDTO;
import ait.shop.model.dto.ProductDTO;
import ait.shop.model.entity.Cart;
import ait.shop.model.entity.Customer;
import ait.shop.model.entity.Product;
import ait.shop.repository.CustomerRepository;
import ait.shop.service.interfaces.CustomerService;
import ait.shop.service.interfaces.ProductService;
import ait.shop.service.mapping.CustomerMappingService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final CustomerMappingService mapper;
    private final ProductService productService;

    public CustomerServiceImpl(CustomerRepository repository, CustomerMappingService mapper, ProductService productService) {
        this.repository = repository;
        this.mapper = mapper;
        this.productService = productService;
    }

    @Override
    @Transactional
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer entity = mapper.mapDtoToEntity(customerDTO);

        Cart cart = new Cart();
        cart.setCustomer(entity);
        entity.setCart(cart);

        repository.save(entity);
        return mapper.mapEntityToDto(entity);
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

    private Customer getActiveCustomer(Long id) {
        return repository.findById(id)
                .filter(Customer::isActive)
                .orElseThrow(() -> new IllegalArgumentException("Customer with id " + id + " not found!"));
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
    @Transactional
    public void addProductToCustomersCart(Long customerId, Long productId) {
        Customer customer = getActiveCustomer(customerId);
        Product product = productService.getEntityById(productId);
        customer.getCart().getProducts().add(product);
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
