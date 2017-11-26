package com.shrralis.ssblog.servlet.auth;

import com.shrralis.ssblog.entity.User;
import com.shrralis.ssblog.service.UserServiceImpl;
import com.shrralis.ssblog.service.interfaces.IUserService;
import com.shrralis.ssblog.servlet.base.ServletWithGsonProcessor;
import com.shrralis.tools.model.JsonResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/signIn")
public class SignInServlet extends ServletWithGsonProcessor {
    private static final int MAX_COOKIE_SESSION_AGE = 30 * 60;                   // 30 minutes

    private IUserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        JsonResponse response = userService.signIn(login, password);

        if (response.getResult().equals(JsonResponse.OK)) {
            User user = (User) response.getData().get(0);
            HttpSession session = req.getSession();

            session.setAttribute("user", getGson().toJson(user));
            session.setMaxInactiveInterval(MAX_COOKIE_SESSION_AGE);

            Cookie userCookie = new Cookie("user", getGson().toJson(user));

            userCookie.setMaxAge(MAX_COOKIE_SESSION_AGE);
            resp.addCookie(userCookie);
            resp.sendRedirect("/");
        } else {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/signIn.html");
            PrintWriter out = resp.getWriter();

            out.println("<span style=\"color: red\">" + response.getError().getErrmsg() + "</span>");
            dispatcher.include(req, resp);
        }
    }
}
