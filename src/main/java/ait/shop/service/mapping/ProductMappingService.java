package ait.shop.service.mapping;

import ait.shop.model.dto.ProductDTO;
import ait.shop.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ProductMappingService {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    Product mapDtoToEntity(ProductDTO dto);

    ProductDTO mapEntityToDto(Product entity);


    // Ручной маппинг
//    public Product mapDtoToEntity(ProductDTO dto) {
//        Product entity = new Product();
//        entity.setTitle(dto.getTitle());
//        entity.setPrice(dto.getPrice());
//        return entity;
//    }
//
//    public ProductDTO mapEntityToDto(Product product) {
//        ProductDTO dto = new ProductDTO();
//        dto.setId(product.getId());
//        dto.setTitle(product.getTitle());
//        dto.setPrice(product.getPrice());
//        return dto;
//    }

}
