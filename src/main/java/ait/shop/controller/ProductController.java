package ait.shop.controller;

import ait.shop.model.entity.Product;
import ait.shop.service.interfaces.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// http://localhost:8080/api/products
@RestController
@RequestMapping("/products")
public class ProductController {

    public final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // POST /products
    @PostMapping
    public Product saveProduct(@RequestBody Product product) {
        // обращаемся к сервису для сохранения продукта
        return service.saveProduct(product);
    }

    // GET /products?id=3
    @GetMapping("/{id}")
    public Product detById(@PathVariable Long id) {
        // обращаемся к сервису и запрашиваем продукт по id
        return service.getById(id);
    }

    @GetMapping()
    public List<Product> detAll() {
        // обращаемся к сервису и запрашиваем все продукты
        return service.getAllActiveProducts();
    }

    // Update: PUT -> /products/id и в теле поля, которое мы хотим поменять
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        // обращаемся к сервису для обновления
        return service.update(id, product);
    }

    //DELETE -> DELETE products/id
    @DeleteMapping("/{productId}")
    public Product remove(@PathVariable("productId") Long id) {
        // обращаемся к сервису для удаления
        return service.deleteProduct(id);
    }

}

// POST /products/add - не правильно
// POST /products - правильно

// GET /products/getById /2 - не правильно
// GET /products/2 - правильно

// PUT /products/update/2 - не правильно
// PUT /products/2 - правильно

// DELETE /products/2 - правильно
