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

    private DAO<Long, Image> imageDAO;

    @Override
    public void init() throws ServletException {
        imageDAO=ImageDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Image image = imageDAO.findById(Long.parseLong(req.getParameter("id"))).get();
        resp.setContentType(image.getContentType());
        byte[] imageData = image.getBytes();
        resp.setContentLength(imageData.length);
        try (OutputStream out = resp.getOutputStream()) {
            out.write(imageData);
        }

    }
}
