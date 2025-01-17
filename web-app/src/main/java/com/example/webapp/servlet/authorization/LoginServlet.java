package com.example.webapp.servlet.authorization;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.webapp.dto.UserDto;
import com.example.webapp.model.User;
import com.example.webapp.service.UserService;
import com.example.webapp.service.impl.UserServiceImpl;
import com.example.webapp.utils.JspHelper;
import com.example.webapp.utils.Messages;
import com.example.webapp.utils.ResourceBundleUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

@WebServlet("/login")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class LoginServlet extends HttpServlet {

    UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath("login")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        req.setAttribute("email", email);

        ResourceBundle messages = ResourceBundleUtils.get(req);

        if (email.isBlank() || password.length() < 6) {
            req.setAttribute("error", messages.getString(Messages.ERROR_INVALID_INPUT));
            req.getRequestDispatcher(JspHelper.getPath("login")).forward(req, resp);
            return;
        }

        Optional<UserDto> user = userService.findByEmail(email);
        if (user.isEmpty() || !BCrypt.verifyer().verify(password.toCharArray(), user.get().getPassword()).verified) {
            log.error("User authorization error {} {}, incorrect email or password",
                    email, password);
            req.setAttribute("error", messages.getString(Messages.ERROR_INVALID_CREDENTIALS));
            req.getRequestDispatcher(JspHelper.getPath("login")).forward(req, resp);
            return;
        }

        if (user.get().isBun()) {
            log.error("The user {}  has been banned", user.get().getEmail());
            req.setAttribute("error", messages.getString(Messages.ERROR_USER_BANNED));
            req.getRequestDispatcher(JspHelper.getPath("login")).forward(req, resp);
            return;
        }

        log.info("Authorization completed successfully {}", user.get().getEmail());
        req.getSession().setAttribute("user", user.get());
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
