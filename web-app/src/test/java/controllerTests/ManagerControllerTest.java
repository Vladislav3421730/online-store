package controllerTests;

import com.example.webapp.controller.ManagerController;
import com.example.webapp.dto.CreateProductDto;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static factory.ProductTestDataFactory.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
public class ManagerControllerTest {

    @Autowired
    private MockMvc mock;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ManagerController managerController;

    private static ProductDto firstProduct;
    private static ProductDto secondProduct;
    private static ProductDto thirdProduct;
    private static CreateProductDto createProductDto;
    private static MockMultipartFile file1;
    private static MockMultipartFile file2;

    @BeforeEach
    void setUp() {

        firstProduct = createFirstProductDto();
        secondProduct = createSecondProductDto();
        thirdProduct = createThirdProductDto();
        createProductDto = createProductDtoForSaving();

        file1 = createFirstFile();
        file2 = createSecondFile();

        MockitoAnnotations.openMocks(this);
        managerController = new ManagerController(productService);
        mock = MockMvcBuilders.standaloneSetup(managerController).build();
    }

    @Test
    @Order(1)
    @DisplayName("Test get products with pagination and filter")
    void testGetProductsPage() throws Exception {
        ProductFilterDTO productFilterDTO = new ProductFilterDTO();
        productFilterDTO.setMinPrice(BigDecimal.valueOf(500.0));
        List<ProductDto> products = List.of(firstProduct, secondProduct,thirdProduct);
        Mockito.when(productService.findAllByPriceFilter(productFilterDTO, 1))
                .thenReturn(products);
        Mockito.when(productService.getTotalAmount(productFilterDTO))
                .thenReturn(20);

        mock.perform(get("/manager/products")
                        .param("page", "1")
                        .param("minPrice", "500.0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("managerProducts"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", products))
                .andExpect(model().attribute("currentPage", 1))
                .andExpect(model().attribute("totalPages", 2))
                .andExpect(model().attribute("filter", productFilterDTO));

        Mockito.verify(productService, Mockito.times(1)).findAllByPriceFilter(productFilterDTO, 1);
        Mockito.verify(productService, Mockito.times(1)).getTotalAmount(productFilterDTO);
    }

    @Test
    @Order(2)
    @DisplayName("Test add new product")
    void testAddNewProductSuccess() throws Exception {
        Mockito.doNothing().when(productService).save(Mockito.any(CreateProductDto.class), Mockito.anyList());
        mock.perform(MockMvcRequestBuilders.multipart("/manager/products/add")
                        .file(file1)
                        .file(file2)
                        .flashAttr("createProductDto", createProductDto))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/manager/products"));
        Mockito.verify(productService, Mockito.times(1)).save(Mockito.any(CreateProductDto.class), Mockito.anyList());
    }

    @Test
    @DisplayName("Test search for product with empty id should redirect to product list")
    void testSearchWithEmptyId() throws Exception {
        mock.perform(MockMvcRequestBuilders.get("/manager/products/search")
                        .param("id", ""))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/manager/products"));
    }

    @Test
    @DisplayName("Test search for product with invalid id should show error message")
    void testSearchWithInvalidId() throws Exception {
        mock.perform(MockMvcRequestBuilders.get("/manager/products/search")
                        .param("id", "invalid"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attributeExists("error"))
                .andExpect(view().name("managerProducts"));
    }

    @Test
    @DisplayName("Test search for product with valid id and product found")
    void testSearchWithValidIdFound() throws Exception {

        firstProduct.setId(5L);
        Mockito.when(productService.findByIdAsOptional(firstProduct.getId())).thenReturn(Optional.of(firstProduct));

        mock.perform(MockMvcRequestBuilders.get("/manager/products/search")
                        .param("id", firstProduct.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("products", hasSize(1)))
                .andExpect(view().name("managerProducts"));
    }

    @Test
    @DisplayName("Test search for product with valid id but product not found")
    void testSearchWithValidIdNotFound() throws Exception {

        Mockito.when(productService.findByIdAsOptional(Long.MAX_VALUE)).thenReturn(Optional.empty());

        mock.perform(MockMvcRequestBuilders.get("/manager/products/search")
                        .param("id", String.valueOf(Long.MAX_VALUE)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("products", hasSize(0)))
                .andExpect(view().name("managerProducts"));
    }


}
