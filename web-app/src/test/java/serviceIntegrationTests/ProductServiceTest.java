package serviceIntegrationTests;

import com.example.webapp.dto.CreateProductDto;
import com.example.webapp.dto.ProductDto;
import com.example.webapp.dto.ProductFilterDTO;
import com.example.webapp.exception.ProductNotFoundException;
import com.example.webapp.model.Product;
import com.example.webapp.repository.ProductRepository;
import com.example.webapp.service.ProductService;
import config.TestConfig;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;
import java.util.List;

import static factory.ProductTestDataFactory.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringJUnitConfig(classes = TestConfig.class)
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    private static Product product;
    private static CreateProductDto createProductDto;
    private static MockMultipartFile firstFile;
    private static MockMultipartFile secondFile;

    @BeforeAll
    static void setUp() {
        product = createProduct();
        createProductDto = createProductDtoForSaving();
        firstFile = createFirstFile();
        secondFile = createSecondFile();
    }

    @Test
    @Order(1)
    @DisplayName("Test save product")
    void testSaveProduct() {
        productService.save(createProductDto, List.of(firstFile, secondFile));

        Product product = productRepository
                .findAllByTitleContainingIgnoreCase(createProductDto.getTitle()).get(0);

        assertEquals(product.getTitle(), createProductDto.getTitle());
        assertEquals(product.getAmount(), createProductDto.getAmount());
        assertEquals(product.getCategory(), createProductDto.getCategory());
        assertEquals(0, product.getCoast().compareTo(createProductDto.getCoast()));

        assertEquals(product.getImageList().size(), 2);
    }

    @Test
    @Order(2)
    @DisplayName("Test find all products")
    void testFindAllProducts() {
        productRepository.save(product);
        List<ProductDto> products = productService.findAll();
        assertNotNull(products, "Products should be fetched successfully");
        assertTrue(products.size() >= 2, "There should be at least 2 products in the list");
    }

    @Test
    @Order(3)
    @DisplayName("Test find product by id")
    void testFindById() {
        ProductDto fetchedProduct = productService.findById(product.getId());
        assertNotNull(fetchedProduct, "Product should be fetched by ID");
        assertEquals(product.getTitle(), fetchedProduct.getTitle(), "Product titles should match");
    }

    @Test
    @Order(4)
    @DisplayName("Test find product by wrong id")
    void testFindProductByWrongId() {
        assertThrows(ProductNotFoundException.class,
                () -> productService.findById(Long.MAX_VALUE));
    }

    @Test
    @Order(5)
    @DisplayName("Test find all products by filter")
    void testFindAllProductsByFilter() {

        ProductFilterDTO filter = new ProductFilterDTO();
        filter.setMinPrice(BigDecimal.valueOf(100.0));
        filter.setMaxPrice(BigDecimal.valueOf(250.0));
        filter.setCategory(product.getCategory());

        List<ProductDto> filteredProducts = productService.findAllByFilter(filter);

        assertNotNull(filteredProducts, "Products should be filtered successfully");
        assertTrue(filteredProducts.size() > 0, "There should be filtered products");
        assertTrue(filteredProducts.get(0).getCoast().compareTo(BigDecimal.valueOf(100.0)) > 0
                        && filteredProducts.get(0).getCoast().compareTo(BigDecimal.valueOf(250.0)) < 0,
                "Filtered products should match the coast filter");
    }

    @Test
    @Order(6)
    @DisplayName("Test find all products by search")
    void testFindAllProductsByTitle() {
        List<ProductDto> foundProducts = productService.findAllByTitle(product.getTitle());
        assertNotNull(foundProducts, "Products should be found by search");
        assertEquals(1, foundProducts.size(), "There should be only 1 product matching the search");
        assertEquals(foundProducts.get(0).getTitle(), product.getTitle(), "Product should match the search keyword");
    }

    @Test
    @Order(7)
    @DisplayName("Test find all products by price filter")
    void testFindAllProductsByPriceFilter() {

        ProductFilterDTO filter = new ProductFilterDTO();
        filter.setMinPrice(BigDecimal.valueOf(100.0));
        filter.setMaxPrice(BigDecimal.valueOf(250.0));

        List<ProductDto> filteredProducts = productService.findAllByPriceFilter(filter, 1);

        assertNotNull(filteredProducts, "Products should be filtered by price successfully");
        assertTrue(filteredProducts.size() > 0, "There should be products matching the price filter");
        assertTrue(filteredProducts.get(0).getCoast().compareTo(BigDecimal.valueOf(100.0)) > 0
                        && filteredProducts.get(0).getCoast().compareTo(BigDecimal.valueOf(250.0)) < 0,
                "Filtered products should match the coast filter");
    }

    @Test
    @Order(8)
    @DisplayName("Test get total amount of products")
    void testGetTotalAmount() {

        ProductFilterDTO filter = new ProductFilterDTO();
        filter.setMinPrice(BigDecimal.valueOf(100.0));
        filter.setMaxPrice(BigDecimal.valueOf(250.0));

        int totalAmount = productService.getTotalAmount(filter);

        assertTrue(totalAmount > 0, "Total amount should be greater than 0");
    }

    @Test
    @Order(9)
    @DisplayName("Test delete product")
    void testDeleteProduct() {
        productService.delete(product.getId());
        Product deletedProduct = productRepository.findById(product.getId()).orElse(null);
        assertNull(deletedProduct, "Product should be deleted");
    }
}
