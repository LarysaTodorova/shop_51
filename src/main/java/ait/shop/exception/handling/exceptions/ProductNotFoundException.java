package ait.shop.exception.handling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 2 способ
// ПЛЮС - быстро и удобно без лишнего кода создаем
//        глобальный обработчик ошибок
//@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super(String.format("Product with id %d does not found", id));
    }
}
