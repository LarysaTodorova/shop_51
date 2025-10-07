package ait.shop.controller;

import ait.shop.exception.Response;
import ait.shop.exception.handling.exceptions.ProductNotFoundException;
import ait.shop.model.dto.ProductDTO;
import ait.shop.service.interfaces.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

// http://localhost:8080/api/products
@RestController
@RequestMapping("/products")
@Tag(name = "Product Controller", description = "Controller for operations with products")
public class ProductController {

    public final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // POST /products
    @Operation(summary = "Create product", description = "Add new product.", tags = {"Product"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(implementation = ProductDTO.class))})})

    @PostMapping
    public ProductDTO saveProduct(@Parameter(description = "Create product object") @RequestBody ProductDTO productDto) {
        // обращаемся к сервису для сохранения продукта
        return service.saveProduct(productDto);
    }

    // GET /products?id=3
    @Operation(summary = "Get product by id", description = "Get product by id.", tags = {"Product"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(implementation = ProductDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid API supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)

    })
    @GetMapping("/{id}")
    public ProductDTO detById(@Parameter(description = "The id that needs to be fetched.", required = true)
                              @PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping()
    public List<ProductDTO> detAll() {
        // обращаемся к сервису и запрашиваем все продукты
        return service.getAllActiveProducts();
    }

    // Update: PUT -> /products/id и в теле поля, которое мы хотим поменять
    @PutMapping("/{id}")
    public ProductDTO updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDto) {
        // обращаемся к сервису для обновления
        return service.update(id, productDto);
    }

    //DELETE -> DELETE products/id
    @DeleteMapping("/{productId}")
    public ProductDTO remove(@PathVariable("productId") Long id) {
        // обращаемся к сервису для удаления
        return service.deleteProduct(id);
    }

    //DELETE -> DELETE products/by-title?title=Banana
    @DeleteMapping("/by-title")
    public ProductDTO removeByTitle(@RequestParam String title) {
        return service.deleteByTitle(title);
    }

    @PutMapping("/restore/{id}")
    public ProductDTO restoreProduct(@PathVariable Long id) {
        return service.restoreProductById(id);
    }

    @GetMapping("/count")
    public long getCount() {
        return service.getProductCount();
    }

    @GetMapping("/total-price")
    public BigDecimal getTotalPrice() {
        return service.getTotalPrice();
    }

    @GetMapping("/average-price")
    public BigDecimal getAveragePrice() {
        return service.getAveragePrice();
    }

//    @ExceptionHandler(ProductNotFoundException.class)
//    public ResponseEntity<String> handleException(ProductNotFoundException e) {
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//    }

    //    @ExceptionHandler(ProductNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public String handleException(ProductNotFoundException e) {
//        return e.getMessage();
//    }
//     1 способ
//    // ПЛЮС - мы создаем обработчик ошибок под конкретно взятый контроллер,
//    //        это удобно, когда нам нужна разная логика обработки ошибок для разных контроллеров
//    // МИНУС -
//    @ExceptionHandler(ProductNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public Response handleException(ProductNotFoundException e) {
//        return new Response(e.getMessage());
//    }

}

// POST /products/add - не правильно
// POST /products - правильно

// GET /products/getById /2 - не правильно
// GET /products/2 - правильно

// PUT /products/update/2 - не правильно
// PUT /products/2 - правильно

// DELETE /products/2 - правильно
