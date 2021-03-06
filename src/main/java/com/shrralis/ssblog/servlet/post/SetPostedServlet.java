package com.shrralis.ssblog.servlet.post;

import com.shrralis.ssblog.dto.SetPostedDTO;
import com.shrralis.ssblog.service.PostServiceImpl;
import com.shrralis.ssblog.service.interfaces.IPostService;
import com.shrralis.ssblog.servlet.base.ServletWithGsonProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/setPosted")
public class SetPostedServlet extends ServletWithGsonProcessor {
    private static final Logger logger = LoggerFactory.getLogger(SetPostedServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            IPostService postService = new PostServiceImpl();

            req.setAttribute("response", postService.setPosted(SetPostedDTO.Builder.aSetPostedDTO()
                    .setCookieUser(getCookieUser(req))
                    .setPostId(Integer.valueOf(req.getParameter("id")))
                    .setPostPosted(Boolean.valueOf(req.getParameter("posted")))
                    .build()));
        } catch (ClassNotFoundException | SQLException e) {
            logger.debug("Exception!", e);
        }
        resp.sendRedirect(req.getServletContext().getContextPath() + "/post?id=" + req.getParameter("id"));
    }
}
