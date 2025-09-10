package ait.shop.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.Objects;


@Schema(description = "DTO for Customer")
public class CustomerDTO {

    @Schema(description = "Customer unique identifier", example = "555", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Customer name", example = "Luisa")
    private String name;


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
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return String.format("CustomerDTO: id - %d, name - '%s' ",
                id, name);
    }
}
