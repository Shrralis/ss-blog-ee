package com.shrralis.ssblog.servlet.post;

import com.shrralis.ssblog.dto.EditUpdaterDTO;
import com.shrralis.ssblog.dto.GetPostDTO;
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

@WebServlet("/editUpdaters")
public class EditUpdatersServlet extends ServletWithGsonProcessor {
    private static final Logger logger = LoggerFactory.getLogger(EditUpdatersServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getParameter("action") == null) {
                IPostService postService = new PostServiceImpl();

                req.setAttribute("response", postService.getUsersWithAccess(GetPostDTO.Builder.aGetPostDTO()
                        .setCookieUser(getCookieUser(req))
                        .setPostId(Integer.valueOf(req.getParameter("id")))
                        .build()));
                req.setAttribute("id", req.getParameter("id"));
                getServletContext().getRequestDispatcher("/editUpdaters.jsp").forward(req, resp);
                return;
            }

            try {
                if ("add".equals(req.getParameter("action"))) {
                    req.setAttribute("response", new PostServiceImpl()
                            .addUpdater(EditUpdaterDTO.Builder.anEditUpdaterDTO()
                                    .setCookieUser(getCookieUser(req))
                                    .setPostId(Integer.valueOf(req.getParameter("id")))
                                    .setUpdaterId(Integer.valueOf(req.getParameter("user_id")))
                                    .build()));
                } else {
                    req.setAttribute("response", new PostServiceImpl()
                            .revokeUpdater(EditUpdaterDTO.Builder.anEditUpdaterDTO()
                                    .setCookieUser(getCookieUser(req))
                                    .setPostId(Integer.valueOf(req.getParameter("id")))
                                    .setUpdaterId(Integer.valueOf(req.getParameter("user_id")))
                                    .build()));
                }
            } catch (ClassNotFoundException | SQLException e) {
                logger.debug("Exception!", e);
            }
            resp.sendRedirect(req.getServletContext().getContextPath() + "/editUpdaters?id=" + req.getParameter("id"));
        } catch (ClassNotFoundException | SQLException e) {
            logger.debug("Exception!", e);
        }
    }
}
