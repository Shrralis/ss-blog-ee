package com.shrralis.ssblog.servlet.image;

import com.shrralis.ssblog.config.ImagesConfig;
import com.shrralis.ssblog.dto.DeleteImageDTO;
import com.shrralis.ssblog.service.ImageServiceImpl;
import com.shrralis.ssblog.service.interfaces.IImageService;
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

@WebServlet("/deleteImage")
public class DeleteImageServlet extends ServletWithGsonProcessor {
    private static final Logger logger = LoggerFactory.getLogger(DeleteImageServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("confirm") == null) {
            req.setAttribute("id", req.getParameter("id"));
            req.setAttribute("post_id", req.getParameter("post_id"));
            getServletContext().getRequestDispatcher("/deleteImage.jsp").forward(req, resp);
            return;
        } else if (req.getParameter("confirm").equalsIgnoreCase("true")) {
            IImageService service;
            DeleteImageDTO dto = DeleteImageDTO.Builder.aDeleteImageDTO()
                    .setCookieUser(getCookieUser(req))
                    .setDirectoryPath(req.getServletContext().getRealPath("/") + ".." + ImagesConfig.IMAGES_ROOT_PATH)
                    .setImageId(Integer.valueOf(req.getParameter("id")))
                    .build();

            try {
                service = new ImageServiceImpl();
            } catch (ClassNotFoundException | SQLException e) {
                logger.debug("Exception!", e);
                req.setAttribute("error", JsonError.Error.UNEXPECTED.getMessage());
                resp.sendRedirect("/post?id=" + req.getParameter("post_id"));
                return;
            }

            JsonResponse response = service.delete(dto);

            if (!response.getResult().equals(JsonResponse.OK)) {
                req.setAttribute("error", JsonError.Error.UNEXPECTED.getMessage());
                return;
            }
        }
        resp.sendRedirect("/post?id=" + req.getParameter("post_id"));
    }
}
