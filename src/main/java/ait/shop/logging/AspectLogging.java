package ait.shop.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectLogging {

    private Logger logger = LoggerFactory.getLogger(AspectLogging.class);

    // @Pointcut("execution(* ait.shop.service.ProductServiceImpl.saveProduct(ait.shop.model.dto.ProductDTO))")
    @Pointcut("execution(* ait.shop.service.ProductServiceImpl.saveProduct(..))")
    public void savedProduct() {
    }

    @Before("savedProduct()")
    public void beforeSavingProduct(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        logger.info("Method saveProduct of class ProductServiceImpl called with argument {}", args[0]);
    }

    @After("savedProduct()")
    public void afterSavingProduct() {
        logger.info("Method saveProduct of class ProductServiceImpl finished its work");
    }

    @Pointcut("execution(* ait.shop.service.ProductServiceImpl.getById(..))")
    public void getProductById() {
    }

    @AfterReturning(
            pointcut = "getProductById()",
            returning = "result"
    )
    public void afterReturningProductById(Object result) {
        logger.info("Method getProductById of class ProductServiceImpl successfully returned result: {}", result);

    }

    @AfterThrowing(
            pointcut = "getProductById()",
            throwing = "e"
    )
    public void exceptionWhileGettingProductById(Exception e) {
        logger.warn("Method getProductById of class ProductServiceImpl threw an exception: {}", e.getMessage());
    }
}
