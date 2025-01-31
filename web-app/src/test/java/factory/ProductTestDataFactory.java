package factory;

import com.example.webapp.dto.CreateProductDto;
import com.example.webapp.dto.ProductDto;
import org.springframework.mock.web.MockMultipartFile;

import java.math.BigDecimal;

public class ProductTestDataFactory {

    public static ProductDto createProductDto(String title, String category, Integer amount, BigDecimal coast) {
        return ProductDto.builder()
                .title(title)
                .category(category)
                .amount(amount)
                .coast(coast)
                .build();
    }

    public static ProductDto createFirstProductDto() {
        return createProductDto("Laptop HP", "Electronics", 10, BigDecimal.valueOf(1000.0));
    }

    public static ProductDto createSecondProductDto() {
        return createProductDto("Laptop Dell", "Electronics", 5, BigDecimal.valueOf(1200.0));
    }

    public static ProductDto createThirdProductDto() {
        return createProductDto("Smartphone Samsung", "Electronics", 20, BigDecimal.valueOf(800.0));
    }

    public static CreateProductDto createProductDtoForSaving() {
        return CreateProductDto.builder()
                .title("Laptop")
                .description("A powerful laptop")
                .amount(10)
                .coast(BigDecimal.valueOf(1000.0))
                .build();
    }

    public static MockMultipartFile createFirstFile() {
        return new MockMultipartFile("files", "image1.jpg", "image/jpeg", new byte[]{});
    }

    public static MockMultipartFile createSecondFile() {
        return new MockMultipartFile("files", "image2.jpg", "image/jpeg", new byte[]{});
    }
}

