package com.shrralis.ssblog.servlet.post;

import com.shrralis.ssblog.config.ImagesConfig;
import com.shrralis.ssblog.dto.GetPostDTO;
import com.shrralis.ssblog.dto.NewEditPostDTO;
import com.shrralis.ssblog.service.PostServiceImpl;
import com.shrralis.ssblog.service.interfaces.IPostService;
import com.shrralis.ssblog.servlet.base.ServletWithGsonProcessor;
import com.shrralis.tools.model.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/editPost")
@MultipartConfig(maxFileSize = ImagesConfig.IMAGES_MAX_SIZE)
public class EditPostServlet extends ServletWithGsonProcessor {
    private static final Logger logger = LoggerFactory.getLogger(EditPostServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("user", getCookieUser(req));
        try {
            IPostService postService = new PostServiceImpl();

            req.setAttribute("postResponse", postService.get(GetPostDTO.Builder.aGetPostDTO()
                    .setPostId(Integer.valueOf(req.getParameter("id")))
                    .setCookieUser(getCookieUser(req))
                    .build()));
            req.setAttribute("error", req.getParameter("error"));
        } catch (ClassNotFoundException | SQLException e) {
            logger.debug("Exception!", e);
        }
        getServletContext().getRequestDispatcher("/editPost.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonResponse response = null;

        try {
            response = new PostServiceImpl().edit(NewEditPostDTO.Builder.aNewEditPostDTO()
                    .setCookieUser(getCookieUser(req))
                    .setPostId(Integer.valueOf(req.getParameter("id")))
                    .setPostTitle(req.getParameter("title"))
                    .setPostDescription(req.getParameter("description"))
                    .setPostText(req.getParameter("text"))
                    .setPosted(Boolean.valueOf(req.getParameter("posted")))
                    .setDirectoryPath(req.getServletContext().getRealPath("/") + "/.." + ImagesConfig.IMAGES_ROOT_PATH)
                    .setImagePart(req.getPart("image"))
                    .build());
        } catch (ClassNotFoundException | SQLException e) {
            logger.debug("Exception!", e);
        }

        if (response != null && response.getResult().equals(JsonResponse.OK)) {
            resp.sendRedirect(req.getServletContext().getContextPath() + "/post?id=" + req.getParameter("id"));
        } else {
            resp.sendRedirect(req.getServletContext().getContextPath() + "/editPost?id=" + req.getParameter("id") +
                    "&error=" + response.getError().getErrmsg());
        }
    }
}
