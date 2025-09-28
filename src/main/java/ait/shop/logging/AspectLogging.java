package ait.shop.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

// Аннотация @Aspect определяет, что этот класс является аспектом.
// Задача класса аспекта - содержать поинткаты и адвайсы.
// Поинткат - это правило, которое определяет, к каким целям
// должен быть применён адвайс.
// Адвайс - это дополнительная логика, которая внедряется в основную логику.
@Aspect
@Component
public class AspectLogging {

    private Logger logger = LoggerFactory.getLogger(AspectLogging.class);

    // public double calculate(int v1, double v2)
    // public double calculate(double[] array)
    // @Pointcut(.....calculate(..))  ->  такой поинткат применится к обоим методам
    // @Pointcut(.....calculate(int, double))  ->  такой поинткат применится только к первому методу

    // @Pointcut("execution(* ait.shop.service.ProductServiceImpl.saveProduct(ait.shop.model.dto.ProductDTO))")
//    @Pointcut("execution(* ait.shop.service.ProductServiceImpl.saveProduct(..))")
//    public void savedProduct() {
//    }
//
//    @Before("savedProduct()")
//    public void beforeSavingProduct(JoinPoint joinPoint) {
//        Object[] args = joinPoint.getArgs();
//        logger.info("Method saveProduct of class ProductServiceImpl called with argument {}", args[0]);
//    }
//
//    @After("savedProduct()")
//    public void afterSavingProduct() {
//        logger.info("Method saveProduct of class ProductServiceImpl finished its work");
//    }
//
//    @Pointcut("execution(* ait.shop.service.ProductServiceImpl.getById(..))")
//    public void getProductById() {
//    }
//
//    @AfterReturning(
//            pointcut = "getProductById()",
//            returning = "result"
//    )
//    public void afterReturningProductById(Object result) {
//        logger.info("Method getProductById of class ProductServiceImpl successfully returned result: {}", result);
//
//    }
//
//    @AfterThrowing(
//            pointcut = "getProductById()",
//            throwing = "e"
//    )
//    public void exceptionWhileGettingProductById(Exception e) {
//        logger.warn("Method getProductById of class ProductServiceImpl threw an exception: {}", e.getMessage());
//    }
//
    @Pointcut("execution(* ait.shop.service.implService.*(..))")
    public void allServices() {
    }

    @Before("allServices()")
    public void beforeDoingAllServicesMethods(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        logger.info("Class {} with method {} called with arguments {}", className, methodName, args);
    }

    @After("allServices()")
    public void afterDoingAllServicesMethods(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        logger.info("Class {} with method {} finished its work.", className, methodName);
    }

    @AfterReturning(
            pointcut = "allServices()",
            returning = "result"
    )
    public void afterReturningMethod(JoinPoint joinPoint, Object result) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        logger.info("Class {} with method {} successfully returned result {}", className, methodName, result);
    }

    @AfterThrowing(
            pointcut = "allServices()",
            throwing = "e"
    )
    public void exceptionCaught(JoinPoint joinPoint, Exception e) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        logger.warn("Class {} with method {} failed with exception: {}", className, methodName, e.getMessage());
    }
}
