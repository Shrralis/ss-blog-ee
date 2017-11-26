package com.shrralis.ssblog.service;

import com.shrralis.ssblog.dao.PostJdbcDAOImpl;
import com.shrralis.ssblog.dao.PostUpdaterJdbcDAOImpl;
import com.shrralis.ssblog.dao.UserJdbcDAOImpl;
import com.shrralis.ssblog.dao.interfaces.IPostDAO;
import com.shrralis.ssblog.dao.interfaces.IPostUpdaterDAO;
import com.shrralis.ssblog.dao.interfaces.IUserDAO;
import com.shrralis.ssblog.entity.Post;
import com.shrralis.ssblog.entity.PostUpdater;
import com.shrralis.ssblog.service.interfaces.IPostService;
import com.shrralis.tools.TextUtil;
import com.shrralis.tools.model.JsonError;
import com.shrralis.tools.model.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Calendar;

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
    public JsonResponse addUpdater(Integer postId, Integer newUpdaterId) {
        try {
            if (postUpdaterDAO.get(newUpdaterId, postId) != null) {
                return new JsonResponse(JsonError.Error.ALREADY_EXISTS);
            }

            PostUpdater postUpdater = new PostUpdater.Builder()
                    .setPost(dao.getById(postId))
                    .setUser(userDAO.getById(newUpdaterId, false))
                    .build();

            if (postUpdater.getPost() == null) {
                return new JsonResponse(JsonError.Error.POST_NOT_EXISTS);
            }

            if (postUpdater.getUser() == null) {
                return new JsonResponse(JsonError.Error.USER_NOT_EXISTS);
            }

            postUpdater = postUpdaterDAO.add(postUpdater);

            if (postUpdater == null) {
                return new JsonResponse(JsonError.Error.ADD_POST_UPDATER_FAIL);
            }
            return new JsonResponse(postUpdater);
        } catch (ClassNotFoundException | SQLException e) {
            logger.debug("Exception in addUpdater(" + postId + "," + newUpdaterId + ")", e);
            return new JsonResponse(JsonError.Error.DATABASE);
        }
    }

    @Override
    public JsonResponse create(Post post) {
        if (TextUtil.isEmpty(post.getTitle())) {
            return new JsonResponse(JsonError.Error.EMPTY_TITLE);
        } else if (post.getTitle().length() > 64) {
            return new JsonResponse(JsonError.Error.MAX_LENGTH_TITLE);
        }

        if (TextUtil.isEmpty(post.getDescription())) {
            return new JsonResponse(JsonError.Error.EMPTY_DESCRIPTION);
        } else if (post.getDescription().length() > 128) {
            return new JsonResponse(JsonError.Error.MAX_LENGTH_DESCRIPTION);
        }

        if (TextUtil.isEmpty(post.getText())) {
            return new JsonResponse(JsonError.Error.EMPTY_TEXT);
        } else if (post.getText().length() > 2048) {
            return new JsonResponse(JsonError.Error.MAX_LENGTH_TEXT);
        }

        if (post.getCreator() == null) {
            return new JsonResponse(JsonError.Error.USER_NOT_EXISTS);
        }

        if (post.getCreatedAt() == null) {
            post.setCreatedAt(Calendar.getInstance().getTime());
        }

        try {
            return new JsonResponse(dao.add(post));
        } catch (ClassNotFoundException | SQLException e) {
            logger.debug("Exception with adding new post!");
            return new JsonResponse(JsonError.Error.DATABASE);
        }
    }

    @Override
    public JsonResponse delete(Integer postId, Integer userId) {
        if (postId == null || postId < 1) {
            return new JsonResponse(JsonError.Error.BAD_POST_ID);
        }

        try {
            Post post = dao.getById(postId);

            if (post == null) {
                return new JsonResponse(JsonError.Error.POST_NOT_EXISTS);
            }

            if (!post.getCreator().getId().equals(userId)) {
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
    public JsonResponse edit(Post post, Integer userId) {
        if (post == null || (TextUtil.isEmpty(post.getTitle()) &&
                TextUtil.isEmpty(post.getDescription()) &&
                TextUtil.isEmpty(post.getText()))) {
            return new JsonResponse(JsonError.Error.BAD_UPDATE_DATA);
        }

        try {
            Post dbPost = dao.getById(post.getId());

            if (dbPost == null) {
                return new JsonResponse(JsonError.Error.POST_NOT_EXISTS);
            }

            if (!dbPost.getCreator().getId().equals(userId) &&
                    postUpdaterDAO.get(userId, post.getId()) == null) {
                return new JsonResponse(JsonError.Error.NO_ACCESS);
            }

            if (!TextUtil.isEmpty(post.getTitle())) {
                dbPost.setTitle(post.getTitle());
            }

            if (!TextUtil.isEmpty(post.getDescription())) {
                dbPost.setTitle(post.getDescription());
            }

            if (!TextUtil.isEmpty(post.getText())) {
                dbPost.setText(post.getText());
            }
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
    public JsonResponse getAll() {
        try {
            return new JsonResponse(dao.getAllPosts());
        } catch (ClassNotFoundException | SQLException e) {
            logger.debug("Exception with getting all posts!", e);
            return new JsonResponse(JsonError.Error.DATABASE);
        }
    }

    @Override
    public JsonResponse revokeUpdater(Integer postId, Integer updaterId, Integer revokerId) {
        if (postId == null || postId < 1) {
            return new JsonResponse(JsonError.Error.BAD_POST_ID);
        }

        if (updaterId == null || updaterId < 1) {
            return new JsonResponse(JsonError.Error.BAD_UPDATER_ID);
        }

        try {
            Post post = dao.getById(postId);

            if (post == null) {
                return new JsonResponse(JsonError.Error.POST_NOT_EXISTS);
            }

            if (revokerId == null || revokerId < 1 || !revokerId.equals(post.getCreator().getId())) {
                return new JsonResponse(JsonError.Error.NO_ACCESS);
            }

            PostUpdater postUpdater = postUpdaterDAO.get(updaterId, postId);

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
    public JsonResponse setPosted(Integer postId, boolean isPosted, Integer userId) {
        if (postId == null || postId < 1) {
            return new JsonResponse(JsonError.Error.BAD_POST_ID);
        }

        try {
            Post post = dao.getById(postId);

            if (post == null) {
                return new JsonResponse(JsonError.Error.POST_NOT_EXISTS);
            }

            if (userId == null || userId < 1 || !userId.equals(post.getCreator().getId())) {
                return new JsonResponse(JsonError.Error.NO_ACCESS);
            }
            post.setPosted(isPosted);
            return new JsonResponse(dao.edit(post));
        } catch (ClassNotFoundException | SQLException e) {
            logger.debug("Exception with setting post's `is_posted`!", e);
            return new JsonResponse(JsonError.Error.DATABASE);
        }
    }
}
