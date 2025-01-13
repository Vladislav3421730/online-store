package com.example.webapp.servlet;

import com.example.webapp.dto.ProductDto;
import com.example.webapp.service.ProductService;
import com.example.webapp.service.impl.ProductServiceImpl;
import com.example.webapp.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/manager/products")
public class ManagerProductServlet extends HttpServlet {

    private final ProductService productService = ProductServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ProductDto> products = productService.findAll();
        req.setAttribute("products",products);
        req.getRequestDispatcher(JspHelper.getPath("managerProducts")).forward(req,resp);
    }
}
