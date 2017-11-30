package com.shrralis.ssblog.service;

import com.shrralis.ssblog.dao.ImageJdbcDAOImpl;
import com.shrralis.ssblog.dao.PostJdbcDAOImpl;
import com.shrralis.ssblog.dao.PostUpdaterJdbcDAOImpl;
import com.shrralis.ssblog.dao.UserJdbcDAOImpl;
import com.shrralis.ssblog.dao.interfaces.IImageDAO;
import com.shrralis.ssblog.dao.interfaces.IPostDAO;
import com.shrralis.ssblog.dao.interfaces.IPostUpdaterDAO;
import com.shrralis.ssblog.dao.interfaces.IUserDAO;
import com.shrralis.ssblog.dto.*;
import com.shrralis.ssblog.entity.Image;
import com.shrralis.ssblog.entity.Post;
import com.shrralis.ssblog.entity.PostUpdater;
import com.shrralis.ssblog.entity.User;
import com.shrralis.ssblog.service.interfaces.IPostService;
import com.shrralis.tools.TextUtil;
import com.shrralis.tools.model.JsonError;
import com.shrralis.tools.model.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PostServiceImpl implements IPostService {
    private static Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);
    private IPostDAO dao;
    private IPostUpdaterDAO postUpdaterDAO;
    private IUserDAO userDAO;
    private IImageDAO imageDAO;

    public PostServiceImpl() throws ClassNotFoundException, SQLException {
        dao = PostJdbcDAOImpl.getDao();
        postUpdaterDAO = PostUpdaterJdbcDAOImpl.getDao();
        userDAO = UserJdbcDAOImpl.getDao();
        imageDAO = ImageJdbcDAOImpl.getDao();
    }

    @Override
    public JsonResponse addUpdater(EditUpdaterDTO dto) {
        try {
            if (dto.getCookieUser() == null) {
                return new JsonResponse(JsonError.Error.NO_ACCESS);
            }

            if (dto.getPostId() == null || dto.getPostId() < 1) {
                return new JsonResponse(JsonError.Error.POST_ID_BAD);
            }

            if (dto.getUpdaterId() == null || dto.getUpdaterId() < 1) {
                return new JsonResponse(JsonError.Error.UPDATER_ID_BAD);
            }

            final User user = userDAO.getById(dto.getCookieUser().getId(), true);
            final Post post = dao.getById(dto.getPostId());

            if (post == null) {
                return new JsonResponse(JsonError.Error.POST_NOT_EXISTS);
            }

            if (user == null || !user.getId().equals(post.getCreator().getId())) {
                return new JsonResponse(JsonError.Error.NO_ACCESS);
            }

            if (postUpdaterDAO.get(dto.getPostId(), dto.getUpdaterId()) != null) {
                return new JsonResponse(JsonError.Error.ALREADY_EXISTS);
            }

            final PostUpdater postUpdater = postUpdaterDAO.add(PostUpdater.Builder.aPostUpdater()
                    .setPost(dao.getById(dto.getPostId()))
                    .setUser(userDAO.getById(dto.getUpdaterId(), false))
                    .build());

            if (postUpdater == null) {
                return new JsonResponse(JsonError.Error.ADD_POST_UPDATER_FAIL);
            }
            return new JsonResponse(postUpdater);
        } catch (ClassNotFoundException | SQLException e) {
            logger.debug("Exception in addUpdater(" + dto.getPostId() + "," + dto.getUpdaterId() + ")", e);
            return new JsonResponse(JsonError.Error.DATABASE);
        }
    }

    @Override
    public JsonResponse create(NewEditPostDTO dto) {
        if (dto.getImagePart() != null && TextUtil.isEmpty(dto.getDirectoryPath())) {
            return new JsonResponse(JsonError.Error.IMAGE_INTERNAL);
        }

        if (TextUtil.isEmpty(dto.getPostTitle())) {
            return new JsonResponse(JsonError.Error.TITLE_EMPTY);
        } else if (dto.getPostTitle().length() > 64) {
            return new JsonResponse(JsonError.Error.TITLE_MAX_LENGTH);
        }

        if (TextUtil.isEmpty(dto.getPostDescription())) {
            return new JsonResponse(JsonError.Error.DESCRIPTION_EMPTY);
        } else if (dto.getPostDescription().length() > 128) {
            return new JsonResponse(JsonError.Error.DESCRIPTION_MAX_LENGTH);
        }

        if (TextUtil.isEmpty(dto.getPostText())) {
            return new JsonResponse(JsonError.Error.TEXT_EMPTY);
        } else if (dto.getPostText().length() > 2048) {
            return new JsonResponse(JsonError.Error.TEXT_MAX_LENGTH);
        }

        if (dto.getCookieUser() == null) {
            return new JsonResponse(JsonError.Error.NO_ACCESS);
        }

        User user;

        try {
            user = userDAO.getById(dto.getCookieUser().getId(), false);
        } catch (SQLException e) {
            logger.debug("Exception!", e);
            return new JsonResponse(JsonError.Error.DATABASE);
        }

        if (user == null) {
            return new JsonResponse(JsonError.Error.USER_NOT_EXISTS);
        }

        if (!User.Scope.WRITER.equals(user.getScope()) && !User.Scope.ADMIN.equals(user.getScope())) {
            return new JsonResponse(JsonError.Error.NO_ACCESS);
        }

        try {
            Integer imageId = null;

            if (dto.getImagePart() != null) {
                final String imageName = ImageServiceImpl.writeFile(dto.getImagePart(), dto.getDirectoryPath());
                imageId = imageDAO.add(Image.Builder.anImage()
                        .setSrc(imageName)
                        .build()).getId();

                if (imageId == null) {
                    return new JsonResponse(JsonError.Error.IMAGE_INTERNAL);
                }
            }
            return new JsonResponse(dao.add(Post.Builder.aPost()
                    .setTitle(dto.getPostTitle())
                    .setDescription(dto.getPostDescription())
                    .setText(dto.getPostText())
                    .setCreator(user)
                    .setCreatedAt(LocalDateTime.now())
                    .setImage(imageDAO.get(imageId, false))
                    .build()));
        } catch (ClassNotFoundException | SQLException e) {
            logger.debug("Exception with adding new post!", e);
            return new JsonResponse(JsonError.Error.DATABASE);
        } catch (IOException e) {
            logger.debug("Exception with writing the image!", e);
            return new JsonResponse(JsonError.Error.IMAGE_WRITING_FAIL);
        }
    }

    @Override
    public JsonResponse delete(DeletePostDTO dto) {
        if (dto.getPostId() == null || dto.getPostId() < 1) {
            return new JsonResponse(JsonError.Error.POST_ID_BAD);
        }

        try {
            final Post post = dao.getById(dto.getPostId());

            if (post == null) {
                return new JsonResponse(JsonError.Error.POST_NOT_EXISTS);
            }

            if (dto.getCookieUser() == null) {
                return new JsonResponse(JsonError.Error.NO_ACCESS);
            }

            User user;

            try {
                user = userDAO.getById(dto.getCookieUser().getId(), false);
            } catch (SQLException e) {
                logger.debug("Exception!", e);
                return new JsonResponse(JsonError.Error.DATABASE);
            }

            if (user == null) {
                return new JsonResponse(JsonError.Error.NO_ACCESS);
            }

            if (!post.getCreator().getId().equals(user.getId()) && !User.Scope.ADMIN.equals(user.getScope())) {
                return new JsonResponse(JsonError.Error.NO_ACCESS);
            }
            dao.delete(post);
            return new JsonResponse(JsonResponse.OK);
        } catch (ClassNotFoundException | SQLException e) {
            logger.debug("Exception with deleting post!", e);
            return new JsonResponse(JsonError.Error.DATABASE);
        }
    }

    @Override
    public JsonResponse edit(NewEditPostDTO dto) {
        if (dto == null || (TextUtil.isEmpty(dto.getPostTitle()) &&
                TextUtil.isEmpty(dto.getPostDescription()) &&
                TextUtil.isEmpty(dto.getPostText()))) {
            logger.debug("BAD UPDATE DATA");
            return new JsonResponse(JsonError.Error.UPDATE_DATA_BAD);
        }

        try {
            final Post dbPost = dao.getById(dto.getPostId());

            if (dbPost == null) {
                logger.debug("POST NOT EXISTS");
                return new JsonResponse(JsonError.Error.POST_NOT_EXISTS);
            }

            if (dto.getCookieUser() == null) {
                return new JsonResponse(JsonError.Error.NO_ACCESS);
            }

            if (!dbPost.getCreator().getId().equals(dto.getCookieUser().getId()) &&
                    postUpdaterDAO.get(dto.getPostId(), dto.getCookieUser().getId()) == null) {
                return new JsonResponse(JsonError.Error.NO_ACCESS);
            }

            if (!TextUtil.isEmpty(dto.getPostTitle())) {
                dbPost.setTitle(dto.getPostTitle());
            }

            if (!TextUtil.isEmpty(dto.getPostDescription())) {
                dbPost.setTitle(dto.getPostDescription());
            }

            if (!TextUtil.isEmpty(dto.getPostText())) {
                dbPost.setText(dto.getPostText());
            }
            dbPost.setUpdatedAt(LocalDateTime.now());
            return new JsonResponse(dao.edit(dbPost));
        } catch (ClassNotFoundException | SQLException e) {
            logger.debug("Exception with updating post!", e);
            return new JsonResponse(JsonError.Error.DATABASE);
        }
    }

    @Override
    public JsonResponse get(GetPostDTO dto) {
        if (dto.getPostId() == null || dto.getPostId() < 1) {
            return new JsonResponse(JsonError.Error.POST_ID_BAD);
        }

        if (dto.getCookieUser() == null) {
            return new JsonResponse(JsonError.Error.NO_ACCESS);
        }

        User user;
        Post post;

        try {
            user = userDAO.getById(dto.getCookieUser().getId(), true);
            post = dao.getById(dto.getPostId());
        } catch (SQLException e) {
            logger.debug("Exception with recognizing user OR getting post!", e);
            return new JsonResponse(JsonError.Error.DATABASE);
        } catch (ClassNotFoundException e) {
            logger.debug("Exception with getting post!", e);
            return new JsonResponse(JsonError.Error.DATABASE);
        }

        if (post == null) {
            return new JsonResponse(JsonError.Error.POST_NOT_EXISTS);
        }

        if (user == null || User.Scope.BANNED.equals(user.getScope()) ||
                (!post.isPosted() && !post.getCreator().getId().equals(user.getId()))) {
            return new JsonResponse(JsonError.Error.NO_ACCESS);
        }
        return new JsonResponse(post);
    }

    @Override
    public JsonResponse getAll(User requester) {
        if (requester == null) {
            return new JsonResponse(JsonError.Error.NO_ACCESS);
        }

        try {
            final User user = userDAO.getById(requester.getId(), true);

            if (user == null || User.Scope.BANNED.equals(user.getScope()) ||
                    !user.getPassword().equals(requester.getPassword())) {
                return new JsonResponse(JsonError.Error.NO_ACCESS);
            }
            return new JsonResponse(dao.getAllPosts().stream()
                    .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                    .filter(p -> p.isPosted() || p.getCreator().getId().equals(user.getId()))
                    .collect(Collectors.toList()));
        } catch (ClassNotFoundException | SQLException e) {
            logger.debug("Exception with getting all posts!", e);
            return new JsonResponse(JsonError.Error.DATABASE);
        }
    }

    @Override
    public JsonResponse getByUser(GetPostDTO dto) {
        if (dto.getCookieUser() == null) {
            logger.debug("NO ACCESS 1");
            return new JsonResponse(JsonError.Error.NO_ACCESS);
        }

        try {
            final User requester = userDAO.getById(dto.getCookieUser().getId(), true);

            if (requester == null || User.Scope.BANNED.equals(requester.getScope()) ||
                    !requester.getPassword().equals(dto.getCookieUser().getPassword())) {
                return new JsonResponse(JsonError.Error.NO_ACCESS);
            }

            final User creator = userDAO.getById(dto.getUserId(), false);

            if (creator == null) {
                return new JsonResponse(JsonError.Error.USER_NOT_EXISTS);
            }
            return new JsonResponse(dao.getByCreator(creator).stream()
                    .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                    .filter(p -> p.isPosted() || (p.getCreator().getId().equals(requester.getId())))
                    .collect(Collectors.toList()));
        } catch (ClassNotFoundException | SQLException e) {
            logger.debug("Exception with getting all posts!", e);
            return new JsonResponse(JsonError.Error.DATABASE);
        }
    }

    @Override
    public JsonResponse getUsersWithAccess(GetPostDTO dto) {
        if (dto.getCookieUser() == null || dto.getCookieUser().getId() < 1) {
            return new JsonResponse(JsonError.Error.NO_ACCESS);
        }

        if (dto.getPostId() == null || dto.getPostId() < 1) {
            return new JsonResponse(JsonError.Error.POST_ID_BAD);
        }

        try {
            User user = userDAO.getById(dto.getCookieUser().getId(), true);

            if (user == null || User.Scope.BANNED.equals(user.getScope()) ||
                    !user.getPassword().equals(dto.getCookieUser().getPassword())) {
                return new JsonResponse(JsonError.Error.NO_ACCESS);
            }

            Post post = dao.getById(dto.getPostId());

            if (post == null) {
                return new JsonResponse(JsonError.Error.POST_NOT_EXISTS);
            }

            List<User> users = userDAO.getAllUsers(false);
            List<PostUpdater> postUpdaters = postUpdaterDAO.getByPost(post);
            List<PostUpdaterDTO> result = new ArrayList<>();

            users.forEach(v -> result.add(PostUpdaterDTO.Builder.aPostUpdaterDTO()
                    .setUserId(v.getId())
                    .setUserLogin(v.getLogin())
                    .setPostUpdater(postUpdaters.stream().anyMatch(p -> p.getUser().getId().equals(v.getId())))
                    .build()));
            return new JsonResponse(result);
        } catch (ClassNotFoundException | SQLException e) {
            logger.debug("Exception!", e);
            return new JsonResponse(JsonError.Error.DATABASE);
        }
    }

    @Override
    public JsonResponse revokeUpdater(EditUpdaterDTO dto) {
        try {
            if (dto.getCookieUser() == null) {
                return new JsonResponse(JsonError.Error.NO_ACCESS);
            }

            if (dto.getPostId() == null || dto.getPostId() < 1) {
                return new JsonResponse(JsonError.Error.POST_ID_BAD);
            }

            if (dto.getUpdaterId() == null || dto.getUpdaterId() < 1) {
                return new JsonResponse(JsonError.Error.UPDATER_ID_BAD);
            }

            User user = userDAO.getById(dto.getCookieUser().getId(), true);
            Post post = dao.getById(dto.getPostId());

            if (post == null) {
                return new JsonResponse(JsonError.Error.POST_NOT_EXISTS);
            }

            if (user == null || !user.getId().equals(post.getCreator().getId())) {
                return new JsonResponse(JsonError.Error.NO_ACCESS);
            }

            PostUpdater postUpdater = postUpdaterDAO.get(dto.getPostId(), dto.getUpdaterId());

            if (postUpdater == null) {
                return new JsonResponse(JsonError.Error.POST_HAVE_NOT_UPDATER);
            }
            postUpdaterDAO.delete(postUpdater);
            return new JsonResponse(JsonResponse.OK);
        } catch (ClassNotFoundException | SQLException e) {
            logger.debug("Exception with revoking post updater!", e);
            return new JsonResponse(JsonError.Error.DATABASE);
        }
    }

    @Override
    public JsonResponse search(String word, User requester) {
        if (requester == null) {
            return new JsonResponse(JsonError.Error.NO_ACCESS);
        }

        try {
            final User user = userDAO.getById(requester.getId(), true);

            if (user == null || User.Scope.BANNED.equals(user.getScope()) ||
                    !user.getPassword().equals(requester.getPassword())) {
                return new JsonResponse(JsonError.Error.NO_ACCESS);
            }
            return new JsonResponse(dao.getBySubstring(word).stream()
                    .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                    .filter(p -> p.isPosted() || p.getCreator().getId().equals(user.getId()))
                    .collect(Collectors.toList()));
        } catch (ClassNotFoundException | SQLException e) {
            logger.debug("Exception with getting all posts!", e);
            return new JsonResponse(JsonError.Error.DATABASE);
        }
    }

    @Override
    public JsonResponse setPosted(SetPostedDTO dto) {
        if (dto.getPostId() == null || dto.getPostId() < 1) {
            return new JsonResponse(JsonError.Error.POST_ID_BAD);
        }

        if (dto.getCookieUser() == null) {
            return new JsonResponse(JsonError.Error.NO_ACCESS);
        }

        try {
            Post post = dao.getById(dto.getPostId());

            if (post == null) {
                return new JsonResponse(JsonError.Error.POST_NOT_EXISTS);
            }

            if (dto.getCookieUser() == null || dto.getCookieUser().getId() < 1 ||
                    !post.getCreator().getId().equals(dto.getCookieUser().getId())) {
                return new JsonResponse(JsonError.Error.NO_ACCESS);
            }

            User user = userDAO.getById(dto.getCookieUser().getId(), true);

            if (user == null || User.Scope.BANNED.equals(user.getScope()) ||
                    !user.getPassword().equals(dto.getCookieUser().getPassword())) {
                return new JsonResponse(JsonError.Error.NO_ACCESS);
            }
            post.setPosted(dto.isPostPosted());
            return new JsonResponse(dao.edit(post));
        } catch (ClassNotFoundException | SQLException e) {
            logger.debug("Exception with setting post's `is_posted`!", e);
            return new JsonResponse(JsonError.Error.DATABASE);
        }
    }
}
