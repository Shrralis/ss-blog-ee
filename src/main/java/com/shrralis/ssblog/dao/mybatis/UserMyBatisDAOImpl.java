package com.shrralis.ssblog.dao.mybatis;

import com.shrralis.ssblog.dao.base.MyBatisBasedDAO;
import com.shrralis.ssblog.dao.interfaces.IUserDAO;
import com.shrralis.ssblog.dao.mybatis.mapper.IUserMapper;
import com.shrralis.ssblog.entity.User;
import org.apache.ibatis.session.SqlSession;

import java.sql.SQLException;
import java.util.List;

public class UserMyBatisDAOImpl extends MyBatisBasedDAO implements IUserDAO {
    private static UserMyBatisDAOImpl dao;

    private UserMyBatisDAOImpl() {
        super();
    }

    public static UserMyBatisDAOImpl getDao() {
        if (dao == null) {
            dao = new UserMyBatisDAOImpl();
        }
        return dao;
    }

    @Override
    public User add(User user) throws SQLException {
        try (SqlSession session = getSqlSessionFactory().openSession(true)) {
            user.setId(session.getMapper(IUserMapper.class).add(user));
        }
        return user;
    }

    @Override
    public void delete(User user) throws SQLException {
        try (SqlSession session = getSqlSessionFactory().openSession(true)) {
            session.getMapper(IUserMapper.class).delete(user);
        }
    }

    @Override
    public User edit(User user) throws SQLException {
        try (SqlSession session = getSqlSessionFactory().openSession(true)) {
            session.getMapper(IUserMapper.class).edit(user);
            return session.getMapper(IUserMapper.class).getById(user.getId(), false);
        }
    }

    @Override
    public List<User> getAllUsers(boolean withPassword) throws SQLException {
        try (SqlSession session = getSqlSessionFactory().openSession(true)) {
            return session.getMapper(IUserMapper.class).getAllUsers(withPassword);
        }
    }

    @Override
    public User getById(Integer id, boolean withPassword) throws SQLException {
        try (SqlSession session = getSqlSessionFactory().openSession(true)) {
            return session.getMapper(IUserMapper.class).getById(id, withPassword);
        }
    }

    @Override
    public User getByLogin(String login, boolean withPassword) throws SQLException {
        try (SqlSession session = getSqlSessionFactory().openSession(true)) {
            return session.getMapper(IUserMapper.class).getByLogin(login, withPassword);
        }
    }

    @Override
    public List<User> getByScope(User.Scope scope, boolean withPassword) throws SQLException {
        try (SqlSession session = getSqlSessionFactory().openSession(true)) {
            return session.getMapper(IUserMapper.class).getByScope(scope, withPassword);
        }
    }
}
