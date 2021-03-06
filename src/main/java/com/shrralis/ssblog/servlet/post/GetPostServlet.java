package com.shrralis.ssblog.servlet.post;

import com.shrralis.ssblog.dto.GetPostDTO;
import com.shrralis.ssblog.dto.PostUpdaterDTO;
import com.shrralis.ssblog.entity.Post;
import com.shrralis.ssblog.entity.User;
import com.shrralis.ssblog.service.PostServiceImpl;
import com.shrralis.ssblog.service.interfaces.IPostService;
import com.shrralis.ssblog.servlet.base.ServletWithGsonProcessor;
import com.shrralis.tools.model.JsonError;
import com.shrralis.tools.model.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/post")
public class GetPostServlet extends ServletWithGsonProcessor {
    private static Logger logger = LoggerFactory.getLogger(GetPostServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IPostService postService;
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/post.jsp");

        try {
            postService = new PostServiceImpl();
        } catch (ClassNotFoundException | SQLException e) {
            logger.debug("Exception with creating PostService!", e);
            req.setAttribute("response", new JsonResponse(JsonError.Error.UNEXPECTED));

            try {
                dispatcher.forward(req, resp);
            } catch (ServletException | IOException e1) {
                logger.debug("Exception!", e1);
            }
            return;
        }

        User user = getCookieUser(req);

        try {
            req.setAttribute("response", postService.get(GetPostDTO.Builder.aGetPostDTO()
                    .setCookieUser(user)
                    .setPostId(Integer.valueOf(req.getParameter("id")))
                    .build()));
        } catch (NumberFormatException e) {
            logger.debug("Exception!", e);
            return;
        }

        req.setAttribute("scope", user.getScope().name());
        req.setAttribute("user_id", user.getId());
        req.setAttribute("access", postService.getUsersWithAccess(GetPostDTO.Builder.aGetPostDTO()
                .setCookieUser(user)
                .setPostId(((Post) ((JsonResponse) req.getAttribute("response")).getData().get(0)).getId())
                .build()).getData().stream()
                .anyMatch(v -> ((PostUpdaterDTO) v).getUserId().equals(user.getId()) &&
                        ((PostUpdaterDTO) v).isPostUpdater())
        );

        try {
            dispatcher.forward(req, resp);
        } catch (ServletException | IOException e1) {
            logger.debug("Exception!", e1);
        }
    }
}
