package com.example.webapp.servlet.manager;

import com.example.webapp.dto.ImageDto;
import com.example.webapp.dto.ProductDto;
import com.example.webapp.model.Image;
import com.example.webapp.model.Product;
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
import java.util.List;

@WebServlet("/manager/product/edit")
@MultipartConfig
@Slf4j
public class ProductEditServlet extends HttpServlet {

    private final ProductService productService = ProductServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long productId = Validator.validateLong(req.getParameter("id"));
        req.setAttribute("product", productService.findById(productId));
        req.getRequestDispatcher(JspHelper.getPath("productEdit")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ProductDto product = productService.findById(Validator.validateLong(req.getParameter("id")));
        product.setTitle(req.getParameter("name"));
        product.setDescription((req.getParameter("description")));
        product.setCoast(BigDecimal.valueOf(Validator.validateDouble(req.getParameter("coast"))));
        product.setAmount(Validator.validateInt(req.getParameter("amount")));
        product.setCategory(req.getParameter("category"));

        List<ImageDto> existingImages = product.getImageList();

        log.info("Size of files {}", req.getParts().size());
        for (Part part : req.getParts()) {
            if (part.getName().startsWith("file")) {
                String partName = part.getName();
                int fileIndex = Validator.validateInt(partName.substring(4));
                //check if file was entered
                if (part.getSize() > 0) {
                    InputStream fileContent = part.getInputStream();
                    byte[] fileBytes = fileContent.readAllBytes();

                    if (fileIndex <= existingImages.size()) {
                        ImageDto existingImage = existingImages.get(fileIndex - 1);
                        existingImage.setContentType(part.getContentType());
                        existingImage.setBytes(fileBytes);
                    } else {
                        ImageDto newImage = ImageDto.builder()
                                .contentType(part.getContentType())
                                .bytes(fileBytes)
                                .build();
                        product.addImageToList(newImage);
                    }
                }
            }
        }
        productService.update(product);
        resp.sendRedirect(req.getContextPath() + "/manager/products");
    }
}
