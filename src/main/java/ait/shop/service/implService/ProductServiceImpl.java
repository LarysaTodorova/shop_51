package ait.shop.service.implService;

import ait.shop.model.dto.ProductDTO;
import ait.shop.model.entity.Product;
import ait.shop.repository.ProductRepository;
import ait.shop.service.interfaces.ProductService;
import ait.shop.service.mapping.ProductMappingService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductMappingService mapper;

    // Это объект логгера, при помощи него осуществляется логирование,
    // то есть фиксация событий в электронный журнал (лог)
    // private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    public ProductServiceImpl(ProductRepository repository, ProductMappingService mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    @Override
    public ProductDTO saveProduct(ProductDTO productDto) {
        Product product = mapper.mapDtoToEntity(productDto);
        //product.setActive(true);
        return mapper.mapEntityToDto(repository.save(product));
    }

    @Override
    public ProductDTO getById(Long id) {

        Product product = repository.findById(id).orElse(null);

        if (product == null || !product.isActive()) {
            throw new IllegalArgumentException(String.format("Product with id %d does not found", id));
        }

        // true || ? -> true
        // true | NPE -> true
        // true || ? ->
        return mapper.mapEntityToDto(product);
    }

    @Override
    public Product getEntityById(Long id) {
        return repository.findById(id)
                .filter(Product::isActive)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Product with id %d does not found", id)));
    }

    @Override
    public List<ProductDTO> getAllActiveProducts() {

        // У объекта логгера мы вызываем разные методы для фиксации
        // событий на определённые уровни важности
//        logger.info("Info level massage");
//        logger.warn("Warn level massage");
//        logger.error("Error level massage");

        return repository.findAll().stream()
                .filter(Product::isActive)
                // Добавим строку маппинга
                .map(mapper::mapEntityToDto)
                // .map(product -> mapper.mapEntityToDto(product))
                .toList();
    }

    @Override
    public ProductDTO update(Long id, ProductDTO productDto) {
        return null;
    }

    @Override
    @Transactional
    public ProductDTO deleteProduct(Long id) {
       Product product = getEntityById(id);
       product.setActive(false);
       return mapper.mapEntityToDto(product);
    }

    @Override
    public ProductDTO deleteByTitle(String title) {
        return null;
    }

    @Override
    public ProductDTO restoreProductById(Long id) {
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
