package com.shrralis.ssblog.servlet.auth;

import com.shrralis.ssblog.entity.User;
import com.shrralis.ssblog.service.UserServiceImpl;
import com.shrralis.ssblog.service.interfaces.IUserService;
import com.shrralis.ssblog.servlet.base.ServletWithGsonProcessor;
import com.shrralis.tools.model.JsonError;
import com.shrralis.tools.model.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;

@WebServlet("/signIn")
public class SignInServlet extends ServletWithGsonProcessor {
    private static final int MAX_COOKIE_SESSION_AGE = 30 * 60;                   // 30 minutes
    private static Logger logger = LoggerFactory.getLogger(SignInServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();

        Cookie userCookie = new Cookie("user", null);

        userCookie.setMaxAge(0);
        resp.addCookie(userCookie);
        getServletContext().getRequestDispatcher("/signIn.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IUserService userService;
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/signIn.jsp");
        String login = req.getParameter("login");

        try {
            userService = new UserServiceImpl();
        } catch (ClassNotFoundException | SQLException e) {
            logger.debug("Exception with creating UserService!", e);
            req.setAttribute("login", login);
            req.setAttribute("error", JsonError.Error.UNEXPECTED.getMessage());
            dispatcher.forward(req, resp);
            return;
        }

        String password = req.getParameter("password");
        JsonResponse response = userService.signIn(login, password);

        if (response.getResult().equals(JsonResponse.OK)) {
            User user = (User) response.getData().get(0);
            HttpSession session = req.getSession();

            session.setAttribute("user", getGson().toJson(user));
            session.setMaxInactiveInterval(MAX_COOKIE_SESSION_AGE);

            Cookie userCookie = new Cookie("user", URLEncoder.encode(getGson().toJson(user), "UTF-8"));

            userCookie.setMaxAge(MAX_COOKIE_SESSION_AGE);
            resp.addCookie(userCookie);
            resp.sendRedirect(req.getServletContext().getContextPath() + "/");
        } else {
            req.setAttribute("login", login);
            req.setAttribute("error", response.getError().getErrmsg());
            dispatcher.forward(req, resp);
        }
    }
}
