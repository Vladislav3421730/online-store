package productDaoTesting;

import com.example.webapp.dao.impl.ProductDaoImpl;
import com.example.webapp.dto.ProductFilterDTO;
import com.example.webapp.model.Product;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductDaoTest {

    private static ProductDaoImpl productDao;
    private static Product firstProduct;
    private static Product secondProduct;
    private static Product thirdProduct;

    private static ProductFilterDTO filterDTO;
    private static ProductFilterDTO filterCoastDTO;


    @BeforeAll
    static void setup() {
        productDao = ProductDaoImpl.getInstance();

        firstProduct = ProductTestDataBuilder.createFirstProduct();
        secondProduct = ProductTestDataBuilder.createSecondProduct();
        thirdProduct = ProductTestDataBuilder.createThirdProduct();

        filterDTO = ProductTestDataBuilder.createProductFilterDto();
        filterCoastDTO = ProductTestDataBuilder.createProductFilterCoastDto();
    }

    @Test
    @Order(1)
    @DisplayName("Test save product")
    void testSaveProduct() {
        productDao.save(firstProduct);
        productDao.save(secondProduct);
        productDao.save(thirdProduct);

        assertNotNull(firstProduct.getId());
        assertNotNull(secondProduct.getId());
        assertNotNull(thirdProduct.getId());
    }

    @Test
    @Order(2)
    @DisplayName("Test find all products")
    void testFindAllProducts() {
        List<Product> products = productDao.findAll();
        assertNotNull(products);
        assertEquals(3, products.size());
    }

    @Test
    @Order(3)
    @DisplayName("Test find product by id")
    void testFindProductById() {
        Optional<Product> product = productDao.findById(firstProduct.getId());
        assertTrue(product.isPresent(), "Product should be found");
        Product retrievedProduct = product.get();

        assertEquals("Product A", retrievedProduct.getTitle());
        assertEquals("Description for Product A", retrievedProduct.getDescription());
        assertEquals("Category 1", retrievedProduct.getCategory());
        assertEquals(10, retrievedProduct.getAmount());
        assertEquals(0, retrievedProduct.getCoast().compareTo(BigDecimal.valueOf(100.50)),
                "Product cost should be 100.50");
    }

    @Test
    @Order(4)
    @DisplayName("Test find product by invalid id")
    void testFindProductByInvalidId() {
        Optional<Product> product = productDao.findById(45L);
        assertFalse(product.isPresent(), "Product should not be found");
    }

    @Test
    @Order(5)
    @DisplayName("Test find all products by title")
    void testFindAllByTitle() {
        List<Product> products = productDao.findAllByTitle("Product A");

        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals("Product A", products.get(0).getTitle());

        products = productDao.findAllByTitle("Nonexistent Product");
        assertTrue(products.isEmpty(), "No products should be found");
    }


    @Test
    @Order(6)
    @DisplayName("Test find all products by wrong title")
    void testFindAllByWrongTitle() {
        List<Product> products = productDao.findAllByTitle("Nonexistent Product");
        assertTrue(products.isEmpty(), "No products should be found");
    }

    @Test
    @Order(7)
    @DisplayName("Test find all products by filter")
    void testFindAllByFilter() {

        List<Product> products = productDao.findAllByFilter(filterDTO);

        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals("Product A", products.get(0).getTitle());
        assertEquals(0, products.get(0).getCoast().compareTo(BigDecimal.valueOf(100.50)),
                "Product cost should be 100.50 for Category 1");
    }

    @Test
    @Order(8)
    @DisplayName("Test find all products by price filter")
    void testFindAllByPriceFilter() {


        List<Product> products = productDao.findAllByPriceFilter(filterCoastDTO, 0);

        assertNotNull(products);
        assertEquals(2, products.size());
        assertTrue(products.get(0).getCoast().compareTo(BigDecimal.valueOf(100.50)) >= 0,
                "Product cost should be greater than or equal to 50");
        assertTrue(products.get(1).getCoast().compareTo(BigDecimal.valueOf(150)) <= 0,
                "Product cost should be less than or equal to 150");
    }

    @Test
    @Order(9)
    @DisplayName("Test get total amount of products by filter ")
    void testGetTotalAmount() {
        int size = productDao.getTotalAmountByFilter(filterCoastDTO);
        assertEquals(2, size);
    }

    @Test
    @Order(10)
    @DisplayName("Test products using offset and limit")
    void testFindProductsByOffsetAndLimit() {
        List<Product> products = productDao.findAllOffset(0);
        assertEquals(3, products.size());
    }


    @Test
    @Order(11)
    @DisplayName("Test update product")
    void testUpdateProduct() {

        firstProduct.setTitle("Updated Product A");
        firstProduct.setDescription("Updated description for Product A");
        firstProduct.setCoast(BigDecimal.valueOf(120.75));

        Product updatedProduct = productDao.update(firstProduct);

        assertNotNull(updatedProduct);
        assertEquals("Updated Product A", updatedProduct.getTitle());
        assertEquals("Updated description for Product A", updatedProduct.getDescription());
        assertEquals(0, updatedProduct.getCoast().compareTo(BigDecimal.valueOf(120.75)),
                "Product cost should be 120.75");

        Optional<Product> retrievedProduct = productDao.findById(updatedProduct.getId());
        assertTrue(retrievedProduct.isPresent());
        assertEquals("Updated Product A", retrievedProduct.get().getTitle());
        assertEquals("Updated description for Product A", retrievedProduct.get().getDescription());
        assertEquals(0, retrievedProduct.get().getCoast().compareTo(BigDecimal.valueOf(120.75)),
                "Product cost should be 120.75 after update");
    }


    @Test
    @Order(12)
    @DisplayName("Test delete Product")
    void testDeleteProduct() {
        productDao.delete(thirdProduct.getId());
        Optional<Product> deletedProduct = productDao.findById(thirdProduct.getId());
        assertFalse(deletedProduct.isPresent(), "Product should not be found");
    }


}
