package com.shrralis.ssblog.dao;

import com.shrralis.ssblog.dao.base.JdbcBasedDAO;
import com.shrralis.ssblog.dao.interfaces.IPostDAO;
import com.shrralis.ssblog.entity.Image;
import com.shrralis.ssblog.entity.Post;
import com.shrralis.ssblog.entity.User;

import java.sql.*;
import java.time.LocalDateTime;
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
    private static final String IMAGE_ID_COLUMN_NAME = "image_id";

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
                .prepareStatement("INSERT INTO posts (title, description, text, is_posted, creator_id, created_at," +
                        "image_id) VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setString(1, post.getTitle());
        preparedStatement.setString(2, post.getDescription());
        preparedStatement.setString(3, post.getText());
        preparedStatement.setBoolean(4, post.isPosted());
        preparedStatement.setInt(5, post.getCreator().getId());
        preparedStatement.setObject(6, post.getCreatedAt());

        if (post.getImage() != null) {
            preparedStatement.setInt(7, post.getImage().getId());
        } else {
            preparedStatement.setNull(7, Types.INTEGER);
        }

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
    public Integer countPosts() throws SQLException {
        ResultSet resultSet = getConnection().createStatement().executeQuery("SELECT COUNT(*) AS count FROM posts");

        if (resultSet != null && resultSet.next()) {
            return resultSet.getInt(1);
        }
        return null;
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
                        "creator_id = ?, created_at = ?, updated_at = ?, image_id = ? WHERE id = ?");

        preparedStatement.setString(1, post.getTitle());
        preparedStatement.setString(2, post.getDescription());
        preparedStatement.setString(3, post.getText());
        preparedStatement.setBoolean(4, post.isPosted());
        preparedStatement.setInt(5, post.getCreator().getId());
        preparedStatement.setObject(6, post.getCreatedAt());
        preparedStatement.setObject(7, post.getUpdatedAt());

        if (post.getImage() != null) {
            preparedStatement.setInt(8, post.getImage().getId());
        } else {
            preparedStatement.setNull(8, Types.INTEGER);
        }
        preparedStatement.setInt(9, post.getId());
        preparedStatement.executeUpdate();
        return getById(post.getId());
    }

    @Override
    public List<Post> getAllPosts(Integer count, Integer offset, User requester) throws ClassNotFoundException, SQLException {
        ArrayList<Post> result = new ArrayList<>();
        PreparedStatement preparedStatement = getConnection()
                .prepareStatement("SELECT * FROM posts LEFT JOIN posts_updaters ON " +
                        "posts_updaters.post_id = posts.id WHERE is_posted = TRUE OR creator_id = ? OR user_id = ? " +
                        "ORDER BY created_at DESC LIMIT ? OFFSET ?");

        preparedStatement.setInt(1, requester.getId());
        preparedStatement.setInt(2, requester.getId());

        if (count == null) {
            preparedStatement.setNull(3, Types.INTEGER);
        } else {
            preparedStatement.setInt(3, count);
        }

        if (offset == null) {
            preparedStatement.setNull(4, Types.INTEGER);
        } else {
            preparedStatement.setInt(4, offset);
        }

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            result.add(Post.Builder.aPost()
                    .setId(resultSet.getInt(ID_COLUMN_NAME))
                    .setTitle(resultSet.getString(TITLE_COLUMN_NAME))
                    .setDescription(resultSet.getString(DESCRIPTION_COLUMN_NAME))
                    .setText(resultSet.getString(TEXT_COLUMN_NAME))
                    .setPosted(resultSet.getBoolean(IS_POSTED_COLUMN_NAME))
                    .setCreator(UserJdbcDAOImpl.getDao().getById(resultSet.getInt(CREATOR_ID_COLUMN_NAME)))
                    .setCreatedAt(resultSet.getObject(CREATED_AT_COLUMN_NAME, LocalDateTime.class))
                    .setUpdatedAt(resultSet.getObject(UPDATED_AT_COLUMN_NAME, LocalDateTime.class))
                    .setImage(ImageJdbcDAOImpl.getDao().get(resultSet.getInt(IMAGE_ID_COLUMN_NAME), false))
                    .build());
        }
        return result;
    }

    @Override
    public List<Post> getByCreator(User creator, Integer count, Integer offset, User requester) throws ClassNotFoundException, SQLException {
        ArrayList<Post> result = new ArrayList<>();

        if (creator == null) {
            return result;
        }

        PreparedStatement preparedStatement = getConnection()
                .prepareStatement("SELECT * FROM posts LEFT JOIN posts_updaters ON " +
                        "posts_updaters.post_id = posts.id WHERE creator_id = ? AND " +
                        "(is_posted = TRUE OR creator_id = ? OR user_id = ?) ORDER BY created_at DESC LIMIT ? OFFSET ?");

        preparedStatement.setInt(1, creator.getId());
        preparedStatement.setInt(2, requester.getId());
        preparedStatement.setInt(3, requester.getId());

        if (count == null) {
            preparedStatement.setNull(4, Types.INTEGER);
        } else {
            preparedStatement.setInt(4, count);
        }

        if (offset == null) {
            preparedStatement.setNull(5, Types.INTEGER);
        } else {
            preparedStatement.setInt(5, offset);
        }

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            result.add(Post.Builder.aPost()
                    .setId(resultSet.getInt(ID_COLUMN_NAME))
                    .setTitle(resultSet.getString(TITLE_COLUMN_NAME))
                    .setDescription(resultSet.getString(DESCRIPTION_COLUMN_NAME))
                    .setText(resultSet.getString(TEXT_COLUMN_NAME))
                    .setPosted(resultSet.getBoolean(IS_POSTED_COLUMN_NAME))
                    .setCreator(UserJdbcDAOImpl.getDao().getById(resultSet.getInt(CREATOR_ID_COLUMN_NAME)))
                    .setCreatedAt(resultSet.getObject(CREATED_AT_COLUMN_NAME, LocalDateTime.class))
                    .setUpdatedAt(resultSet.getObject(UPDATED_AT_COLUMN_NAME, LocalDateTime.class))
                    .setImage(ImageJdbcDAOImpl.getDao().get(resultSet.getInt(IMAGE_ID_COLUMN_NAME), false))
                    .build());
        }        return result;
    }

    @Override
    public Post getById(Integer id, User requester) throws ClassNotFoundException, SQLException {
        PreparedStatement preparedStatement = getConnection()
                .prepareStatement("SELECT * FROM posts LEFT JOIN posts_updaters ON " +
                        "posts_updaters.post_id = posts.id WHERE id = ? AND " +
                        "(is_posted = TRUE OR creator_id = ? OR user_id = ?)");

        preparedStatement.setInt(1, id);
        preparedStatement.setInt(2, requester.getId());
        preparedStatement.setInt(3, requester.getId());

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return Post.Builder.aPost()
                    .setId(resultSet.getInt(ID_COLUMN_NAME))
                    .setTitle(resultSet.getString(TITLE_COLUMN_NAME))
                    .setDescription(resultSet.getString(DESCRIPTION_COLUMN_NAME))
                    .setText(resultSet.getString(TEXT_COLUMN_NAME))
                    .setPosted(resultSet.getBoolean(IS_POSTED_COLUMN_NAME))
                    .setCreator(UserJdbcDAOImpl.getDao().getById(resultSet.getInt(CREATOR_ID_COLUMN_NAME)))
                    .setCreatedAt(resultSet.getObject(CREATED_AT_COLUMN_NAME, LocalDateTime.class))
                    .setUpdatedAt(resultSet.getObject(UPDATED_AT_COLUMN_NAME, LocalDateTime.class))
                    .setImage(ImageJdbcDAOImpl.getDao().get(resultSet.getInt(IMAGE_ID_COLUMN_NAME), false))
                    .build();
        }
        return null;
    }

    Post getById(Integer id) throws ClassNotFoundException, SQLException {
        PreparedStatement preparedStatement = getConnection()
                .prepareStatement("SELECT * FROM posts LEFT JOIN posts_updaters ON " +
                        "posts_updaters.post_id = posts.id WHERE id = ?");

        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return Post.Builder.aPost()
                    .setId(resultSet.getInt(ID_COLUMN_NAME))
                    .setTitle(resultSet.getString(TITLE_COLUMN_NAME))
                    .setDescription(resultSet.getString(DESCRIPTION_COLUMN_NAME))
                    .setText(resultSet.getString(TEXT_COLUMN_NAME))
                    .setPosted(resultSet.getBoolean(IS_POSTED_COLUMN_NAME))
                    .setCreator(UserJdbcDAOImpl.getDao().getById(resultSet.getInt(CREATOR_ID_COLUMN_NAME)))
                    .setCreatedAt(resultSet.getObject(CREATED_AT_COLUMN_NAME, LocalDateTime.class))
                    .setUpdatedAt(resultSet.getObject(UPDATED_AT_COLUMN_NAME, LocalDateTime.class))
                    .setImage(ImageJdbcDAOImpl.getDao().get(resultSet.getInt(IMAGE_ID_COLUMN_NAME), false))
                    .build();
        }
        return null;
    }

    @Override
    public List<Post> getByImage(Image image, User requester) throws ClassNotFoundException, SQLException {
        ArrayList<Post> result = new ArrayList<>();
        PreparedStatement preparedStatement = getConnection()
                .prepareStatement("SELECT * FROM posts LEFT JOIN posts_updaters ON " +
                        "posts_updaters.post_id = posts.id WHERE image_id = ? AND " +
                        "(is_posted = TRUE OR creator_id = ? OR user_id = ?)");

        if (image == null) {
            return result;
        }
        preparedStatement.setInt(1, image.getId());
        preparedStatement.setInt(2, requester.getId());
        preparedStatement.setInt(3, requester.getId());

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            result.add(Post.Builder.aPost()
                    .setId(resultSet.getInt(ID_COLUMN_NAME))
                    .setTitle(resultSet.getString(TITLE_COLUMN_NAME))
                    .setDescription(resultSet.getString(DESCRIPTION_COLUMN_NAME))
                    .setText(resultSet.getString(TEXT_COLUMN_NAME))
                    .setPosted(resultSet.getBoolean(IS_POSTED_COLUMN_NAME))
                    .setCreator(UserJdbcDAOImpl.getDao().getById(resultSet.getInt(CREATOR_ID_COLUMN_NAME)))
                    .setCreatedAt(resultSet.getObject(CREATED_AT_COLUMN_NAME, LocalDateTime.class))
                    .setUpdatedAt(resultSet.getObject(UPDATED_AT_COLUMN_NAME, LocalDateTime.class))
                    .setImage(ImageJdbcDAOImpl.getDao().get(resultSet.getInt(IMAGE_ID_COLUMN_NAME), false))
                    .build());
        }
        return result;
    }

    @Override
    public List<Post> getByIsPosted(boolean isPosted, User requester) throws ClassNotFoundException, SQLException {
        ArrayList<Post> result = new ArrayList<>();
        PreparedStatement preparedStatement = getConnection()
                .prepareStatement("SELECT * FROM posts LEFT JOIN posts_updaters ON " +
                        "posts_updaters.post_id = posts.id WHERE is_posted = ? AND " +
                        "(is_posted = TRUE OR creator_id = ? OR user_id = ?)");

        preparedStatement.setBoolean(1, isPosted);
        preparedStatement.setInt(2, requester.getId());
        preparedStatement.setInt(3, requester.getId());

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            result.add(Post.Builder.aPost()
                    .setId(resultSet.getInt(ID_COLUMN_NAME))
                    .setTitle(resultSet.getString(TITLE_COLUMN_NAME))
                    .setDescription(resultSet.getString(DESCRIPTION_COLUMN_NAME))
                    .setText(resultSet.getString(TEXT_COLUMN_NAME))
                    .setPosted(resultSet.getBoolean(IS_POSTED_COLUMN_NAME))
                    .setCreator(UserJdbcDAOImpl.getDao().getById(resultSet.getInt(CREATOR_ID_COLUMN_NAME)))
                    .setCreatedAt(resultSet.getObject(CREATED_AT_COLUMN_NAME, LocalDateTime.class))
                    .setUpdatedAt(resultSet.getObject(UPDATED_AT_COLUMN_NAME, LocalDateTime.class))
                    .setImage(ImageJdbcDAOImpl.getDao().get(resultSet.getInt(IMAGE_ID_COLUMN_NAME), false))
                    .build());
        }
        return result;
    }

    @Override
    public List<Post> getBySubstring(String substring, Integer count, Integer offset, User requester) throws ClassNotFoundException, SQLException {
        ArrayList<Post> result = new ArrayList<>();
        PreparedStatement preparedStatement = getConnection()
                .prepareStatement("SELECT * FROM posts LEFT JOIN posts_updaters ON " +
                        "posts_updaters.post_id = posts.id WHERE " +
                        "(LOWER(title) LIKE LOWER(?) OR LOWER(description) LIKE LOWER(?) OR LOWER(text) LIKE LOWER(?)) " +
                        "AND (is_posted = TRUE OR creator_id = ? OR user_id = ?) ORDER BY created_at DESC " +
                        "LIMIT ? OFFSET ?");

        preparedStatement.setString(1, "%" + substring + "%");
        preparedStatement.setString(2, "%" + substring + "%");
        preparedStatement.setString(3, "%" + substring + "%");
        preparedStatement.setInt(4, requester.getId());
        preparedStatement.setInt(5, requester.getId());

        if (count == null) {
            preparedStatement.setNull(6, Types.INTEGER);
        } else {
            preparedStatement.setInt(6, count);
        }

        if (offset == null) {
            preparedStatement.setNull(7, Types.INTEGER);
        } else {
            preparedStatement.setInt(7, offset);
        }

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            result.add(Post.Builder.aPost()
                    .setId(resultSet.getInt(ID_COLUMN_NAME))
                    .setTitle(resultSet.getString(TITLE_COLUMN_NAME))
                    .setDescription(resultSet.getString(DESCRIPTION_COLUMN_NAME))
                    .setText(resultSet.getString(TEXT_COLUMN_NAME))
                    .setPosted(resultSet.getBoolean(IS_POSTED_COLUMN_NAME))
                    .setCreator(UserJdbcDAOImpl.getDao().getById(resultSet.getInt(CREATOR_ID_COLUMN_NAME)))
                    .setCreatedAt(resultSet.getObject(CREATED_AT_COLUMN_NAME, LocalDateTime.class))
                    .setUpdatedAt(resultSet.getObject(UPDATED_AT_COLUMN_NAME, LocalDateTime.class))
                    .setImage(ImageJdbcDAOImpl.getDao().get(resultSet.getInt(IMAGE_ID_COLUMN_NAME), false))
                    .build());
        }
        return result;
    }

    @Override
    public List<Post> getByTitle(String title, User requester) throws ClassNotFoundException, SQLException {
        ArrayList<Post> result = new ArrayList<>();
        PreparedStatement preparedStatement = getConnection()
                .prepareStatement("SELECT * FROM posts LEFT JOIN posts_updaters ON " +
                        "posts_updaters.post_id = posts.id WHERE title LIKE ? AND " +
                        "(is_posted = TRUE OR creator_id = ? OR user_id = ?)");

        preparedStatement.setString(1, title);
        preparedStatement.setInt(2, requester.getId());
        preparedStatement.setInt(3, requester.getId());

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            result.add(Post.Builder.aPost()
                    .setId(resultSet.getInt(ID_COLUMN_NAME))
                    .setTitle(resultSet.getString(TITLE_COLUMN_NAME))
                    .setDescription(resultSet.getString(DESCRIPTION_COLUMN_NAME))
                    .setText(resultSet.getString(TEXT_COLUMN_NAME))
                    .setPosted(resultSet.getBoolean(IS_POSTED_COLUMN_NAME))
                    .setCreator(UserJdbcDAOImpl.getDao().getById(resultSet.getInt(CREATOR_ID_COLUMN_NAME)))
                    .setCreatedAt(resultSet.getObject(CREATED_AT_COLUMN_NAME, LocalDateTime.class))
                    .setUpdatedAt(resultSet.getObject(UPDATED_AT_COLUMN_NAME, LocalDateTime.class))
                    .setImage(ImageJdbcDAOImpl.getDao().get(resultSet.getInt(IMAGE_ID_COLUMN_NAME), false))
                    .build());
        }
        return result;
    }
}
