package com.shrralis.ssblog.dao.interfaces;

import com.shrralis.ssblog.entity.Post;
import com.shrralis.ssblog.entity.PostUpdater;
import com.shrralis.ssblog.entity.User;

import java.util.List;

public interface IPostUpdaterDAO {
    PostUpdater add(PostUpdater postUpdater);

    boolean delete(PostUpdater postUpdater);

    PostUpdater get(Integer userId, Integer postId);

    List<PostUpdater> getAllPostsUpdaters();

    List<PostUpdater> getByPost(Post post);

    List<PostUpdater> getByUser(User user);
}
