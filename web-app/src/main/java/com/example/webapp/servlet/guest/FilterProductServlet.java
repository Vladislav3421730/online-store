package com.example.webapp.servlet.guest;

import com.example.webapp.dto.ProductDto;
import com.example.webapp.dto.ProductFilterDTO;
import com.example.webapp.service.ProductService;
import com.example.webapp.service.impl.ProductServiceImpl;
import com.example.webapp.utils.JspHelper;
import com.example.webapp.utils.ProductFilterFormationAssistant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@WebServlet("/product/filter")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class FilterProductServlet extends HttpServlet {

    ProductService productService = ProductServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ProductFilterDTO productFilterDTO = ProductFilterFormationAssistant.formFilter(req);
        log.info("productFilterDTO: {}", productFilterDTO);

        List<ProductDto> products =  productService.findAllByFilter(productFilterDTO);
        req.setAttribute("products",products);
        req.setAttribute("search", productFilterDTO.getTitle());
        req.getRequestDispatcher(JspHelper.getPath("index")).forward(req, resp);
    }
}
