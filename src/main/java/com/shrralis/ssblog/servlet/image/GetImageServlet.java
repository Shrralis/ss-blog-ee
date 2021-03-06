package com.shrralis.ssblog.servlet.image;

import com.shrralis.ssblog.config.ImagesConfig;
import com.shrralis.ssblog.dto.GetImageDTO;
import com.shrralis.ssblog.service.ImageServiceImpl;
import com.shrralis.ssblog.service.interfaces.IImageService;
import com.shrralis.ssblog.servlet.base.ServletWithGsonProcessor;
import com.shrralis.tools.model.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;

@WebServlet("/getImage")
public class GetImageServlet extends ServletWithGsonProcessor {
    private static Logger logger = LoggerFactory.getLogger(GetImageServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IImageService service;

        try {
            service = new ImageServiceImpl();
        } catch (ClassNotFoundException | SQLException e) {
            logger.debug("Exception with creating ImageService!", e);
            return;
        }

        JsonResponse response;

        try {
            response = service.get(GetImageDTO.Builder.aGetImageDTO()
                    .setCookieUser(getCookieUser(req))
                    .setDirectoryPath(req.getServletContext().getRealPath("/") + ".." + ImagesConfig.IMAGES_ROOT_PATH)
                    .setImageId(Integer.valueOf(req.getParameter("id")))
                    .build());
        } catch (SQLException e) {
            logger.debug("Exception with getting image!", e);
            return;
        }

        File file;

        if (!response.getResult().equals(JsonResponse.OK)) {
            // TODO: show error
            logger.debug("ERROR!!!!!!!!! {}", response.getError().getErrmsg());

            file = new File(getServletContext().getRealPath("/") + ".." +
                    ImagesConfig.IMAGES_ROOT_PATH + "/image-not-found.png");
        } else {
            file = (File) response.getData().get(0);
        }

        String fileExtension;

        switch (file.getName().substring(file.getName().indexOf('.'))) {
            case "png":
            case "PNG":
                fileExtension = "png";

                break;
            case "gif":
            case "GIF":
                fileExtension = "gif";

                break;
            case "jpg":
            case "JPG":
            case "jpeg":
            case "JPEG":
            default:
                fileExtension = "jpeg";

                break;
        }

        resp.setContentType("image/" + fileExtension);
        Files.copy(file.toPath(), resp.getOutputStream());
    }
}
