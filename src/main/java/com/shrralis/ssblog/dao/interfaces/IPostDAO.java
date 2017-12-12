package com.shrralis.ssblog.dao.interfaces;

import com.shrralis.ssblog.entity.Image;
import com.shrralis.ssblog.entity.Post;
import com.shrralis.ssblog.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface IPostDAO {
    String ID_COLUMN_NAME = "id";
    String TITLE_COLUMN_NAME = "title";
    String DESCRIPTION_COLUMN_NAME = "description";
    String TEXT_COLUMN_NAME = "text";
    String IS_POSTED_COLUMN_NAME = "is_posted";
    String CREATOR_ID_COLUMN_NAME = "creator_id";
    String CREATED_AT_COLUMN_NAME = "created_at";
    String UPDATED_AT_COLUMN_NAME = "updated_at";
    String IMAGE_ID_COLUMN_NAME = "image_id";

    Post add(Post post) throws ClassNotFoundException, SQLException;

    Integer countPosts(User requester) throws SQLException;

    Integer countPosts(String substring, User requester) throws SQLException;

    void delete(Post post) throws SQLException;

    Post edit(Post post) throws ClassNotFoundException, SQLException;

    List<Post> getAllPosts(Integer count, Integer offset, User requester) throws ClassNotFoundException, SQLException;

    List<Post> getByCreator(User creator, Integer count, Integer offset, User requester) throws ClassNotFoundException, SQLException;

    Post getById(Integer id, User requester) throws ClassNotFoundException, SQLException;

    List<Post> getByImage(Image image, Integer count, Integer offset, User requester) throws ClassNotFoundException, SQLException;

    List<Post> getByIsPosted(boolean isPosted, Integer count, Integer offset, User requester) throws ClassNotFoundException, SQLException;

    List<Post> getByTitle(String title, Integer count, Integer offset, User requester) throws ClassNotFoundException, SQLException;

    List<Post> getBySubstring(String substring, Integer count, Integer offset, User requester) throws ClassNotFoundException, SQLException;
}
