package com.shrralis.ssblog.dao.mybatis;

import com.shrralis.ssblog.dao.base.MyBatisBasedDAO;
import com.shrralis.ssblog.dao.interfaces.IPostDAO;
import com.shrralis.ssblog.dao.mybatis.mapper.IPostMapper;
import com.shrralis.ssblog.entity.Image;
import com.shrralis.ssblog.entity.Post;
import com.shrralis.ssblog.entity.User;
import org.apache.ibatis.session.SqlSession;

import java.sql.SQLException;
import java.util.List;

public class PostMyBatisDAOImpl extends MyBatisBasedDAO implements IPostDAO {
    private static PostMyBatisDAOImpl dao;

    private PostMyBatisDAOImpl() {
        super();
    }

    public static PostMyBatisDAOImpl getDao() {
        if (dao == null) {
            dao = new PostMyBatisDAOImpl();
        }
        return dao;
    }

    @Override
    public Post add(Post post) throws ClassNotFoundException, SQLException {
        try (SqlSession session = getSqlSessionFactory().openSession(true)) {
            post.setId(session.getMapper(IPostMapper.class).add(post));
        }
        return post;
    }

    @Override
    public Integer countPosts(User requester) throws SQLException {
        try (SqlSession session = getSqlSessionFactory().openSession(true)) {
            return session.getMapper(IPostMapper.class).countPosts(requester);
        }
    }

    @Override
    public Integer countPosts(String substring, User requester) throws SQLException {
        try (SqlSession session = getSqlSessionFactory().openSession(true)) {
            return session.getMapper(IPostMapper.class).countPostsSearch(substring, requester);
        }
    }

    @Override
    public void delete(Post post) throws SQLException {
        try (SqlSession session = getSqlSessionFactory().openSession(true)) {
            session.getMapper(IPostMapper.class).delete(post);
        }
    }

    @Override
    public Post edit(Post post) throws ClassNotFoundException, SQLException {
        try (SqlSession session = getSqlSessionFactory().openSession(true)) {
            session.getMapper(IPostMapper.class).edit(post);
        }
        return post;
    }

    @Override
    public List<Post> getAllPosts(Integer count, Integer offset, User requester) throws ClassNotFoundException, SQLException {
        try (SqlSession session = getSqlSessionFactory().openSession(true)) {
            return session.getMapper(IPostMapper.class).getAllPosts(count, offset, requester);
        }
    }

    @Override
    public List<Post> getByCreator(User creator, Integer count, Integer offset, User requester) throws ClassNotFoundException, SQLException {
        try (SqlSession session = getSqlSessionFactory().openSession(true)) {
            return session.getMapper(IPostMapper.class).getByCreator(creator, count, offset, requester);
        }
    }

    @Override
    public Post getById(Integer id, User requester) throws ClassNotFoundException, SQLException {
        try (SqlSession session = getSqlSessionFactory().openSession(true)) {
            return session.getMapper(IPostMapper.class).getById(id, requester);
        }
    }

    @Override
    public List<Post> getByImage(Image image, Integer count, Integer offset, User requester)
            throws ClassNotFoundException, SQLException {
        try (SqlSession session = getSqlSessionFactory().openSession(true)) {
            return session.getMapper(IPostMapper.class).getByImage(image, count, offset, requester);
        }
    }

    @Override
    public List<Post> getByIsPosted(boolean isPosted, Integer count, Integer offset, User requester)
            throws ClassNotFoundException, SQLException {
        try (SqlSession session = getSqlSessionFactory().openSession(true)) {
            return session.getMapper(IPostMapper.class).getByIsPosted(isPosted, count, offset, requester);
        }
    }

    @Override
    public List<Post> getByTitle(String title, Integer count, Integer offset, User requester)
            throws ClassNotFoundException, SQLException {
        try (SqlSession session = getSqlSessionFactory().openSession(true)) {
            return session.getMapper(IPostMapper.class).getByTitle(title, count, offset, requester);
        }
    }

    @Override
    public List<Post> getBySubstring(String substring, Integer count, Integer offset, User requester)
            throws ClassNotFoundException, SQLException {
        try (SqlSession session = getSqlSessionFactory().openSession(true)) {
            return session.getMapper(IPostMapper.class).getBySubstring(substring, count, offset, requester);
        }
    }
}
