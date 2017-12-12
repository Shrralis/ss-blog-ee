package com.shrralis.ssblog.dao.mybatis.mapper;

import com.shrralis.ssblog.entity.Image;
import com.shrralis.ssblog.entity.Post;
import com.shrralis.ssblog.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

import static com.shrralis.ssblog.dao.interfaces.IPostDAO.*;
import static com.shrralis.ssblog.dao.interfaces.IPostUpdaterDAO.POST_ID_COLUMN_NAME;
import static com.shrralis.ssblog.dao.interfaces.IPostUpdaterDAO.USER_ID_COLUMN_NAME;

public interface IPostMapper {
    @Insert("INSERT INTO posts (" + ID_COLUMN_NAME + ", " + TITLE_COLUMN_NAME + ", " + DESCRIPTION_COLUMN_NAME + ", " +
            TEXT_COLUMN_NAME + ", " + CREATOR_ID_COLUMN_NAME + ", " + CREATED_AT_COLUMN_NAME + ", " +
            IMAGE_ID_COLUMN_NAME + ") " + "VALUES (#{id}, #{title}, #{description}, #{text}, #{creator.id}, " +
            "#{createdAt}, #{image.id})")
    @SelectKey(statement = "SELECT nextVal('posts_id_seq')", keyProperty = "id", before = true, resultType = int.class)
    int add(Post post);

    @Select("SELECT COUNT(*) FROM (SELECT p.* FROM posts p LEFT JOIN posts_updaters ON " + POST_ID_COLUMN_NAME +
            " = p." + ID_COLUMN_NAME + " WHERE " + IS_POSTED_COLUMN_NAME + " = TRUE OR " + CREATOR_ID_COLUMN_NAME +
            " = #{id} OR " + USER_ID_COLUMN_NAME + " = #{id} GROUP BY p." + ID_COLUMN_NAME + ") AS count")
    Integer countPosts(User requester);

    @Select("SELECT COUNT(*) FROM (SELECT p.* FROM posts p LEFT JOIN posts_updaters ON " + POST_ID_COLUMN_NAME +
            " = p." + ID_COLUMN_NAME + " WHERE (" + IS_POSTED_COLUMN_NAME + " = TRUE OR " + CREATOR_ID_COLUMN_NAME +
            " = #{requester.id} OR " + USER_ID_COLUMN_NAME + " = #{requester.id}) AND (LOWER(" + TITLE_COLUMN_NAME +
            ") LIKE LOWER('%' || #{substring} || '%') OR LOWER(" + DESCRIPTION_COLUMN_NAME +
            ") LIKE LOWER ('%' || #{substring} || '%') OR LOWER(" + TEXT_COLUMN_NAME +
            ") LIKE LOWER('%' || #{substring} || '%')) GROUP BY p." + ID_COLUMN_NAME + ") AS count")
    Integer countPostsSearch(@Param("substring") String substring,
                             @Param("requester") User requester);

    @Delete("DELETE FROM posts WHERE " + ID_COLUMN_NAME + " = #{id}")
    void delete(Post post);

    @Update("UPDATE posts SET " + TITLE_COLUMN_NAME + " = #{title}, " + DESCRIPTION_COLUMN_NAME +
            " = #{description}, " + TEXT_COLUMN_NAME + " = #{text}, " + IS_POSTED_COLUMN_NAME + " = #{isPosted}, " +
            CREATOR_ID_COLUMN_NAME + " = #{creator.id}, " + CREATED_AT_COLUMN_NAME + " = #{createdAt}, " +
            UPDATED_AT_COLUMN_NAME + " = #{updatedAt}, " + IMAGE_ID_COLUMN_NAME + " = #{image.id}")
    void edit(Post post);

    @Select("SELECT posts.* FROM posts LEFT JOIN posts_updaters ON " + POST_ID_COLUMN_NAME + " = " + ID_COLUMN_NAME +
            " WHERE " + IS_POSTED_COLUMN_NAME + " = TRUE OR " + CREATOR_ID_COLUMN_NAME + " = #{requester.id} OR " +
            USER_ID_COLUMN_NAME + " = #{requester.id} GROUP BY posts.id ORDER BY " + CREATED_AT_COLUMN_NAME +
            " DESC LIMIT #{count} OFFSET #{offset}")
    @Results({
            @Result(property = "id", column = ID_COLUMN_NAME),
            @Result(property = "title", column = TITLE_COLUMN_NAME),
            @Result(property = "description", column = DESCRIPTION_COLUMN_NAME),
            @Result(property = "text", column = TEXT_COLUMN_NAME),
            @Result(property = "isPosted", column = IS_POSTED_COLUMN_NAME),
            @Result(property = "createdAt", column = CREATED_AT_COLUMN_NAME),
            @Result(property = "updatedAt", column = UPDATED_AT_COLUMN_NAME),
            @Result(property = "creator", column = CREATOR_ID_COLUMN_NAME,
                    one = @One(select = "com.shrralis.ssblog.dao.mybatis.mapper.IUserMapper.getByIdForMapper")),
            @Result(property = "image", column = IMAGE_ID_COLUMN_NAME,
                    one = @One(select = "com.shrralis.ssblog.dao.mybatis.mapper.IImageMapper.getForMapper"))
    })
    List<Post> getAllPosts(@Param("count") Integer count,
                           @Param("offset") Integer offset,
                           @Param("requester") User requester);

    @Select("SELECT posts.* FROM posts LEFT JOIN posts_updaters ON " + POST_ID_COLUMN_NAME + " = " + ID_COLUMN_NAME +
            " WHERE " + CREATOR_ID_COLUMN_NAME + " = #{creator.id} AND (" + IS_POSTED_COLUMN_NAME + " = TRUE OR " +
            CREATOR_ID_COLUMN_NAME + " = #{requester.id} OR " + USER_ID_COLUMN_NAME + " = #{requester.id}) " +
            "GROUP BY posts.id ORDER BY " + CREATED_AT_COLUMN_NAME + " DESC LIMIT #{count} OFFSET #{offset}")
    @Results({
            @Result(property = "id", column = ID_COLUMN_NAME),
            @Result(property = "title", column = TITLE_COLUMN_NAME),
            @Result(property = "description", column = DESCRIPTION_COLUMN_NAME),
            @Result(property = "text", column = TEXT_COLUMN_NAME),
            @Result(property = "isPosted", column = IS_POSTED_COLUMN_NAME),
            @Result(property = "createdAt", column = CREATED_AT_COLUMN_NAME),
            @Result(property = "updatedAt", column = UPDATED_AT_COLUMN_NAME),
            @Result(property = "creator", column = CREATOR_ID_COLUMN_NAME,
                    one = @One(select = "com.shrralis.ssblog.dao.mybatis.mapper.IUserMapper.getByIdForMapper")),
            @Result(property = "image", column = IMAGE_ID_COLUMN_NAME,
                    one = @One(select = "com.shrralis.ssblog.dao.mybatis.mapper.IImageMapper.getForMapper"))
    })
    List<Post> getByCreator(@Param("creator") User creator,
                            @Param("count") Integer count,
                            @Param("offset") Integer offset,
                            @Param("requester") User requester);

    @Select("SELECT * FROM posts LEFT JOIN posts_updaters ON " + POST_ID_COLUMN_NAME + " = " + ID_COLUMN_NAME +
            " WHERE " + ID_COLUMN_NAME + " = #{id} AND (" + IS_POSTED_COLUMN_NAME + " = TRUE OR " +
            CREATOR_ID_COLUMN_NAME + " = #{requester.id} OR " + USER_ID_COLUMN_NAME + " = #{requester.id}) LIMIT 1")
    @Results({
            @Result(property = "id", column = ID_COLUMN_NAME),
            @Result(property = "title", column = TITLE_COLUMN_NAME),
            @Result(property = "description", column = DESCRIPTION_COLUMN_NAME),
            @Result(property = "text", column = TEXT_COLUMN_NAME),
            @Result(property = "isPosted", column = IS_POSTED_COLUMN_NAME),
            @Result(property = "createdAt", column = CREATED_AT_COLUMN_NAME),
            @Result(property = "updatedAt", column = UPDATED_AT_COLUMN_NAME),
            @Result(property = "creator", column = CREATOR_ID_COLUMN_NAME,
                    one = @One(select = "com.shrralis.ssblog.dao.mybatis.mapper.IUserMapper.getByIdForMapper")),
            @Result(property = "image", column = IMAGE_ID_COLUMN_NAME,
                    one = @One(select = "com.shrralis.ssblog.dao.mybatis.mapper.IImageMapper.getForMapper"))
    })
    Post getById(@Param("id") Integer id, @Param("requester") User requester);

    @Select("SELECT * FROM posts LEFT JOIN posts_updaters ON " + POST_ID_COLUMN_NAME + " = " + ID_COLUMN_NAME +
            " WHERE " + ID_COLUMN_NAME + " = #{id} LIMIT 1")
    @Results({
            @Result(property = "id", column = ID_COLUMN_NAME),
            @Result(property = "title", column = TITLE_COLUMN_NAME),
            @Result(property = "description", column = DESCRIPTION_COLUMN_NAME),
            @Result(property = "text", column = TEXT_COLUMN_NAME),
            @Result(property = "isPosted", column = IS_POSTED_COLUMN_NAME),
            @Result(property = "createdAt", column = CREATED_AT_COLUMN_NAME),
            @Result(property = "updatedAt", column = UPDATED_AT_COLUMN_NAME),
            @Result(property = "creator", column = CREATOR_ID_COLUMN_NAME,
                    one = @One(select = "com.shrralis.ssblog.dao.mybatis.mapper.IUserMapper.getByIdForMapper")),
            @Result(property = "image", column = IMAGE_ID_COLUMN_NAME,
                    one = @One(select = "com.shrralis.ssblog.dao.mybatis.mapper.IImageMapper.getForMapper"))
    })
    Post getByIdForMapper(@Param("id") Integer id);

    @Select("SELECT posts.* FROM posts LEFT JOIN posts_updaters ON " + POST_ID_COLUMN_NAME + " = " + ID_COLUMN_NAME +
            " WHERE " + IMAGE_ID_COLUMN_NAME + " = #{image.id} AND (" + IS_POSTED_COLUMN_NAME + " = TRUE OR " +
            CREATOR_ID_COLUMN_NAME + " = #{requester.id} OR " + USER_ID_COLUMN_NAME + " = #{requester.id})" +
            "GROUP BY posts.id ORDER BY " + CREATED_AT_COLUMN_NAME + " DESC LIMIT #{count} OFFSET #{offset}")
    @Results({
            @Result(property = "id", column = ID_COLUMN_NAME),
            @Result(property = "title", column = TITLE_COLUMN_NAME),
            @Result(property = "description", column = DESCRIPTION_COLUMN_NAME),
            @Result(property = "text", column = TEXT_COLUMN_NAME),
            @Result(property = "isPosted", column = IS_POSTED_COLUMN_NAME),
            @Result(property = "createdAt", column = CREATED_AT_COLUMN_NAME),
            @Result(property = "updatedAt", column = UPDATED_AT_COLUMN_NAME),
            @Result(property = "creator", column = CREATOR_ID_COLUMN_NAME,
                    one = @One(select = "com.shrralis.ssblog.dao.mybatis.mapper.IUserMapper.getByIdForMapper")),
            @Result(property = "image", column = IMAGE_ID_COLUMN_NAME,
                    one = @One(select = "com.shrralis.ssblog.dao.mybatis.mapper.IImageMapper.getForMapper"))
    })
    List<Post> getByImage(@Param("image") Image image,
                          @Param("count") Integer count,
                          @Param("offset") Integer offset,
                          @Param("requester") User requester);

    @Select("SELECT posts.* FROM posts LEFT JOIN posts_updaters ON " + POST_ID_COLUMN_NAME + " = " + ID_COLUMN_NAME +
            " WHERE " + IS_POSTED_COLUMN_NAME + " = #{isPosted} AND (" + IS_POSTED_COLUMN_NAME + " = TRUE OR " +
            CREATOR_ID_COLUMN_NAME + " = #{requester.id} OR " + USER_ID_COLUMN_NAME + " = #{requester.id})" +
            "GROUP BY posts.id ORDER BY " + CREATED_AT_COLUMN_NAME + " DESC LIMIT #{count} OFFSET #{offset}")
    @Results({
            @Result(property = "id", column = ID_COLUMN_NAME),
            @Result(property = "title", column = TITLE_COLUMN_NAME),
            @Result(property = "description", column = DESCRIPTION_COLUMN_NAME),
            @Result(property = "text", column = TEXT_COLUMN_NAME),
            @Result(property = "isPosted", column = IS_POSTED_COLUMN_NAME),
            @Result(property = "createdAt", column = CREATED_AT_COLUMN_NAME),
            @Result(property = "updatedAt", column = UPDATED_AT_COLUMN_NAME),
            @Result(property = "creator", column = CREATOR_ID_COLUMN_NAME,
                    one = @One(select = "com.shrralis.ssblog.dao.mybatis.mapper.IUserMapper.getByIdForMapper")),
            @Result(property = "image", column = IMAGE_ID_COLUMN_NAME,
                    one = @One(select = "com.shrralis.ssblog.dao.mybatis.mapper.IImageMapper.getForMapper"))
    })
    List<Post> getByIsPosted(@Param("isPosted") boolean isPosted,
                             @Param("count") Integer count,
                             @Param("offset") Integer offset,
                             @Param("requester") User requester);

    @Select("SELECT posts.* FROM posts LEFT JOIN posts_updaters ON " + POST_ID_COLUMN_NAME + " = " + ID_COLUMN_NAME +
            " WHERE " + TITLE_COLUMN_NAME + " LIKE '#{title}%' AND (" + IS_POSTED_COLUMN_NAME + " = TRUE OR " +
            CREATOR_ID_COLUMN_NAME + " = #{requester.id} OR " + USER_ID_COLUMN_NAME + " = #{requester.id})" +
            "GROUP BY posts.id ORDER BY " + CREATED_AT_COLUMN_NAME + " DESC LIMIT #{count} OFFSET #{offset}")
    @Results({
            @Result(property = "id", column = ID_COLUMN_NAME),
            @Result(property = "title", column = TITLE_COLUMN_NAME),
            @Result(property = "description", column = DESCRIPTION_COLUMN_NAME),
            @Result(property = "text", column = TEXT_COLUMN_NAME),
            @Result(property = "isPosted", column = IS_POSTED_COLUMN_NAME),
            @Result(property = "createdAt", column = CREATED_AT_COLUMN_NAME),
            @Result(property = "updatedAt", column = UPDATED_AT_COLUMN_NAME),
            @Result(property = "creator", column = CREATOR_ID_COLUMN_NAME,
                    one = @One(select = "com.shrralis.ssblog.dao.mybatis.mapper.IUserMapper.getByIdForMapper")),
            @Result(property = "image", column = IMAGE_ID_COLUMN_NAME,
                    one = @One(select = "com.shrralis.ssblog.dao.mybatis.mapper.IImageMapper.getForMapper"))
    })
    List<Post> getByTitle(@Param("title") String title,
                          @Param("count") Integer count,
                          @Param("offset") Integer offset,
                          @Param("requester") User requester);

    @Select("SELECT posts.* FROM posts LEFT JOIN posts_updaters ON " + POST_ID_COLUMN_NAME + " = " + ID_COLUMN_NAME +
            " WHERE (LOWER(" + TITLE_COLUMN_NAME + ") LIKE LOWER('%' || #{substring} || '%') OR LOWER(" +
            DESCRIPTION_COLUMN_NAME + ") LIKE LOWER('%' || #{substring} || '%') OR LOWER(" + TEXT_COLUMN_NAME +
            ") LIKE LOWER('%' || #{substring} || '%')) AND (" + IS_POSTED_COLUMN_NAME + " = TRUE OR " +
            CREATOR_ID_COLUMN_NAME + " = #{requester.id} OR " + USER_ID_COLUMN_NAME + " = #{requester.id})" +
            "GROUP BY posts.id ORDER BY " + CREATED_AT_COLUMN_NAME + " DESC LIMIT #{count} OFFSET #{offset}")
    @Results({
            @Result(property = "id", column = ID_COLUMN_NAME),
            @Result(property = "title", column = TITLE_COLUMN_NAME),
            @Result(property = "description", column = DESCRIPTION_COLUMN_NAME),
            @Result(property = "text", column = TEXT_COLUMN_NAME),
            @Result(property = "isPosted", column = IS_POSTED_COLUMN_NAME),
            @Result(property = "createdAt", column = CREATED_AT_COLUMN_NAME),
            @Result(property = "updatedAt", column = UPDATED_AT_COLUMN_NAME),
            @Result(property = "creator", column = CREATOR_ID_COLUMN_NAME,
                    one = @One(select = "com.shrralis.ssblog.dao.mybatis.mapper.IUserMapper.getByIdForMapper")),
            @Result(property = "image", column = IMAGE_ID_COLUMN_NAME,
                    one = @One(select = "com.shrralis.ssblog.dao.mybatis.mapper.IImageMapper.getForMapper"))
    })
    List<Post> getBySubstring(@Param("substring") String substring,
                              @Param("count") Integer count,
                              @Param("offset") Integer offset,
                              @Param("requester") User requester);
}
