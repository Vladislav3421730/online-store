package controllerTests;

import com.example.webapp.controller.UserController;
import com.example.webapp.dto.ProductDto;
import com.example.webapp.dto.UserDto;
import com.example.webapp.service.CartService;
import com.example.webapp.service.ProductService;
import com.example.webapp.service.SecurityContextService;
import com.example.webapp.service.UserService;
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

import static factory.UserControllerTestDataFactory.*;
import static factory.ProductTestDataFactory.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mock;

    @Mock
    private SecurityContextService securityContextService;
    @Mock
    private ProductService productService;

    @Mock
    private UserService userService;
    @Mock
    private CartService cartService;

    private static UserDto userDto;
    private static ProductDto productDto;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        userDto = createFirstUserDto();
        productDto = createFirstProductDto();
        productDto.setId(1L);

        MockitoAnnotations.openMocks(this);
        userController = new UserController(securityContextService, productService, userService, cartService);
        mock = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    @Order(1)
    @DisplayName("Test adding product to cart")
    void testAddProductToCart() throws Exception {
        Mockito.when(productService.findById(productDto.getId())).thenReturn(productDto);
        Mockito.when(securityContextService.getUser()).thenReturn(userDto);
        Mockito.when(userService.addProductToCart(userDto, productDto)).thenReturn(userDto);

        mock.perform(post("/user/cart/add/{id}", productDto.getId()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"));

        Mockito.verify(productService, Mockito.times(1)).findById(productDto.getId());
        Mockito.verify(userService, Mockito.times(1)).addProductToCart(userDto, productDto);
        Mockito.verify(securityContextService, Mockito.times(1)).updateContext(userDto);
    }

    @Test
    @Order(2)
    @DisplayName("Test adding product to cart when product is out of stock")
    void testAddProductToCart_OutOfStock() throws Exception {
        productDto.setAmount(0);
        Mockito.when(productService.findById(productDto.getId())).thenReturn(productDto);

        mock.perform(post("/user/cart/add/{id}", productDto.getId()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"));

        Mockito.verify(productService, Mockito.times(1)).findById(productDto.getId());
        Mockito.verifyNoInteractions(userService);
        Mockito.verifyNoInteractions(securityContextService);
    }
}
