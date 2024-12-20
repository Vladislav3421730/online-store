package com.example.webapp.servlet;

import com.example.webapp.model.Image;
import com.example.webapp.service.impl.ImageServiceImpl;
import com.example.webapp.utils.Validator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/image")
@Slf4j
public class GetImageServlet extends HttpServlet {

    private final ImageServiceImpl imageService = ImageServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        long imageId = Validator.validateLong(req.getParameter("id"));
        Image image = imageService.findById(imageId);
        resp.setContentType(image.getContentType());
        byte[] imageData = image.getBytes();
        resp.setContentLength(imageData.length);
        try (OutputStream out = resp.getOutputStream()) {
            out.write(imageData);
        }

    }
}
