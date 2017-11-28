package com.shrralis.ssblog.service;

import com.shrralis.ssblog.dao.PostJdbcDAOImpl;
import com.shrralis.ssblog.dao.PostUpdaterJdbcDAOImpl;
import com.shrralis.ssblog.dao.UserJdbcDAOImpl;
import com.shrralis.ssblog.dao.interfaces.IPostDAO;
import com.shrralis.ssblog.dao.interfaces.IPostUpdaterDAO;
import com.shrralis.ssblog.dao.interfaces.IUserDAO;
import com.shrralis.ssblog.dto.*;
import com.shrralis.ssblog.entity.Post;
import com.shrralis.ssblog.entity.PostUpdater;
import com.shrralis.ssblog.entity.User;
import com.shrralis.ssblog.service.interfaces.IPostService;
import com.shrralis.tools.TextUtil;
import com.shrralis.tools.model.JsonError;
import com.shrralis.tools.model.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public PostServiceImpl() throws ClassNotFoundException, SQLException {
        dao = PostJdbcDAOImpl.getDao();
        postUpdaterDAO = PostUpdaterJdbcDAOImpl.getDao();
        userDAO = UserJdbcDAOImpl.getDao();
    }

    @Override
    public JsonResponse addUpdater(EditUpdaterDTO dto) {
        try {
            if (dto.getCookieUser() == null) {
                return new JsonResponse(JsonError.Error.NO_ACCESS);
            }

            if (dto.getPostId() == null || dto.getPostId() < 1) {
                return new JsonResponse(JsonError.Error.BAD_POST_ID);
            }

            if (dto.getUpdaterId() == null || dto.getUpdaterId() < 1) {
                return new JsonResponse(JsonError.Error.BAD_UPDATER_ID);
            }

            User user = userDAO.getById(dto.getCookieUser().getId(), true);
            Post post = dao.getById(dto.getPostId());

            if (post == null) {
                return new JsonResponse(JsonError.Error.POST_NOT_EXISTS);
            }

            if (user == null || !user.getId().equals(post.getCreator().getId())) {
                return new JsonResponse(JsonError.Error.NO_ACCESS);
            }

            if (postUpdaterDAO.get(dto.getPostId(), dto.getUpdaterId()) != null) {
                return new JsonResponse(JsonError.Error.ALREADY_EXISTS);
            }

            PostUpdater postUpdater = postUpdaterDAO.add(new PostUpdater.Builder()
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
    public JsonResponse create(NewEditPostDTO postDTO) {
        if (TextUtil.isEmpty(postDTO.getPostTitle())) {
            return new JsonResponse(JsonError.Error.EMPTY_TITLE);
        } else if (postDTO.getPostTitle().length() > 64) {
            return new JsonResponse(JsonError.Error.MAX_LENGTH_TITLE);
        }

        if (TextUtil.isEmpty(postDTO.getPostDescription())) {
            return new JsonResponse(JsonError.Error.EMPTY_DESCRIPTION);
        } else if (postDTO.getPostDescription().length() > 128) {
            return new JsonResponse(JsonError.Error.MAX_LENGTH_DESCRIPTION);
        }

        if (TextUtil.isEmpty(postDTO.getPostText())) {
            return new JsonResponse(JsonError.Error.EMPTY_TEXT);
        } else if (postDTO.getPostText().length() > 2048) {
            return new JsonResponse(JsonError.Error.MAX_LENGTH_TEXT);
        }

        if (postDTO.getCookieUser() == null) {
            return new JsonResponse(JsonError.Error.NO_ACCESS);
        }

        User user;

        try {
            user = userDAO.getById(postDTO.getCookieUser().getId(), false);
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
            return new JsonResponse(dao.add(new Post.Builder()
                    .setTitle(postDTO.getPostTitle())
                    .setDescription(postDTO.getPostDescription())
                    .setText(postDTO.getPostText())
                    .setCreator(user)
                    .setCreatedAt(LocalDateTime.now())
                    .build()));
        } catch (ClassNotFoundException | SQLException e) {
            logger.debug("Exception with adding new post!", e);
            return new JsonResponse(JsonError.Error.DATABASE);
        }
    }

    @Override
    public JsonResponse delete(DeletePostDTO postDTO) {
        if (postDTO.getPostId() == null || postDTO.getPostId() < 1) {
            return new JsonResponse(JsonError.Error.BAD_POST_ID);
        }

        try {
            Post post = dao.getById(postDTO.getPostId());

            if (post == null) {
                return new JsonResponse(JsonError.Error.POST_NOT_EXISTS);
            }

            if (postDTO.getCookieUser() == null) {
                return new JsonResponse(JsonError.Error.NO_ACCESS);
            }

            User user;

            try {
                user = userDAO.getById(postDTO.getCookieUser().getId(), false);
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
    public JsonResponse edit(NewEditPostDTO postDTO) {
        if (postDTO == null || (TextUtil.isEmpty(postDTO.getPostTitle()) &&
                TextUtil.isEmpty(postDTO.getPostDescription()) &&
                TextUtil.isEmpty(postDTO.getPostText()))) {
            logger.debug("BAD UPDATE DATA");
            return new JsonResponse(JsonError.Error.BAD_UPDATE_DATA);
        }

        try {
            Post dbPost = dao.getById(postDTO.getPostId());

            if (dbPost == null) {
                logger.debug("POST NOT EXISTS");
                return new JsonResponse(JsonError.Error.POST_NOT_EXISTS);
            }

            if (postDTO.getCookieUser() == null) {
                logger.debug("NO ACCESS");
                return new JsonResponse(JsonError.Error.NO_ACCESS);
            }

            if (!dbPost.getCreator().getId().equals(postDTO.getCookieUser().getId()) &&
                    postUpdaterDAO.get(postDTO.getPostId(), postDTO.getCookieUser().getId()) == null) {
                logger.debug("NO ACCESS 1");
                return new JsonResponse(JsonError.Error.NO_ACCESS);
            }

            if (!TextUtil.isEmpty(postDTO.getPostTitle())) {
                dbPost.setTitle(postDTO.getPostTitle());
            }

            if (!TextUtil.isEmpty(postDTO.getPostDescription())) {
                dbPost.setTitle(postDTO.getPostDescription());
            }

            if (!TextUtil.isEmpty(postDTO.getPostText())) {
                dbPost.setText(postDTO.getPostText());
            }
            dbPost.setUpdatedAt(LocalDateTime.now());
            return new JsonResponse(dao.edit(dbPost));
        } catch (ClassNotFoundException | SQLException e) {
            logger.debug("Exception with updating post!", e);
            return new JsonResponse(JsonError.Error.DATABASE);
        }
    }

    @Override
    public JsonResponse get(Integer postId) {
        if (postId == null || postId < 1) {
            return new JsonResponse(JsonError.Error.BAD_POST_ID);
        }

        try {
            return new JsonResponse(dao.getById(postId));
        } catch (ClassNotFoundException | SQLException e) {
            logger.debug("Exception with getting post by `id`!", e);
            return new JsonResponse(JsonError.Error.DATABASE);
        }
    }

    @Override
    public JsonResponse getAll(User user) {
        try {
            User tempUser = user == null ? null : userDAO.getById(user.getId(), true);
            final User u1 = tempUser == null ? null :
                    (tempUser.getPassword().equals(user.getPassword()) ? tempUser : null);

            return new JsonResponse(dao.getAllPosts().stream()
                    .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                    .filter(p -> p.isPosted() || (u1 != null && p.getCreator().getId().equals(u1.getId())))
                    .collect(Collectors.toList()));
        } catch (ClassNotFoundException | SQLException e) {
            logger.debug("Exception with getting all posts!", e);
            return new JsonResponse(JsonError.Error.DATABASE);
        }
    }

    @Override
    public JsonResponse getUsersWithAccess(Integer postId, User user) {
        if (user == null || user.getId() < 1) {
            return new JsonResponse(JsonError.Error.NO_ACCESS);
        }

        if (postId == null || postId < 1) {
            return new JsonResponse(JsonError.Error.BAD_POST_ID);
        }

        try {
            User u = userDAO.getById(user.getId(), true);

            if (u == null || !u.getPassword().equals(user.getPassword())) {
                return new JsonResponse(JsonError.Error.NO_ACCESS);
            }

            Post post = dao.getById(postId);

            if (post == null) {
                return new JsonResponse(JsonError.Error.POST_NOT_EXISTS);
            }

            List<User> users = userDAO.getAllUsers(false);
            List<PostUpdater> postUpdaters = postUpdaterDAO.getByPost(post);
            List<PostUpdaterDTO> result = new ArrayList<>();

            users.forEach(v -> result.add(new PostUpdaterDTO.Builder()
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
                return new JsonResponse(JsonError.Error.BAD_POST_ID);
            }

            if (dto.getUpdaterId() == null || dto.getUpdaterId() < 1) {
                return new JsonResponse(JsonError.Error.BAD_UPDATER_ID);
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
    public JsonResponse setPosted(SetPostedDTO postedDTO) {
        if (postedDTO == null || postedDTO.getPostId() == null || postedDTO.getPostId() < 1) {
            return new JsonResponse(JsonError.Error.BAD_POST_ID);
        }

        try {
            Post post = dao.getById(postedDTO.getPostId());

            if (post == null) {
                return new JsonResponse(JsonError.Error.POST_NOT_EXISTS);
            }

            if (postedDTO.getCookieUser() == null || postedDTO.getCookieUser().getId() < 1) {
                return new JsonResponse(JsonError.Error.NO_ACCESS);
            }

            User user = userDAO.getById(postedDTO.getCookieUser().getId(), true);

            if (user == null || !user.getPassword().equals(postedDTO.getCookieUser().getPassword())) {
                return new JsonResponse(JsonError.Error.NO_ACCESS);
            }
            post.setPosted(postedDTO.isPostPosted());
            return new JsonResponse(dao.edit(post));
        } catch (ClassNotFoundException | SQLException e) {
            logger.debug("Exception with setting post's `is_posted`!", e);
            return new JsonResponse(JsonError.Error.DATABASE);
        }
    }
}
