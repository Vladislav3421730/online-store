package com.example.webapp.servlet.guest;

import com.example.webapp.dto.ImageDto;
import com.example.webapp.service.impl.ImageServiceImpl;
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
import java.io.OutputStream;

@WebServlet("/image")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class GetImageServlet extends HttpServlet {

    ImageServiceImpl imageService = ImageServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        long imageId = Validator.validateLong(req.getParameter("id"));
        ImageDto image = imageService.findById(imageId);

        resp.setContentType(image.getContentType());
        byte[] imageData = image.getBytes();
        resp.setContentLength(imageData.length);
        try (OutputStream out = resp.getOutputStream()) {
            out.write(imageData);
        }

    }
}
