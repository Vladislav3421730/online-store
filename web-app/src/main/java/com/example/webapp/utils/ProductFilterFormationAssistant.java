package com.example.webapp.utils;

import com.example.webapp.dto.ProductFilterDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class ProductFilterFormationAssistant {

    public ProductFilterDTO formFilter(final HttpServletRequest req){
        ProductFilterDTO productFilterDTO = new ProductFilterDTO();
        productFilterDTO.setCategory(req.getParameter("category"));
        productFilterDTO.setSort(req.getParameter("sort"));
        productFilterDTO.setTitle(req.getParameter("search-filter"));
        if (req.getParameter("minPrice") != null && !req.getParameter("minPrice").isBlank()) {
            productFilterDTO.setMinPrice(new BigDecimal(req.getParameter("minPrice")));
        }
        if (req.getParameter("maxPrice") != null && !req.getParameter("maxPrice").isBlank()) {
            productFilterDTO.setMaxPrice(new BigDecimal(req.getParameter("maxPrice")));
        }
        return productFilterDTO;
    }

}
