package ait.shop.service.mapping;

import ait.shop.model.dto.CartDTO;
import ait.shop.model.entity.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ProductMappingService.class)
public interface CartMappingService {

    Cart mapDTOtoEntity(CartDTO dto);

    CartDTO mapEntityToDTO(Cart entity);
}
