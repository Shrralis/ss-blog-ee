package com.shrralis.ssblog.dao.mybatis;

import com.shrralis.ssblog.dao.base.MyBatisBasedDAO;
import com.shrralis.ssblog.dao.interfaces.IPostUpdaterDAO;
import com.shrralis.ssblog.dao.mybatis.mapper.IPostUpdaterMapper;
import com.shrralis.ssblog.entity.Post;
import com.shrralis.ssblog.entity.PostUpdater;
import com.shrralis.ssblog.entity.User;
import org.apache.ibatis.session.SqlSession;

import java.sql.SQLException;
import java.util.List;

public class PostUpdaterMyBatisDAOImpl extends MyBatisBasedDAO implements IPostUpdaterDAO {
    private static PostUpdaterMyBatisDAOImpl dao;

    private PostUpdaterMyBatisDAOImpl() {
        super();
    }

    public static PostUpdaterMyBatisDAOImpl getDao() {
        if (dao == null) {
            dao = new PostUpdaterMyBatisDAOImpl();
        }
        return dao;
    }

    @Override
    public PostUpdater add(PostUpdater postUpdater) throws SQLException {
        try (SqlSession session = getSqlSessionFactory().openSession(true)) {
            session.getMapper(IPostUpdaterMapper.class).add(postUpdater);
        }
        return postUpdater;
    }

    @Override
    public void delete(PostUpdater postUpdater) throws SQLException {
        try (SqlSession session = getSqlSessionFactory().openSession(true)) {
            session.getMapper(IPostUpdaterMapper.class).delete(postUpdater);
        }
    }

    @Override
    public PostUpdater get(Integer postId, Integer userId) throws ClassNotFoundException, SQLException {
        try (SqlSession session = getSqlSessionFactory().openSession(true)) {
            return session.getMapper(IPostUpdaterMapper.class).get(postId, userId);
        }
    }

    @Override
    public List<PostUpdater> getAllPostsUpdaters() throws ClassNotFoundException, SQLException {
        try (SqlSession session = getSqlSessionFactory().openSession(true)) {
            return session.getMapper(IPostUpdaterMapper.class).getAllPostsUpdaters();
        }
    }

    @Override
    public List<PostUpdater> getByPost(Post post) throws ClassNotFoundException, SQLException {
        try (SqlSession session = getSqlSessionFactory().openSession(true)) {
            return session.getMapper(IPostUpdaterMapper.class).getByPost(post);
        }
    }

    @Override
    public List<PostUpdater> getByUser(User user) throws ClassNotFoundException, SQLException {
        try (SqlSession session = getSqlSessionFactory().openSession(true)) {
            return session.getMapper(IPostUpdaterMapper.class).getByUser(user);
        }
    }
}
