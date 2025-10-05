package ait.shop.service.implService;

import ait.shop.model.dto.CustomerDTO;
import ait.shop.model.dto.ProductDTO;
import ait.shop.model.entity.Cart;
import ait.shop.model.entity.Customer;
import ait.shop.model.entity.Product;
import ait.shop.repository.CustomerRepository;
import ait.shop.service.interfaces.CustomerService;
import ait.shop.service.interfaces.ProductService;
import ait.shop.service.mapping.CartMappingService;
import ait.shop.service.mapping.CustomerMappingService;
import ait.shop.service.mapping.ProductMappingService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMappingService customerMapping;
    private final ProductService productService;
    private final ProductMappingService productMapping;
    private final CartMappingService cartMapping;


    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMappingService customerMapping, ProductService productService, ProductMappingService productMapping, CartMappingService cartMapping) {
        this.customerRepository = customerRepository;
        this.customerMapping = customerMapping;
        this.productService = productService;
        this.productMapping = productMapping;
        this.cartMapping = cartMapping;

    }


    @Override
    @Transactional
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer entity = customerMapping.mapDtoToEntity(customerDTO);

        Cart cart = new Cart();
        cart.setCustomer(entity);
        entity.setCart(cart);

        customerRepository.save(entity);
        return customerMapping.mapEntityToDto(entity);
    }

    @Override
    public List<CustomerDTO> getAllActiveCustomers() {
        return customerRepository.findAll().stream()
                .filter(Customer::isActive)
                .map(customerMapping::mapEntityToDto)
                .toList();
    }

    @Override
    public CustomerDTO getActiveCustomerById(Long id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null || !customer.isActive()) return null;

        return customerMapping.mapEntityToDto(customer);
    }

    private Customer getActiveCustomer(Long id) {
        return customerRepository.findById(id)
                .filter(Customer::isActive)
                .orElseThrow(() -> new IllegalArgumentException("Customer with id " + id + " not found!"));
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTOForUpdate) {

        if (customerDTOForUpdate.getId() == null) {
            throw new IllegalArgumentException("Customer id must not be null");
        }
        Customer customer = customerRepository.findById(customerDTOForUpdate.getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Customer with id " + customerDTOForUpdate.getId() + " not found!"
                ));
        if (customerDTOForUpdate.getName() != null)
            customer.setName(customerDTOForUpdate.getName());
        if (customerDTOForUpdate.getCart() != null)
            customer.setCart(cartMapping.mapDTOtoEntity(customerDTOForUpdate.getCart()));
        return customerMapping.mapEntityToDto(customerRepository.save(customer));
    }

    @Override
    public CustomerDTO deleteCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer with id " + id + " not found!"));
        customer.setActive(false);
        customerRepository.save(customer);
        return customerMapping.mapEntityToDto(customer);
    }

    @Override
    public CustomerDTO deleteCustomerByName(String name) {
        Customer customer = customerRepository.findByName(name);
        if (customer == null) return null;
        customer.setActive(false);
        customerRepository.save(customer);
        return customerMapping.mapEntityToDto(customer);
    }

    @Override
    public CustomerDTO restoreCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer with id " + id + " not found!"));
        customer.setActive(true);
        customerRepository.save(customer);
        return customerMapping.mapEntityToDto(customer);
    }

    @Override
    public long getAllActiveCustomerCount() {
        return customerRepository.findAll()
                .stream()
                .filter(Customer::isActive)
                .count();
    }

    @Override
    @Transactional
    public BigDecimal getTotalCartPriceFromActiveCustomer(Long customerId) {
        Customer customer = getActiveCustomer(customerId);
        if (customer.getCart() == null || customer.getCart().getProducts() == null) {
            return BigDecimal.ZERO;
        }
        return customer.getCart().getProducts().stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    @Transactional
    public BigDecimal getAverageCartPriceFromActiveCustomer(Long customerId) {
        Customer customer = getActiveCustomer(customerId);
        if (customer.getCart() == null || customer.getCart().getProducts() == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal total = getTotalCartPriceFromActiveCustomer(customerId);
        int count = customer.getCart().getProducts().size();
        return total.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP);
    }

    @Override
    @Transactional
    public void addProductToCustomersCart(Long customerId, Long productId) {
        Customer customer = getActiveCustomer(customerId);
        Product product = productService.getEntityById(productId);
        customer.getCart().getProducts().add(product);
    }

    @Override
    @Transactional
    public ProductDTO deleteProductFromCustomerCart(Long customerId, Long productId) {
        Customer customer = getActiveCustomer(customerId);
        Product product = productService.getEntityById(productId);
        customer.getCart().getProducts().remove(product);
        customerRepository.save(customer);
        return productMapping.mapEntityToDto(product);
    }

    @Override
    @Transactional
    public List<ProductDTO> deleteAllProductsFromActiveCustomerCart(Long customerId) {
        Customer customer = getActiveCustomer(customerId);
        List<Product> products = new ArrayList<>(customer.getCart().getProducts());
        customer.getCart().getProducts().clear();
        customerRepository.save(customer);
        return products.stream()
                .map(productMapping::mapEntityToDto)
                .toList();
    }

}
