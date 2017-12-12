package com.shrralis.ssblog.dao.mybatis.mapper;

import com.shrralis.ssblog.entity.Post;
import com.shrralis.ssblog.entity.PostUpdater;
import com.shrralis.ssblog.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

import static com.shrralis.ssblog.dao.interfaces.IPostUpdaterDAO.POST_ID_COLUMN_NAME;
import static com.shrralis.ssblog.dao.interfaces.IPostUpdaterDAO.USER_ID_COLUMN_NAME;

public interface IPostUpdaterMapper {
    @Insert("INSERT INTO posts_updaters VALUES (" + POST_ID_COLUMN_NAME + ", " + USER_ID_COLUMN_NAME +
            ") VALUES (#{post.id}, #{user.id})")
    void add(PostUpdater postUpdater);

    @Delete("DELETE FROM posts_updaters WHERE " + POST_ID_COLUMN_NAME + " = #{post.id} AND " + USER_ID_COLUMN_NAME +
            " = #{user.id}")
    void delete(PostUpdater postUpdater);

    @Select("SELECT * FROM posts_updaters WHERE " + POST_ID_COLUMN_NAME + " = #{postId} AND " + USER_ID_COLUMN_NAME +
            " = #{userId}")
    @Results({
            @Result(property = "post", column = POST_ID_COLUMN_NAME,
                    one = @One(select = "com.shrralis.ssblog.dao.mybatis.mapper.IPostMapper.getByIdForMapper")),
            @Result(property = "user", column = USER_ID_COLUMN_NAME,
                    one = @One(select = "com.shrralis.ssblog.dao.mybatis.mapper.IUserMapper.getByIdForMapper")),
    })
    PostUpdater get(Integer postId, Integer userId);

    @Select("SELECT * FROM posts_updaters")
    @Results({
            @Result(property = "post", column = POST_ID_COLUMN_NAME,
                    one = @One(select = "com.shrralis.ssblog.dao.mybatis.mapper.IPostMapper.getByIdForMapper")),
            @Result(property = "user", column = USER_ID_COLUMN_NAME,
                    one = @One(select = "com.shrralis.ssblog.dao.mybatis.mapper.IUserMapper.getByIdForMapper")),
    })
    List<PostUpdater> getAllPostsUpdaters();

    @Select("SELECT * FROM posts_updaters WHERE " + POST_ID_COLUMN_NAME + " = #{id}")
    @Results({
            @Result(property = "post", column = POST_ID_COLUMN_NAME,
                    one = @One(select = "com.shrralis.ssblog.dao.mybatis.mapper.IPostMapper.getByIdForMapper")),
            @Result(property = "user", column = USER_ID_COLUMN_NAME,
                    one = @One(select = "com.shrralis.ssblog.dao.mybatis.mapper.IUserMapper.getByIdForMapper")),
    })
    List<PostUpdater> getByPost(Post post);

    @Select("SELECT * FROM posts_updaters WHERE " + USER_ID_COLUMN_NAME + " = #{id}")
    @Results({
            @Result(property = "post", column = POST_ID_COLUMN_NAME,
                    one = @One(select = "com.shrralis.ssblog.dao.mybatis.mapper.IPostMapper.getByIdForMapper")),
            @Result(property = "user", column = USER_ID_COLUMN_NAME,
                    one = @One(select = "com.shrralis.ssblog.dao.mybatis.mapper.IUserMapper.getByIdForMapper")),
    })
    List<PostUpdater> getByUser(User user);
}
