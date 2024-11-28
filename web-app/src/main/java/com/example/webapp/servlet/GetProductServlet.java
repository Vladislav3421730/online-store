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

import java.io.IOException;

@WebServlet("/product/get")
public class GetProductServlet extends HttpServlet {

    private final ProductService productService= ProductServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Product product=productService.findById(Long.parseLong(req.getParameter("id")));
        req.setAttribute("product",product);
        req.getRequestDispatcher(JspHelper.getPath("product")).forward(req,resp);
    }
}
