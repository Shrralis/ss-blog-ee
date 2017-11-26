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

        }

        try {
            Post post = dao.getById(postId);

            if (post == null) {

            }

            if (!post.getCreator().getId().equals(userId)) {

            }
            return null;
        } catch (ClassNotFoundException | SQLException e) {

        }
    }

    @Override
    public JsonResponse edit(Post post, Integer userId) {
        if (post == null || (TextUtil.isEmpty(post.getTitle()) &&
                TextUtil.isEmpty(post.getDescription()) &&
                TextUtil.isEmpty(post.getText()))) {

        }

        try {
            Post dbPost = dao.getById(post.getId());

            if (dbPost == null) {

            }

            if (!dbPost.getCreator().getId().equals(userId) &&
                    postUpdaterDAO.get(userId, post.getId()) == null) {

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

        }
    }

    @Override
    public JsonResponse get(Integer postId) {
        return null;
    }

    @Override
    public JsonResponse getAll() {
        return null;
    }

    @Override
    public JsonResponse revokeUpdater(Integer postId, Integer updaterId) {
        return null;
    }

    @Override
    public JsonResponse setPosted(Integer postId, boolean isPosted) {
        return null;
    }
}
