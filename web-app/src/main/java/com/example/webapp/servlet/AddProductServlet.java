package com.example.webapp.servlet;

import com.example.webapp.model.Image;
import com.example.webapp.model.Product;
import com.example.webapp.service.ProductService;
import com.example.webapp.service.impl.ProductServiceImpl;
import com.example.webapp.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;

@Slf4j
@WebServlet("/product/add")
@MultipartConfig
public class AddProductServlet extends HttpServlet {

    private final ProductService productService=ProductServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath("addProduct")).forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Product product= Product.builder()
                .title(req.getParameter("name"))
                .description(req.getParameter("description"))
                .coast(BigDecimal.valueOf(Double.parseDouble(req.getParameter("coast"))))
                .amount(Integer.parseInt(req.getParameter("amount")))
                .category(req.getParameter("category"))
                .imageList(new ArrayList<>())
                .build();

        log.info("Size of files {}", req.getParts().size());
        for (Part part : req.getParts()) {
            if (part.getName().startsWith("file") && part.getSize() > 0) {

                InputStream fileContent = part.getInputStream();
                byte[] fileBytes = fileContent.readAllBytes();
                Image image = Image.builder()
                        .contentType(part.getContentType())
                        .bytes(fileBytes)
                        .build();

                product.addImageToList(image);
            }
        }
        productService.save(product);
        resp.sendRedirect(req.getContextPath()+ "/");
    }
}
