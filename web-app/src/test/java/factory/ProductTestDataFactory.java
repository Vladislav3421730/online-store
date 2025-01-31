package factory;

import com.example.webapp.dto.ProductDto;

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
}

