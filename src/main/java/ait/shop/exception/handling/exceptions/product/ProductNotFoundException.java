package ait.shop.exception.handling.exceptions.product;

// 2 способ
// ПЛЮС -  быстро и удобно без лишнего кода создаем
//         глобальный обработчик ошибок
// МИНУС - пользователь не видит сообщение об ошибке,
//         поэтому может не понять причин ее возникновения
//@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super(String.format("Product with id %d does not found", id));
    }
}
