package com.shrralis.ssblog.servlet.post;

import com.shrralis.ssblog.dto.DeletePostDTO;
import com.shrralis.ssblog.service.PostServiceImpl;
import com.shrralis.ssblog.service.interfaces.IPostService;
import com.shrralis.ssblog.servlet.base.ServletWithGsonProcessor;
import com.shrralis.tools.model.JsonError;
import com.shrralis.tools.model.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/deletePost")
public class DeletePostServlet extends ServletWithGsonProcessor {
    private static final Logger logger = LoggerFactory.getLogger(DeletePostServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("confirm") == null) {
            req.setAttribute("id", req.getParameter("id"));
            getServletContext().getRequestDispatcher("/deletePost.jsp").forward(req, resp);
            return;
        } else if (req.getParameter("confirm").equalsIgnoreCase("true")) {
            IPostService service;
            DeletePostDTO dto = DeletePostDTO.Builder.aDeletePostDTO()
                    .setCookieUser(getCookieUser(req))
                    .setPostId(Integer.valueOf(req.getParameter("id")))
                    .build();

            try {
                service = new PostServiceImpl();
            } catch (ClassNotFoundException | SQLException e) {
                logger.debug("Exception!", e);
                req.setAttribute("error", JsonError.Error.UNEXPECTED.getMessage());
                resp.sendRedirect(req.getServletContext().getContextPath() + "/");
                return;
            }

            JsonResponse response = service.delete(dto);

            if (!response.getResult().equals(JsonResponse.OK)) {
                req.setAttribute("error", JsonError.Error.UNEXPECTED.getMessage());
                return;
            }
        }
        resp.sendRedirect(req.getServletContext().getContextPath() + "/");
    }
}
