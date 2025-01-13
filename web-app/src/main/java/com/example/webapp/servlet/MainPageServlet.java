package com.example.webapp.servlet;


import com.example.webapp.dto.ProductDto;
import com.example.webapp.service.ProductService;
import com.example.webapp.service.impl.ProductServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.example.webapp.utils.JspHelper;


import java.io.IOException;
import java.util.List;


@WebServlet("/")
public class MainPageServlet extends HttpServlet {

    private final ProductService productService = ProductServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ProductDto> products = productService.findAll();
        req.setAttribute("products", products);
        req.getRequestDispatcher(JspHelper.getPath("index")).forward(req, resp);
    }
}
