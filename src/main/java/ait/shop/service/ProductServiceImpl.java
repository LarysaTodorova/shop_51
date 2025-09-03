package ait.shop.service;

import ait.shop.model.entity.Product;
import ait.shop.service.interfaces.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Override
    public Product saveProduct(Product product) {
        return null;
    }

    @Override
    public Product getById(Long id) {
        return null;
    }

    @Override
    public List<Product> getAllActiveProducts() {
        return List.of();
    }

    @Override
    public Product update(Long id, Product product) {
        return null;
    }

    @Override
    public Product deleteProduct(Long id) {
        return null;
    }

    @Override
    public Product deleteByTitle(String title) {
        return null;
    }

    @Override
    public Product restoreProductById(Long id) {
        return null;
    }

    @Override
    public long getProductCount() {
        return 0;
    }

    @Override
    public BigDecimal getTotalPrice() {
        return null;
    }

    @Override
    public BigDecimal getAveragePrice() {
        return null;
    }
}
