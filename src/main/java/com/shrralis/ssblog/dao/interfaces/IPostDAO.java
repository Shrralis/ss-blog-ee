package com.shrralis.ssblog.dao.interfaces;

import com.shrralis.ssblog.entity.Image;
import com.shrralis.ssblog.entity.Post;
import com.shrralis.ssblog.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface IPostDAO {
    Post add(Post post) throws ClassNotFoundException, SQLException;

    Integer countPosts() throws SQLException;

    void delete(Post post) throws SQLException;

    Post edit(Post post) throws ClassNotFoundException, SQLException;

    List<Post> getAllPosts(Integer count, Integer offset, User requester) throws ClassNotFoundException, SQLException;

    List<Post> getByCreator(User creator, Integer count, Integer offset, User requester) throws ClassNotFoundException, SQLException;

    Post getById(Integer id, User requester) throws ClassNotFoundException, SQLException;

    List<Post> getByImage(Image image, User requester) throws ClassNotFoundException, SQLException;

    List<Post> getByIsPosted(boolean isPosted, User requester) throws ClassNotFoundException, SQLException;

    List<Post> getByTitle(String title, User requester) throws ClassNotFoundException, SQLException;

    List<Post> getBySubstring(String substring, Integer count, Integer offset, User requester) throws ClassNotFoundException, SQLException;
}
