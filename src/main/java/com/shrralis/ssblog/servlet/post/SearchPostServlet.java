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
import java.util.HashMap;
import java.util.Map;

@WebServlet("/search")
public class SearchPostServlet extends ServletWithGsonProcessor {
    private static final Logger logger = LoggerFactory.getLogger(SearchPostServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IPostService postService;
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/posts.jsp");

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
        JsonResponse response = postService.search(req.getParameter("word"), user);
        Map<Integer, Boolean> access = new HashMap<>();

        req.setAttribute("response", response);
        req.setAttribute("scope", user.getScope().name());
        req.setAttribute("user_id", user.getId());
        response.getData().forEach(p -> access.put(
                ((Post) p).getId(),
                postService.getUsersWithAccess(GetPostDTO.Builder.aGetPostDTO()
                        .setCookieUser(user)
                        .setPostId(((Post) p).getId())
                        .build()).getData().stream()
                        .anyMatch(v -> ((PostUpdaterDTO) v).getUserId().equals(user.getId()) &&
                                ((PostUpdaterDTO) v).isPostUpdater())
        ));
        req.setAttribute("access", access);

        try {
            dispatcher.forward(req, resp);
        } catch (ServletException | IOException e1) {
            logger.debug("Exception!", e1);
        }
    }
}
