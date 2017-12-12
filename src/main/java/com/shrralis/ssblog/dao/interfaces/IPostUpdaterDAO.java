package com.shrralis.ssblog.dao.interfaces;

import com.shrralis.ssblog.entity.Post;
import com.shrralis.ssblog.entity.PostUpdater;
import com.shrralis.ssblog.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface IPostUpdaterDAO {
    String POST_ID_COLUMN_NAME = "post_id";
    String USER_ID_COLUMN_NAME = "user_id";

    PostUpdater add(PostUpdater postUpdater) throws SQLException;

    void delete(PostUpdater postUpdater) throws SQLException;

    PostUpdater get(Integer postId, Integer userId) throws ClassNotFoundException, SQLException;

    List<PostUpdater> getAllPostsUpdaters() throws ClassNotFoundException, SQLException;

    List<PostUpdater> getByPost(Post post) throws ClassNotFoundException, SQLException;

    List<PostUpdater> getByUser(User user) throws ClassNotFoundException, SQLException;
}
