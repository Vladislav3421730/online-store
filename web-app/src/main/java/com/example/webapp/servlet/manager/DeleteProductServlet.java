package com.example.webapp.servlet.manager;

import com.example.webapp.service.ProductService;
import com.example.webapp.service.impl.ProductServiceImpl;
import com.example.webapp.utils.Validator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.io.IOException;

@WebServlet("/manager/product/delete")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)

public class DeleteProductServlet extends HttpServlet {

    ProductService productService = ProductServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long productId = Validator.validateLong(req.getParameter("id"));
        productService.delete(productId);
        resp.sendRedirect(req.getContextPath()+"/manager/products");
    }
}
