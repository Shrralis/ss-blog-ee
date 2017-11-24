package com.shrralis.ssblog.dao;

import com.shrralis.ssblog.dao.base.JdbcBasedDAO;
import com.shrralis.ssblog.dao.interfaces.IUserDAO;
import com.shrralis.ssblog.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserJdbcDAOImpl extends JdbcBasedDAO implements IUserDAO {
    private static final Logger logger = LoggerFactory.getLogger(UserJdbcDAOImpl.class);

    private static UserJdbcDAOImpl dao;

    static {
        try {
            dao = new UserJdbcDAOImpl();
        } catch (ClassNotFoundException | SQLException e) {
            logger.debug("Unable to create connection!", e);
        }
    }

    private UserJdbcDAOImpl() throws ClassNotFoundException, SQLException {
        super();
    }

    public static UserJdbcDAOImpl getDao() {
        return dao;
    }

    @Override
    public User add(User user) {
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement("INSERT INTO users (login, password, scope) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getScope().name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.debug("Error with inserting data into `users`!", e);
        }
        return getByLogin(user.getLogin());
    }

    @Override
    public boolean delete(User user) {
        return false;
    }

    @Override
    public User edit(User user) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>();
    }

    @Override
    public User getById(Integer id) {
        return null;
    }

    @Override
    public User getByLogin(String login) {
        return null;
    }

    @Override
    public User getByScope(User.Scope scope) {
        return null;
    }
}
