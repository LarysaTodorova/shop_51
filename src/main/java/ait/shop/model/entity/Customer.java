package ait.shop.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "customer")
@Schema(description = "Class that describes Customer")
public class Customer {

    @Schema(description = "Customer unique identifier", example = "555", accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Customer name", example = "Luisa")
    @Column(name = "name")
    private String name;

    @Schema(description = "Is customer available", accessMode = Schema.AccessMode.READ_ONLY)
    @Column
    private boolean active;

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return active == customer.active && Objects.equals(id, customer.id) && Objects.equals(name, customer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, active);
    }

    @Override
    public String toString() {
        return String.format("Customer: id - %d, name - '%s', active - %s}",
                id, name, active ? "yes" : "no");
    }
}
