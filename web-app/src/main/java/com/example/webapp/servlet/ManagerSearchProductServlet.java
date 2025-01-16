package com.example.webapp.servlet;

import com.example.webapp.dto.ProductDto;
import com.example.webapp.exception.InvalidParamException;
import com.example.webapp.model.Product;
import com.example.webapp.service.ProductService;
import com.example.webapp.service.impl.ProductServiceImpl;
import com.example.webapp.utils.JspHelper;
import com.example.webapp.utils.Validator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/manager/product/search")
@Slf4j
public class ManagerSearchProductServlet extends HttpServlet {

    private final ProductService productService = ProductServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String paramId = req.getParameter("id");
        if (paramId.isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/manager/products");
            return;
        }
        try {
            Long id = Validator.validateLong(paramId);
            Optional<ProductDto> product = productService.findByIdAsOptional(id);
            if (product.isPresent()) {
                req.setAttribute("products", List.of(product.get()));
            } else {
                req.setAttribute("products", List.of());
            }

        } catch (InvalidParamException e) {
            req.setAttribute("products", List.of());
            req.setAttribute("error", "Параметр id введён неверно");
        } finally {
            req.getRequestDispatcher(JspHelper.getPath("managerProducts")).forward(req, resp);
        }

    }
}
