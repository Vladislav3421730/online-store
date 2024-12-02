package com.example.webapp.servlet;

import com.example.webapp.model.Product;
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

@WebServlet("/product/get")
@Slf4j
public class GetProductServlet extends HttpServlet {

    private final ProductService productService = ProductServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long productId;
        try {
            log.info("Received request to fetch product. Query parameter: id={}", req.getParameter("id"));
            productId = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e) {
            log.error("Invalid product ID parameter: {}", req.getParameter("id"), e);
            throw new RuntimeException("Invalid product ID parameter: " + req.getParameter("id"), e);
        }
        Product product = productService.findById(productId);
        req.setAttribute("product", product);
        req.getRequestDispatcher(JspHelper.getPath("product")).forward(req, resp);
    }
}
