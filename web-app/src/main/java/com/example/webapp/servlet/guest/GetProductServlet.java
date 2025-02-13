package com.example.webapp.servlet.guest;

import com.example.webapp.dto.ProductDto;
import com.example.webapp.service.ProductService;
import com.example.webapp.service.impl.ProductServiceImpl;
import com.example.webapp.utils.JspHelper;
import com.example.webapp.utils.Validator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@WebServlet("/product/get")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class GetProductServlet extends HttpServlet {

    ProductService productService = ProductServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        long productId = Validator.validateLong(req.getParameter("id"));

        ProductDto product = productService.findById(productId);
        req.setAttribute("product", product);
        req.getRequestDispatcher(JspHelper.getPath("product")).forward(req, resp);
    }
}
