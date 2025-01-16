package com.example.webapp.servlet;

import com.example.webapp.dto.CreateImageDto;
import com.example.webapp.dto.CreateProductDto;
import com.example.webapp.service.ProductService;
import com.example.webapp.service.impl.ProductServiceImpl;
import com.example.webapp.utils.JspHelper;
import com.example.webapp.utils.Validator;
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
import java.util.List;

@Slf4j
@WebServlet("/manager/product/add")
@MultipartConfig
public class ManagerAddProductServlet extends HttpServlet {

    private final ProductService productService = ProductServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath("addProduct")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        CreateProductDto productDTO = CreateProductDto.builder()
                .title(req.getParameter("name"))
                .description(req.getParameter("description"))
                .coast(BigDecimal.valueOf(Validator.validateDouble(req.getParameter("coast"))))
                .amount(Validator.validateInt(req.getParameter("amount")))
                .category(req.getParameter("category"))
                .build();

        log.info("Size of files {}", req.getParts().size());
        List<CreateImageDto> imageDTOList = new ArrayList<>();
        for (Part part : req.getParts()) {
            if (part.getName().startsWith("file") && part.getSize() > 0) {

                InputStream fileContent = part.getInputStream();
                byte[] fileBytes = fileContent.readAllBytes();
                CreateImageDto imageDTO = CreateImageDto.builder()
                        .contentType(part.getContentType())
                        .bytes(fileBytes)
                        .build();
                imageDTOList.add(imageDTO);
            }
        }
        productDTO.setImageList(imageDTOList);
        productService.save(productDTO);
        resp.sendRedirect(req.getContextPath() + "/manager/products");
    }
}
