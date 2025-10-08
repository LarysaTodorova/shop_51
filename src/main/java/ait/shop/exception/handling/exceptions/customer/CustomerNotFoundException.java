package ait.shop.exception.handling.exceptions.customer;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(Long id) {
        super(String.format("Customer with id %d does not found", id));
    }

    public CustomerNotFoundException(String name) {
        super(String.format("Customer with name %S does not found", name));
    }
}
