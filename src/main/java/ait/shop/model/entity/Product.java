package ait.shop.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "product")
@Schema(description = "Class that describes Product")
public class Product {

    @Schema(description = "Product unique identifier", example = "777", accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Product title", example = "Banana")
    @Column(name = "title")
    private String title;

    @Schema(description = "Product price", example = "3.50")
    @Column(name = "price")
    private BigDecimal price;

    // CreatedAt -> created_at
    @Schema(description = "Is product available", accessMode = Schema.AccessMode.READ_ONLY)
    @Column
    private boolean active;

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return String.format("Product: id - %d, title - %s, price - %s, active - %s",
                id, title, price, active ? "yes" : "no");
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return active == product.active && Objects.equals(id, product.id) && Objects.equals(title, product.title) && Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, price, active);
    }
}
