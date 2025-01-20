package DaoTests.productDaoTesting;

import com.example.webapp.dto.ProductFilterDTO;
import com.example.webapp.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ProductTestDataBuilder {

    public static Product createFirstProduct() {
        return Product.builder()
                .title("Product A")
                .description("Description for Product A")
                .category("Category 1")
                .amount(10)
                .imageList(new ArrayList<>())
                .coast(BigDecimal.valueOf(100.50))
                .build();
    }

    public static Product createSecondProduct() {
        return Product.builder()
                .title("Product B")
                .description("Description for Product B")
                .category("Category 2")
                .amount(5)
                .imageList(new ArrayList<>())
                .coast(BigDecimal.valueOf(50.75))
                .build();
    }

    public static Product createThirdProduct() {
        return Product.builder()
                .title("Product C")
                .description("Description for Product C")
                .category("Category 3")
                .amount(20)
                .imageList(new ArrayList<>())
                .coast(BigDecimal.valueOf(200.8))
                .build();
    }

    public static ProductFilterDTO createProductFilterDto() {
        return ProductFilterDTO.builder()
                .category("Category 1")
                .sort("cheap")
                .build();
    }

    public static ProductFilterDTO createProductFilterCoastDto() {
        return ProductFilterDTO.builder()
                .minPrice(BigDecimal.valueOf(50))
                .maxPrice(BigDecimal.valueOf(150))
                .build();
    }
}
