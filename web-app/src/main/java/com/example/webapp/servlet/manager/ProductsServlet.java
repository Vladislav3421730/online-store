package com.example.webapp.servlet.manager;

import com.example.webapp.dto.ProductDto;
import com.example.webapp.dto.ProductFilterDTO;
import com.example.webapp.service.ProductService;
import com.example.webapp.service.impl.ProductServiceImpl;
import com.example.webapp.utils.JspHelper;
import com.example.webapp.utils.ProductFilterFormationAssistant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@WebServlet("/manager/products")
@Slf4j
public class ProductsServlet extends HttpServlet {

    private final ProductService productService = ProductServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int page = Integer.parseInt(req.getParameter("page") != null ? req.getParameter("page") : "1");
        ProductFilterDTO productFilterDTO = ProductFilterFormationAssistant.formFilter(req);

        List<ProductDto> products = productService.findAllByPriceFilter(productFilterDTO,page);
        int totalProducts = productService.getTotalAmount(productFilterDTO);

        int totalPages = (int) Math.ceil((double) totalProducts / 10);

        req.setAttribute("filter", productFilterDTO);
        req.setAttribute("products", products);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);

        req.getRequestDispatcher(JspHelper.getPath("managerProducts")).forward(req, resp);
    }
}
