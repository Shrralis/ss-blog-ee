package com.shrralis.ssblog.dao;

import com.shrralis.ssblog.dao.base.JdbcBasedDAO;
import com.shrralis.ssblog.dao.interfaces.IPostDAO;
import com.shrralis.ssblog.entity.Post;
import com.shrralis.ssblog.entity.User;
import com.shrralis.tools.DateUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostJdbcDAOImpl extends JdbcBasedDAO implements IPostDAO {
    private static final String ID_COLUMN_NAME = "id";
    private static final String TITLE_COLUMN_NAME = "title";
    private static final String DESCRIPTION_COLUMN_NAME = "description";
    private static final String TEXT_COLUMN_NAME = "text";
    private static final String IS_POSTED_COLUMN_NAME = "is_posted";
    private static final String CREATOR_ID_COLUMN_NAME = "creator_id";
    private static final String CREATED_AT_COLUMN_NAME = "created_at";
    private static final String UPDATED_AT_COLUMN_NAME = "updated_at";

    private static PostJdbcDAOImpl dao;

    private PostJdbcDAOImpl() throws ClassNotFoundException, SQLException {
        super();
    }

    public static PostJdbcDAOImpl getDao() throws ClassNotFoundException, SQLException {
        if (dao == null) {
            dao = new PostJdbcDAOImpl();
        }
        return dao;
    }

    @Override
    public Post add(Post post) throws ClassNotFoundException, SQLException {
        PreparedStatement preparedStatement = getConnection()
                .prepareStatement("INSERT INTO posts (title, description, text, is_posted, creator_id, created_at)" +
                        "VALUES (?, ?, ?, ?, ?, ?)");

        preparedStatement.setString(1, post.getTitle());
        preparedStatement.setString(2, post.getDescription());
        preparedStatement.setString(3, post.getText());
        preparedStatement.setBoolean(4, post.isPosted());
        preparedStatement.setInt(5, post.getCreator().getId());
        preparedStatement.setString(6, DateUtil.toMySQLDateTimeString(post.getCreatedAt()));

        if (preparedStatement.executeUpdate() == 0) {
            throw new SQLException("Creating user failed, no rows affected.");
        }

        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                post.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }
        return post;
    }

    @Override
    public void delete(Post post) throws SQLException {
        PreparedStatement preparedStatement = getConnection()
                .prepareStatement("DELETE FROM posts WHERE id = ?");

        preparedStatement.setInt(1, post.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public Post edit(Post post) throws ClassNotFoundException, SQLException {
        if (post == null) {
            return null;
        }

        PreparedStatement preparedStatement = getConnection()
                .prepareStatement("UPDATE posts SET title = ?, description = ?, text = ?, is_posted = ?," +
                        "creator_id = ?, created_at = ?, updated_at = ? WHERE id = ?");

        preparedStatement.setString(1, post.getTitle());
        preparedStatement.setString(2, post.getDescription());
        preparedStatement.setString(3, post.getText());
        preparedStatement.setBoolean(4, post.isPosted());
        preparedStatement.setInt(5, post.getCreator().getId());
        preparedStatement.setString(6, DateUtil.toMySQLDateTimeString(post.getCreatedAt()));
        preparedStatement.setString(7, DateUtil.toMySQLDateTimeString(post.getUpdatedAt()));
        preparedStatement.setInt(8, post.getId());
        preparedStatement.executeUpdate();
        return getById(post.getId());
    }

    @Override
    public List<Post> getAllPosts() throws ClassNotFoundException, SQLException {
        ArrayList<Post> result = new ArrayList<>();
        PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM posts");
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            result.add(new Post.Builder()
                    .setId(resultSet.getInt(ID_COLUMN_NAME))
                    .setTitle(resultSet.getString(TITLE_COLUMN_NAME))
                    .setDescription(resultSet.getString(DESCRIPTION_COLUMN_NAME))
                    .setText(resultSet.getString(TEXT_COLUMN_NAME))
                    .setPosted(resultSet.getBoolean(IS_POSTED_COLUMN_NAME))
                    .setCreator(UserJdbcDAOImpl.getDao().getById(resultSet.getInt(CREATOR_ID_COLUMN_NAME)))
                    .setCreatedAt(DateUtil.fromMySQLDateTimeString(resultSet.getString(CREATED_AT_COLUMN_NAME)))
                    .setUpdatedAt(DateUtil.fromMySQLDateTimeString(resultSet.getString(UPDATED_AT_COLUMN_NAME)))
                    .build());
        }
        return result;
    }

    @Override
    public List<Post> getByCreator(User creator) throws ClassNotFoundException, SQLException {
        ArrayList<Post> result = new ArrayList<>();

        if (creator == null) {
            return result;
        }

        PreparedStatement preparedStatement = getConnection()
                .prepareStatement("SELECT * FROM posts WHERE creator_id = ?");

        preparedStatement.setInt(1, creator.getId());

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            result.add(new Post.Builder()
                    .setId(resultSet.getInt(ID_COLUMN_NAME))
                    .setTitle(resultSet.getString(TITLE_COLUMN_NAME))
                    .setDescription(resultSet.getString(DESCRIPTION_COLUMN_NAME))
                    .setText(resultSet.getString(TEXT_COLUMN_NAME))
                    .setPosted(resultSet.getBoolean(IS_POSTED_COLUMN_NAME))
                    .setCreator(UserJdbcDAOImpl.getDao().getById(resultSet.getInt(CREATOR_ID_COLUMN_NAME)))
                    .setCreatedAt(DateUtil.fromMySQLDateTimeString(resultSet.getString(CREATED_AT_COLUMN_NAME)))
                    .setUpdatedAt(DateUtil.fromMySQLDateTimeString(resultSet.getString(UPDATED_AT_COLUMN_NAME)))
                    .build());
        }        return result;
    }

    @Override
    public Post getById(Integer id) throws ClassNotFoundException, SQLException {
        PreparedStatement preparedStatement = getConnection()
                .prepareStatement("SELECT * FROM posts WHERE id = ?");

        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return new Post.Builder()
                    .setId(resultSet.getInt(ID_COLUMN_NAME))
                    .setTitle(resultSet.getString(TITLE_COLUMN_NAME))
                    .setDescription(resultSet.getString(DESCRIPTION_COLUMN_NAME))
                    .setText(resultSet.getString(TEXT_COLUMN_NAME))
                    .setPosted(resultSet.getBoolean(IS_POSTED_COLUMN_NAME))
                    .setCreator(UserJdbcDAOImpl.getDao().getById(resultSet.getInt(CREATOR_ID_COLUMN_NAME)))
                    .setCreatedAt(DateUtil.fromMySQLDateTimeString(resultSet.getString(CREATED_AT_COLUMN_NAME)))
                    .setUpdatedAt(DateUtil.fromMySQLDateTimeString(resultSet.getString(UPDATED_AT_COLUMN_NAME)))
                    .build();
        }
        return null;
    }

    @Override
    public List<Post> getByIsPosted(boolean isPosted) throws ClassNotFoundException, SQLException {
        ArrayList<Post> result = new ArrayList<>();
        PreparedStatement preparedStatement = getConnection()
                .prepareStatement("SELECT * FROM posts WHERE is_posted = ?");

        preparedStatement.setBoolean(1, isPosted);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            result.add(new Post.Builder()
                    .setId(resultSet.getInt(ID_COLUMN_NAME))
                    .setTitle(resultSet.getString(TITLE_COLUMN_NAME))
                    .setDescription(resultSet.getString(DESCRIPTION_COLUMN_NAME))
                    .setText(resultSet.getString(TEXT_COLUMN_NAME))
                    .setPosted(resultSet.getBoolean(IS_POSTED_COLUMN_NAME))
                    .setCreator(UserJdbcDAOImpl.getDao().getById(resultSet.getInt(CREATOR_ID_COLUMN_NAME)))
                    .setCreatedAt(DateUtil.fromMySQLDateTimeString(resultSet.getString(CREATED_AT_COLUMN_NAME)))
                    .setUpdatedAt(DateUtil.fromMySQLDateTimeString(resultSet.getString(UPDATED_AT_COLUMN_NAME)))
                    .build());
        }
        return result;
    }

    @Override
    public List<Post> getBySubstring(String substring) throws ClassNotFoundException, SQLException {
        ArrayList<Post> result = new ArrayList<>();
        PreparedStatement preparedStatement = getConnection()
                .prepareStatement("SELECT * FROM posts WHERE title LIKE ? OR description LIKE ? OR text LIKE ?");

        preparedStatement.setString(1, "%" + substring + "%");
        preparedStatement.setString(2, "%" + substring + "%");
        preparedStatement.setString(3, "%" + substring + "%");

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            result.add(new Post.Builder()
                    .setId(resultSet.getInt(ID_COLUMN_NAME))
                    .setTitle(resultSet.getString(TITLE_COLUMN_NAME))
                    .setDescription(resultSet.getString(DESCRIPTION_COLUMN_NAME))
                    .setText(resultSet.getString(TEXT_COLUMN_NAME))
                    .setPosted(resultSet.getBoolean(IS_POSTED_COLUMN_NAME))
                    .setCreator(UserJdbcDAOImpl.getDao().getById(resultSet.getInt(CREATOR_ID_COLUMN_NAME)))
                    .setCreatedAt(DateUtil.fromMySQLDateTimeString(resultSet.getString(CREATED_AT_COLUMN_NAME)))
                    .setUpdatedAt(DateUtil.fromMySQLDateTimeString(resultSet.getString(UPDATED_AT_COLUMN_NAME)))
                    .build());
        }
        return result;
    }

    @Override
    public List<Post> getByTitle(String title) throws ClassNotFoundException, SQLException {
        ArrayList<Post> result = new ArrayList<>();
        PreparedStatement preparedStatement = getConnection()
                .prepareStatement("SELECT * FROM posts WHERE title LIKE ?");

        preparedStatement.setString(1, title);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            result.add(new Post.Builder()
                    .setId(resultSet.getInt(ID_COLUMN_NAME))
                    .setTitle(resultSet.getString(TITLE_COLUMN_NAME))
                    .setDescription(resultSet.getString(DESCRIPTION_COLUMN_NAME))
                    .setText(resultSet.getString(TEXT_COLUMN_NAME))
                    .setPosted(resultSet.getBoolean(IS_POSTED_COLUMN_NAME))
                    .setCreator(UserJdbcDAOImpl.getDao().getById(resultSet.getInt(CREATOR_ID_COLUMN_NAME)))
                    .setCreatedAt(DateUtil.fromMySQLDateTimeString(resultSet.getString(CREATED_AT_COLUMN_NAME)))
                    .setUpdatedAt(DateUtil.fromMySQLDateTimeString(resultSet.getString(UPDATED_AT_COLUMN_NAME)))
                    .build());
        }
        return result;
    }
}
