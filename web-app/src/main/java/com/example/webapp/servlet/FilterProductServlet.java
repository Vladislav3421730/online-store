package com.example.webapp.servlet;

import com.example.webapp.dto.ProductFilterDTO;
import com.example.webapp.service.ProductService;
import com.example.webapp.service.impl.ProductServiceImpl;
import com.example.webapp.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/product/filter")
@Slf4j
public class FilterProductServlet extends HttpServlet {

    private final ProductService productService= ProductServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductFilterDTO productFilterDTO=new ProductFilterDTO();
        productFilterDTO.setCategory(req.getParameter("category"));
        productFilterDTO.setSort(req.getParameter("sort"));
        productFilterDTO.setTitle(req.getParameter("search-filter"));
        if(req.getParameter("minPrice")!=null && !req.getParameter("minPrice").isBlank()) {
            productFilterDTO.setMinPrice(new BigDecimal(req.getParameter("minPrice")));
        }
        if(req.getParameter("maxPrice")!=null && !req.getParameter("maxPrice").isBlank()) {
            productFilterDTO.setMaxPrice(new BigDecimal(req.getParameter("maxPrice")));
        }
        log.info("productFilterDTO: {}",productFilterDTO);
        req.setAttribute("products",productService.findAllByFilter(productFilterDTO));
        req.setAttribute("search",productFilterDTO.getTitle());
        req.getRequestDispatcher(JspHelper.getPath("index")).forward(req,resp);
    }
}
