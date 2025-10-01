package ait.shop.controller;

import ait.shop.model.dto.CustomerDTO;
import ait.shop.model.dto.ProductDTO;
import ait.shop.service.interfaces.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/customers")
@Tag(name = "Customer Controller", description = "Controller for operation with customers")
public class CustomerController {

    public final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(summary = "Create customer", description = "Add new customer.", tags = {"Customer"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDTO.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(implementation = CustomerDTO.class))})})
    @PostMapping
    public CustomerDTO saveCustomer(@Parameter(description = "Create customer object") @RequestBody CustomerDTO customerDto) {
        return customerService.saveCustomer(customerDto);
    }

    @Operation(summary = "Get customer by Id", tags = {"Customer"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDTO.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = CustomerDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid customer Id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content)})
    @GetMapping("/{id}")
    public CustomerDTO getById(@Parameter(description = "The id that needs to be fetched.", required = true)
                            @PathVariable Long id) {
        return customerService.getActiveCustomerById(id);
    }

    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllActiveCustomers();
    }

    @PutMapping("/{id}")
    public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        return customerService.updateCustomer(id, customerDTO);
    }

    @DeleteMapping("/{customerId}")
    public CustomerDTO deleteCustomerById(@PathVariable("customerId") Long id) {
        return customerService.deleteCustomerById(id);
    }

    @DeleteMapping("/name")
    public CustomerDTO deleteCustomerByName(@RequestParam String name) {
        return customerService.deleteCustomerByName(name);
    }

    @PutMapping("/restore/{id}")
    public CustomerDTO restoreCustomer(@PathVariable Long id) {
        return customerService.restoreCustomerById(id);
    }

    @GetMapping("/active-count")
    public long getActiveCustomerCount() {
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



    @DeleteMapping("/from-customer-bucket/{customerId}/{productId}")
    public ProductDTO deleteProductFromCustomerBucket(@PathVariable Long customerId, @PathVariable Long productId) {
        return customerService.deleteProductFromCustomersBucket(customerId, productId);
    }

    @DeleteMapping("/products-from-customer-bucket/{customerId}")
    public List<ProductDTO> deleteProductsFromCustomerBucket(@PathVariable Long customerId) {
        return customerService.deleteAllProductsFromActiveCustomersBucket(customerId);
    }

    @PutMapping("/{customerId}/add-product/{productId}")
    public void addProductToCustomersCart(@PathVariable Long customerId, @PathVariable Long productId) {
        customerService.addProductToCustomersCart(customerId, productId);
    }

}
