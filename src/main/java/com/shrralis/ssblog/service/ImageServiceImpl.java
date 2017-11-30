package com.shrralis.ssblog.service;

import com.shrralis.ssblog.dao.ImageJdbcDAOImpl;
import com.shrralis.ssblog.dao.PostJdbcDAOImpl;
import com.shrralis.ssblog.dao.UserJdbcDAOImpl;
import com.shrralis.ssblog.dao.interfaces.IImageDAO;
import com.shrralis.ssblog.dao.interfaces.IPostDAO;
import com.shrralis.ssblog.dao.interfaces.IUserDAO;
import com.shrralis.ssblog.dto.AddImageDTO;
import com.shrralis.ssblog.dto.DeleteImageDTO;
import com.shrralis.ssblog.dto.GetImageDTO;
import com.shrralis.ssblog.entity.Image;
import com.shrralis.ssblog.entity.Post;
import com.shrralis.ssblog.entity.User;
import com.shrralis.ssblog.service.interfaces.IImageService;
import com.shrralis.tools.TextUtil;
import com.shrralis.tools.model.JsonError;
import com.shrralis.tools.model.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.List;

public class ImageServiceImpl implements IImageService {
    private static Logger logger = LoggerFactory.getLogger(IImageService.class);
    private IImageDAO dao;
    private IUserDAO userDAO;
    private IPostDAO postDAO;

    public ImageServiceImpl() throws ClassNotFoundException, SQLException {
        dao = ImageJdbcDAOImpl.getDao();
        userDAO = UserJdbcDAOImpl.getDao();
        postDAO = PostJdbcDAOImpl.getDao();
    }

    static String writeFile(Part filePart, String directory) throws IOException {
        final String fileName = getFileName(filePart);
        OutputStream out = null;
        InputStream fileContent = null;

        try {
            out = new FileOutputStream(new File(directory + File.separator + fileName));
            fileContent = filePart.getInputStream();

            int read;
            final byte[] bytes = new byte[1024];

            while ((read = fileContent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            logger.info("New file {} created at {}", fileName, directory);
        } catch (FileNotFoundException e) {
            logger.debug("Exception with writing file to the server!", e);
            throw new IOException();
        } finally {
            if (out != null) {
                out.close();
            }

            if (fileContent != null) {
                fileContent.close();
            }
        }
        return fileName;
    }

    private static String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");

        logger.info("New file, Part Header: {}", partHeader);

        for (String content : partHeader.split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    @Override
    public JsonResponse add(AddImageDTO dto) {
        if (TextUtil.isEmpty(dto.getDirectoryPath())) {
            logger.debug("Directory path is empty!");
            return new JsonResponse(JsonError.Error.IMAGE_INTERNAL);
        }

        if (dto.getImagePart() == null) {
            return new JsonResponse(JsonError.Error.IMAGE_BAD_PART);
        }

        if (dto.getCookieUser() == null) {
            return new JsonResponse(JsonError.Error.NO_ACCESS);
        }

        try {
            User user = userDAO.getById(dto.getCookieUser().getId(), true);

            if (user == null ||
                    User.Scope.WRITER.ordinal() < user.getScope().ordinal() ||
                    !user.getPassword().equals(dto.getCookieUser().getPassword())) {
                return new JsonResponse(JsonError.Error.NO_ACCESS);
            }
        } catch (SQLException e) {
            logger.debug("Exception with recognizing user!", e);
            return new JsonResponse(JsonError.Error.DATABASE);
        }

        String imageName;
        Image image;

        try {
            imageName = writeFile(dto.getImagePart(), dto.getDirectoryPath());
            image = dao.add(Image.Builder.anImage()
                    .setSrc(imageName)
                    .build());
        } catch (IOException e) {
            logger.debug("Exception with writing image!", e);
            return new JsonResponse(JsonError.Error.IMAGE_WRITING_FAIL);
        } catch (SQLException e) {
            logger.debug("Exception with adding image to DB!", e);
            return new JsonResponse(JsonError.Error.DATABASE);
        }
        image.setSrc(null);
        return new JsonResponse(image);
    }

    @Override
    public JsonResponse delete(DeleteImageDTO dto) {
        if (TextUtil.isEmpty(dto.getDirectoryPath())) {
            logger.debug("Directory path is empty!");
            return new JsonResponse(JsonError.Error.IMAGE_INTERNAL);
        }

        if (dto.getImageId() == null) {
            return new JsonResponse(JsonError.Error.IMAGE_BAD_ID);
        }

        if (dto.getCookieUser() == null) {
            return new JsonResponse(JsonError.Error.NO_ACCESS);
        }

        try {
            User user = userDAO.getById(dto.getCookieUser().getId(), true);

            if (user == null ||
                    User.Scope.ADMIN.ordinal() < user.getScope().ordinal() ||
                    !user.getPassword().equals(dto.getCookieUser().getPassword())) {
                return new JsonResponse(JsonError.Error.NO_ACCESS);
            }
        } catch (SQLException e) {
            logger.debug("Exception with recognizing user!", e);
            return new JsonResponse(JsonError.Error.DATABASE);
        }

        Image image;

        try {
            image = dao.get(dto.getImageId(), true);
        } catch (SQLException e) {
            logger.debug("Exception with getting image!", e);
            return new JsonResponse(JsonError.Error.DATABASE);
        }

        if (image == null) {
            return new JsonResponse(JsonError.Error.IMAGE_NOT_EXISTS);
        }

        try {
            dao.delete(image);
        } catch (SQLException e) {
            logger.debug("Exception with deleting photo from DB!", e);
            return new JsonResponse(JsonError.Error.DATABASE);
        }

        try {
            return new JsonResponse(deleteFile(image.getSrc(), dto.getDirectoryPath()) ?
                    JsonResponse.OK :
                    JsonResponse.ERROR);
        } catch (IOException e) {
            logger.debug("Exception with deleting photo from filesystem!", e);
            return new JsonResponse(JsonError.Error.IMAGE_INTERNAL);
        }
    }

    @Override
    public JsonResponse get(GetImageDTO dto) throws SQLException {
        if (TextUtil.isEmpty(dto.getDirectoryPath())) {
            logger.debug("Directory path is empty!");
            return new JsonResponse(JsonError.Error.IMAGE_INTERNAL);
        }

        if (dto.getImageId() == null || dto.getImageId() < 1) {
            return new JsonResponse(JsonError.Error.IMAGE_BAD_ID);
        }

        if (dto.getCookieUser() == null) {
            return new JsonResponse(JsonError.Error.NO_ACCESS);
        }

        try {
            User user = userDAO.getById(dto.getCookieUser().getId(), true);

            if (user == null ||
                    User.Scope.BANNED.equals(user.getScope().ordinal()) ||
                    !user.getPassword().equals(dto.getCookieUser().getPassword())) {
                return new JsonResponse(JsonError.Error.NO_ACCESS);
            }
        } catch (SQLException e) {
            logger.debug("Exception with recognizing user!", e);
            return new JsonResponse(JsonError.Error.DATABASE);
        }

        Image image;

        try {
            image = dao.get(dto.getImageId(), true);
        } catch (SQLException e) {
            logger.debug("Exception with getting image!", e);
            return new JsonResponse(JsonError.Error.DATABASE);
        }

        if (image == null) {
            return new JsonResponse(JsonError.Error.IMAGE_NOT_EXISTS);
        }

        try {
            List<Post> linkedPosts = postDAO.getByImage(image);

            if (!linkedPosts.isEmpty() && linkedPosts.stream().noneMatch(post -> post.isPosted() ||
                    post.getCreator().getId().equals(dto.getCookieUser().getId()))) {
                return new JsonResponse(JsonError.Error.NO_ACCESS);
            }
        } catch (ClassNotFoundException | SQLException e) {
            logger.debug("Error with checking access by posts!", e);
            return new JsonResponse(JsonError.Error.DATABASE);
        }

        File file = new File(dto.getDirectoryPath() + "/" + image.getSrc());

        if (!file.exists()) {
            return new JsonResponse(JsonError.Error.IMAGE_NOT_EXISTS);
        }
        return new JsonResponse(file);
    }

    private boolean deleteFile(final String relativeSrc, final String directory) throws IOException {
        return Files.deleteIfExists(new File(directory + File.separator + relativeSrc).toPath());
    }
}
