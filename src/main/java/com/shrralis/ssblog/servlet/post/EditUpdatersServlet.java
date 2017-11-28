package com.shrralis.ssblog.servlet.post;

import com.shrralis.ssblog.dto.EditUpdaterDTO;
import com.shrralis.ssblog.entity.User;
import com.shrralis.ssblog.service.PostServiceImpl;
import com.shrralis.ssblog.service.UserServiceImpl;
import com.shrralis.ssblog.service.interfaces.IUserService;
import com.shrralis.ssblog.servlet.base.ServletWithGsonProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;

@WebServlet("/editUpdaters")
public class EditUpdatersServlet extends ServletWithGsonProcessor {
    private static final Logger logger = LoggerFactory.getLogger(EditUpdatersServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getParameter("action") == null) {
                IUserService userService = new UserServiceImpl();

                req.setAttribute("response", userService.getAllUsers());
                req.setAttribute("id", req.getParameter("id"));
                getServletContext().getRequestDispatcher("/editUpdaters.jsp").forward(req, resp);
                return;
            }

            try {
                if ("add".equals(req.getParameter("action"))) {
                    req.setAttribute("response", new PostServiceImpl().addUpdater(new EditUpdaterDTO.Builder()
                            .setCookieUser(getGson().fromJson(
                                    URLDecoder.decode(req.getSession(false).getAttribute("user").toString(), "UTF-8"),
                                    User.class
                            ))
                            .setPostId(Integer.valueOf(req.getParameter("id")))
                            .setUpdaterId(Integer.valueOf(req.getParameter("user_id")))
                            .build()));
                } else {
                    req.setAttribute("response", new PostServiceImpl().revokeUpdater(new EditUpdaterDTO.Builder()
                            .setCookieUser(getGson().fromJson(
                                    URLDecoder.decode(req.getSession(false).getAttribute("user").toString(), "UTF-8"),
                                    User.class
                            ))
                            .setPostId(Integer.valueOf(req.getParameter("id")))
                            .setUpdaterId(Integer.valueOf(req.getParameter("user_id")))
                            .build()));
                }
            } catch (ClassNotFoundException | SQLException e) {
                logger.debug("Exception!", e);
            }
            resp.sendRedirect("/editUpdaters?id=" + req.getParameter("id"));
        } catch (ClassNotFoundException | SQLException e) {
            logger.debug("Exception!", e);
        }
    }
}
