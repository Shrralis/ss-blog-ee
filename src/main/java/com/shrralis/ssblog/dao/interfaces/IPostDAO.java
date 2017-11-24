package com.shrralis.ssblog.dao.interfaces;

import com.shrralis.ssblog.entity.Post;
import com.shrralis.ssblog.entity.User;

import java.util.List;

public interface IPostDAO {
    Post add(Post post);

    boolean delete(Post post);

    Post edit(Post post);

    List<Post> getAllPosts();

    List<Post> getByCreator(User creator);

    Post getById(Integer id);

    List<Post> getByIsPosted(boolean isPosted);

    List<Post> getByWord(String word);
}
