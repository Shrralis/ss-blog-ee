package com.shrralis.ssblog.dao;

import com.shrralis.ssblog.dao.base.JdbcBasedDAO;
import com.shrralis.ssblog.dao.interfaces.IPostUpdaterDAO;
import com.shrralis.ssblog.entity.Post;
import com.shrralis.ssblog.entity.PostUpdater;
import com.shrralis.ssblog.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostUpdaterJdbcDAOImpl extends JdbcBasedDAO implements IPostUpdaterDAO {
    private static final String POST_ID_COLUMN_NAME = "post_id";
    private static final String USER_ID_COLUMN_NAME = "user_id";

    private static PostUpdaterJdbcDAOImpl dao;

    private PostUpdaterJdbcDAOImpl() throws ClassNotFoundException, SQLException {
        super();
    }

    public static PostUpdaterJdbcDAOImpl getDao() throws ClassNotFoundException, SQLException {
        if (dao == null) {
            dao = new PostUpdaterJdbcDAOImpl();
        }
        return dao;
    }

    @Override
    public PostUpdater add(PostUpdater postUpdater) throws SQLException {
        PreparedStatement preparedStatement = getConnection()
                .prepareStatement("INSERT INTO posts_updaters (post_id, user_id) VALUES (?, ?)");

        preparedStatement.setInt(1, postUpdater.getPost().getId());
        preparedStatement.setInt(2, postUpdater.getUser().getId());

        if (preparedStatement.executeUpdate() == 0) {
            throw new SQLException("Creation PostUpdater failed, no rows affected.");
        }
        return postUpdater;
    }

    @Override
    public void delete(PostUpdater postUpdater) throws SQLException {
        PreparedStatement preparedStatement = getConnection()
                .prepareStatement("DELETE FROM posts_updaters WHERE post_id = ? AND user_id = ?");

        preparedStatement.setInt(1, postUpdater.getPost().getId());
        preparedStatement.setInt(2, postUpdater.getUser().getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public PostUpdater get(Integer postId, Integer userId) throws ClassNotFoundException, SQLException {
        PreparedStatement preparedStatement = getConnection()
                .prepareStatement("SELECT * FROM posts_updaters WHERE post_id = ? AND user_id = ?");

        preparedStatement.setInt(1, postId);
        preparedStatement.setInt(2, userId);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return new PostUpdater.Builder()
                    .setPost(PostJdbcDAOImpl.getDao().getById(resultSet.getInt(POST_ID_COLUMN_NAME)))
                    .setUser(UserJdbcDAOImpl.getDao().getById(resultSet.getInt(USER_ID_COLUMN_NAME)))
                    .build();
        }
        return null;
    }

    @Override
    public List<PostUpdater> getAllPostsUpdaters() throws ClassNotFoundException, SQLException {
        ArrayList<PostUpdater> result = new ArrayList<>();
        PreparedStatement preparedStatement = getConnection()
                .prepareStatement("SELECT * FROM posts_updaters");
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            result.add(new PostUpdater.Builder()
                    .setPost(PostJdbcDAOImpl.getDao().getById(resultSet.getInt(POST_ID_COLUMN_NAME)))
                    .setUser(UserJdbcDAOImpl.getDao().getById(resultSet.getInt(USER_ID_COLUMN_NAME)))
                    .build());
        }
        return result;
    }

    @Override
    public List<PostUpdater> getByPost(Post post) throws ClassNotFoundException, SQLException {
        ArrayList<PostUpdater> result = new ArrayList<>();

        if (post == null) {
            return result;
        }

        PreparedStatement preparedStatement = getConnection()
                .prepareStatement("SELECT * FROM posts_updaters WHERE post_id = ?");

        preparedStatement.setInt(1, post.getId());

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            result.add(new PostUpdater.Builder()
                    .setPost(PostJdbcDAOImpl.getDao().getById(resultSet.getInt(POST_ID_COLUMN_NAME)))
                    .setUser(UserJdbcDAOImpl.getDao().getById(resultSet.getInt(USER_ID_COLUMN_NAME)))
                    .build());
        }
        return result;
    }

    @Override
    public List<PostUpdater> getByUser(User user) throws ClassNotFoundException, SQLException {
        ArrayList<PostUpdater> result = new ArrayList<>();

        if (user == null) {
            return result;
        }

        PreparedStatement preparedStatement = getConnection()
                .prepareStatement("SELECT * FROM posts_updaters WHERE user_id = ?");

        preparedStatement.setInt(1, user.getId());

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            result.add(new PostUpdater.Builder()
                    .setPost(PostJdbcDAOImpl.getDao().getById(resultSet.getInt(POST_ID_COLUMN_NAME)))
                    .setUser(UserJdbcDAOImpl.getDao().getById(resultSet.getInt(USER_ID_COLUMN_NAME)))
                    .build());
        }
        return result;
    }
}
