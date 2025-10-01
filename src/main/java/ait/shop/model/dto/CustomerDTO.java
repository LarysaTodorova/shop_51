package ait.shop.model.dto;

import ait.shop.model.entity.Cart;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.Objects;


@Schema(description = "DTO for Customer")
public class CustomerDTO {

    @Schema(description = "Customer unique identifier", example = "555", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Customer name", example = "Luisa")
    private String name;

    private CartDTO cart;

    public CustomerDTO() {
    }

    public CartDTO getCart() {
        return cart;
    }

    public void setCart(CartDTO cart) {
        this.cart = cart;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CustomerDTO that = (CustomerDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(cart, that.cart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cart);
    }

    @Override
    public String toString() {
        return String.format("Customer DTO: id - %d, name - '%s' ",
                id, name);
    }
}
