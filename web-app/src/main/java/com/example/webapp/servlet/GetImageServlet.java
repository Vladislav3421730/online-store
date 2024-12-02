package com.example.webapp.servlet;

import com.example.webapp.dao.DAO;
import com.example.webapp.dao.ImageDao;
import com.example.webapp.model.Image;
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

    private final ImageDao imageDAO=ImageDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long imageId;
        try {
            log.info("Received request to fetch image. Query parameter: id={}", req.getParameter("id"));
            imageId = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e) {
            log.error("Invalid image ID parameter: {}", req.getParameter("id"), e);
            throw new RuntimeException("Invalid image ID parameter: " + req.getParameter("id"), e);
        }
        Image image = imageDAO.findById(imageId)
                .orElseThrow(()-> new RuntimeException("Image with ID " + imageId + " not found"));
        resp.setContentType(image.getContentType());
        byte[] imageData = image.getBytes();
        resp.setContentLength(imageData.length);
        try (OutputStream out = resp.getOutputStream()) {
            out.write(imageData);
        }

    }
}
