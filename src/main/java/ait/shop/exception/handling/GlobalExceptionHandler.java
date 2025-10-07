package ait.shop.exception.handling;

import ait.shop.exception.Response;
import ait.shop.exception.handling.exceptions.ProductNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Set;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

//    @ExceptionHandler(ProductNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ResponseEntity<Response> handleException(ProductNotFoundException e) {
//        String message = e.getMessage();
//        logger.warn(message);
//        Response response = new Response(message);
//        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//    }

    // Способ 3
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleException(ProductNotFoundException e) {
        String message = e.getMessage();
        logger.warn(message);
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Set<ConstraintViolation<?>>> handleException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        logger.warn("{}", constraintViolations);
        return new ResponseEntity<>(constraintViolations, HttpStatus.BAD_REQUEST);
    }
}
