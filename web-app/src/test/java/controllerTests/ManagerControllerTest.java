package controllerTests;

import com.example.webapp.controller.ManagerController;
import com.example.webapp.controller.ProductController;
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
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

import static factory.ProductTestDataFactory.*;
import static org.mockito.ArgumentMatchers.any;
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
        List<ProductDto> products = List.of(firstProduct, secondProduct);
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
    @DisplayName("Test add new product - successful")
    void testAddNewProductSuccess() throws Exception {
        CreateProductDto createProductDto = new CreateProductDto();
        createProductDto.setTitle("Laptop");
        createProductDto.setDescription("A powerful laptop");
        createProductDto.setCategory("Electronics");
        createProductDto.setAmount(10);
        createProductDto.setCoast(BigDecimal.valueOf(1000.0));

        MockMultipartFile file1 = new MockMultipartFile("files", "image1.jpg", "image/jpeg", new byte[]{});
        MockMultipartFile file2 = new MockMultipartFile("files", "image2.jpg", "image/jpeg", new byte[]{});
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


}
