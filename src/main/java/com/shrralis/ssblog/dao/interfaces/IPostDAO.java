package com.shrralis.ssblog.dao.interfaces;

import com.shrralis.ssblog.entity.Image;
import com.shrralis.ssblog.entity.Post;
import com.shrralis.ssblog.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface IPostDAO {
    Post add(Post post) throws ClassNotFoundException, SQLException;

    void delete(Post post) throws SQLException;

    Post edit(Post post) throws ClassNotFoundException, SQLException;

    List<Post> getAllPosts() throws ClassNotFoundException, SQLException;

    List<Post> getByCreator(User creator) throws ClassNotFoundException, SQLException;

    Post getById(Integer id) throws ClassNotFoundException, SQLException;

    List<Post> getByImage(Image image) throws ClassNotFoundException, SQLException;

    List<Post> getByIsPosted(boolean isPosted) throws ClassNotFoundException, SQLException;

    List<Post> getByTitle(String title) throws ClassNotFoundException, SQLException;

    List<Post> getBySubstring(String substring) throws ClassNotFoundException, SQLException;
}
