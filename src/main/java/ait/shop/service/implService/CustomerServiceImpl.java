package ait.shop.service.implService;

import ait.shop.exception.handling.exceptions.customer.CustomerNotFoundException;
import ait.shop.model.dto.CustomerDTO;
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

    private final CustomerRepository customerRepository;
    private final CustomerMappingService customerMapping;
    private final ProductService productService;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMappingService customerMapping, ProductService productService) {
        this.customerRepository = customerRepository;
        this.customerMapping = customerMapping;
        this.productService = productService;
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
        Customer customer = getActiveCustomer(id);
        return customerMapping.mapEntityToDto(customer);
    }

    private Customer getActiveCustomer(Long id) {
        return customerRepository.findById(id)
                .filter(Customer::isActive)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @Override
    @Transactional
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTOForUpdate) {

//        if (customerDTOForUpdate.getId() == null) {
//            throw new CustomerNotFoundException(customerDTOForUpdate.getId());
//        }
//        Customer customer = getActiveCustomer(customerDTOForUpdate.getId());
//
//        if (customerDTOForUpdate.getName() != null)
//            customer.setName(customerDTOForUpdate.getName());
//        return customerMapping.mapEntityToDto(customer);
        Customer customer = getActiveCustomer(id);
        customer.setName(customerDTOForUpdate.getName());
        return customerMapping.mapEntityToDto(customer);
    }

    @Override
    @Transactional
    public CustomerDTO deleteCustomerById(Long id) {
        Customer customer = getActiveCustomer(id);
        customer.setActive(false);
        return customerMapping.mapEntityToDto(customer);
    }

    @Override
    @Transactional
    public CustomerDTO deleteCustomerByName(String name) {
        Customer customer = customerRepository.findByName(name);
        if (customer == null) {
            throw new CustomerNotFoundException(name);
        }
        customer.setActive(false);
        return customerMapping.mapEntityToDto(customer);
    }

    @Override
    @Transactional
    public CustomerDTO restoreCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        customer.setActive(true);
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
                .filter(Product::isActive)
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    @Transactional
    public double getAverageCartPriceFromActiveCustomer(Long customerId) {
        Customer customer = getActiveCustomer(customerId);
        return customer
                .getCart()
                .getProducts()
                .stream()
                .filter(Product::isActive)
                .mapToDouble(product -> product.getPrice().doubleValue())
                .average()
                .orElse(0.0);
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
    public void deleteProductFromCustomerCart(Long customerId, Long productId) {
        Customer customer = getActiveCustomer(customerId);
        Product product = productService.getEntityById(productId);
        customer.getCart().getProducts().remove(product);
    }

    @Override
    @Transactional
    public void deleteAllProductsFromActiveCustomerCart(Long customerId) {
        Customer customer = getActiveCustomer(customerId);
        if (customer.getCart().getProducts() != null) {
            customer.getCart().getProducts().clear();
        }
    }

}
