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
import java.io.IOException;

@WebServlet("/product/filter")
public class FilterProductServlet extends HttpServlet {

    private final ProductService productService= ProductServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductFilterDTO productFilterDTO=new ProductFilterDTO(
                req.getParameter("select-category"),
                req.getParameter("sort"),
                Double.parseDouble(req.getParameter("minPrice")),
                Double.parseDouble(req.getParameter("maxPrice"))
        );
        req.setAttribute("products",productService.findAllByFilter(productFilterDTO));
        req.getRequestDispatcher(JspHelper.getPath("index")).forward(req,resp);
    }
}
