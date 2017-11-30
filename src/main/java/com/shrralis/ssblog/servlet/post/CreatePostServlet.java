package com.shrralis.ssblog.servlet.post;

import com.shrralis.ssblog.config.ImagesConfig;
import com.shrralis.ssblog.dto.NewEditPostDTO;
import com.shrralis.ssblog.service.PostServiceImpl;
import com.shrralis.ssblog.service.interfaces.IPostService;
import com.shrralis.ssblog.servlet.base.ServletWithGsonProcessor;
import com.shrralis.tools.model.JsonError;
import com.shrralis.tools.model.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/createPost")
@MultipartConfig(maxFileSize = ImagesConfig.IMAGES_MAX_SIZE)
public class CreatePostServlet extends ServletWithGsonProcessor {
    private static Logger logger = LoggerFactory.getLogger(GetAllPostsServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/createPost.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/createPost.jsp");
        IPostService service;

        try {
            service = new PostServiceImpl();
        } catch (ClassNotFoundException | SQLException e) {
            logger.debug("Exception!", e);
            req.setAttribute("id", req.getParameter("id"));
            req.setAttribute("title", req.getParameter("title"));
            req.setAttribute("description", req.getParameter("description"));
            req.setAttribute("text", req.getParameter("text"));
            req.setAttribute("error", JsonError.Error.UNEXPECTED.getMessage());
            dispatcher.forward(req, resp);
            return;
        }

        NewEditPostDTO dto = NewEditPostDTO.Builder.aNewEditPostDTO()
                .setCookieUser(getCookieUser(req))
                .setPostTitle(req.getParameter("title"))
                .setPostDescription(req.getParameter("description"))
                .setPostText(req.getParameter("text"))
                .setDirectoryPath(req.getServletContext().getRealPath("/") + "/.." + ImagesConfig.IMAGES_ROOT_PATH)
                .setImagePart(req.getPart("image"))
                .build();
        JsonResponse response = service.create(dto);

        if (response.getResult().equals(JsonResponse.OK)) {
            resp.sendRedirect("/");
        } else {
            req.setAttribute("title", req.getParameter("title"));
            req.setAttribute("description", req.getParameter("description"));
            req.setAttribute("text", req.getParameter("text"));
            req.setAttribute("response", response);
            req.setAttribute("button", req.getAttribute("button"));
            dispatcher.forward(req, resp);
        }
    }
}
