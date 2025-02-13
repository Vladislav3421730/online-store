package com.example.webapp.servlet.guest;

import com.example.webapp.dto.ProductDto;
import com.example.webapp.service.ProductService;
import com.example.webapp.service.impl.ProductServiceImpl;
import com.example.webapp.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.io.IOException;
import java.util.List;

@WebServlet("/product/search")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class SearchProductServlet extends HttpServlet {

    ProductService productService = ProductServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchParameter = req.getParameter("search");
        List<ProductDto> products = productService.findAllBySearch(searchParameter);
        req.setAttribute("products",products);
        req.setAttribute("search", searchParameter);
        req.getRequestDispatcher(JspHelper.getPath("index")).forward(req, resp);
    }
}
