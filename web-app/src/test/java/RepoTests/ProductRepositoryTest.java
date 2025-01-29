package RepoTests;

import com.example.webapp.dto.ProductFilterDTO;
import com.example.webapp.model.Product;
import com.example.webapp.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringJUnitConfig(classes = TestJpaConfig.class)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private static Product firstProduct;
    private static Product secondProduct;

    @BeforeAll
    static void setUp() {
        firstProduct = Product.builder()
                .title("Laptop HP")
                .coast(BigDecimal.valueOf(1000.0))
                .build();
        secondProduct = Product.builder()
                .title("Laptop Dell")
                .coast(BigDecimal.valueOf(1200.0))
                .build();
    }

    @Test
    @Order(1)
    @DisplayName("Test save product")
    void testSaveProduct() {
        productRepository.save(firstProduct);
        productRepository.save(secondProduct);
        assertNotNull(firstProduct.getId());
        assertNotNull(secondProduct.getId());

    }

    @Test
    @Order(2)
    @DisplayName("Test find product by title")
    void testFindAllByTitleContainingIgnoreCase() {
        List<Product> products = productRepository.findAllByTitleContainingIgnoreCase("laptop");
        assertNotNull(products);
        assertEquals(2, products.size());
    }

    @Test
    @Order(3)
    @DisplayName("Test find all products")
    void testFindAllByOrderById() {
        List<Product> products = productRepository.findAllByOrderById();
        assertNotNull(products);
        assertEquals(2, products.size());
        assertTrue(products.get(0).getId() < products.get(1).getId());
    }

    @Test
    @Order(4)
    @DisplayName("Test find all products by filter")
    void testFindAllByFilter() {
        ProductFilterDTO filterDTO = new ProductFilterDTO();
        filterDTO.setTitle("HP");

        List<Product> products = productRepository.findAllByFilter(filterDTO);
        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals("Laptop HP", products.get(0).getTitle());
    }

    @Test
    @Order(5)
    @DisplayName("Test find all products by filter")
    void testFindAllByPriceFilter() {
        ProductFilterDTO filterDTO = new ProductFilterDTO();
        filterDTO.setMinPrice(BigDecimal.valueOf(900.0));
        filterDTO.setMaxPrice(BigDecimal.valueOf(1100.0));

        List<Product> products = productRepository.findAllByPriceFilter(filterDTO, 0);
        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals(0,BigDecimal.valueOf(1000.0)
                .compareTo(products.get(0).getCoast()));
    }

    @Test
    @Order(6)
    @DisplayName("Test get total products amount by filter")
    void testGetTotalAmountByFilter() {
        ProductFilterDTO filterDTO = new ProductFilterDTO();
        filterDTO.setMinPrice(BigDecimal.valueOf(1000.0));
        filterDTO.setMaxPrice(BigDecimal.valueOf(1300.0));

        int totalAmount = productRepository.getTotalAmountByFilter(filterDTO);
        assertEquals(2, totalAmount);
    }

    @Test
    @Order(7)
    @DisplayName("Test find product by ID")
    void testFindById() {
        var foundProduct = productRepository.findById(firstProduct.getId());
        assertTrue(foundProduct.isPresent(), "Product should be found");
        assertEquals(firstProduct.getId(), foundProduct.get().getId(), "Product ID should match");
        var notFoundProduct = productRepository.findById(Long.MAX_VALUE);
        assertFalse(notFoundProduct.isPresent(), "Product should not be found for invalid ID");
    }

    @Test
    @Order(8)
    @DisplayName("Test delete product")
    @Transactional
    void testDeleteProductWithOrderItems() {
        productRepository.deleteProductWithOrderItems(firstProduct.getId());
        assertFalse(productRepository.existsById(firstProduct.getId()));
    }


}
