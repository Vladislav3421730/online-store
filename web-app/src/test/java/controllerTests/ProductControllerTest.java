package controllerTests;


import com.example.webapp.controller.ProductController;
import com.example.webapp.dto.ProductDto;
import com.example.webapp.dto.ProductFilterDTO;
import com.example.webapp.service.ProductService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static factory.ProductTestDataFactory.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mock;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private static ProductDto firstProduct;
    private static ProductDto secondProduct;

    @BeforeEach
    void setUp() {

        firstProduct = createFirstProductDto();
        secondProduct = createSecondProductDto();

        MockitoAnnotations.openMocks(this);
        productController = new ProductController(productService);
        mock = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    @Order(1)
    @DisplayName("Test find all products")
    void testFindAllProducts() throws Exception {
        List<ProductDto> products = List.of(firstProduct, secondProduct);
        Mockito.when(productService.findAll()).thenReturn(products);
        mock.perform(get("/products"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", products));

        Mockito.verify(productService, Mockito.times(1)).findAll();
    }

    @Test
    @Order(2)
    @DisplayName("Test find product by id")
    void testFindProductById() throws Exception {
        Mockito.when(productService.findById(1L)).thenReturn(firstProduct);

        mock.perform(get("/products/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("product"))
                .andExpect(model().attribute("product", firstProduct));

        Mockito.verify(productService, Mockito.times(1)).findById(1L);
    }

    @Test
    @Order(3)
    @DisplayName("Test find products by filter")
    void testFilterProductsByTitle() throws Exception {
        ProductFilterDTO productFilterDTO = new ProductFilterDTO();
        productFilterDTO.setTitle("Laptop");
        productFilterDTO.setMinPrice(BigDecimal.valueOf(500.0));
        List<ProductDto> filteredProducts = List.of(firstProduct, secondProduct);
        Mockito.when(productService.findAllByFilter(productFilterDTO)).thenReturn(filteredProducts);

        mock.perform(get("/products/filter")
                        .param("title", "Laptop")
                        .param("minPrice", "500.0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", filteredProducts))
                .andExpect(model().attribute("search", "Laptop"));

        Mockito.verify(productService, Mockito.times(1)).findAllByFilter(productFilterDTO);
    }

    @Test
    @Order(4)
    @DisplayName("Test find products by title")
    void testFindProductsByTitle() throws Exception {
        String title = "Laptop";
        List<ProductDto> products = List.of(firstProduct, secondProduct);
        Mockito.when(productService.findAllByTitle(title)).thenReturn(products);
        mock.perform(get("/products/search")
                        .param("search", title))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products",products))
                .andExpect(model().attribute("search", title));

        Mockito.verify(productService, Mockito.times(1)).findAllByTitle(title);

    }

}
