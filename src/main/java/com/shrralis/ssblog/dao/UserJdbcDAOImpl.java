package com.shrralis.ssblog.dao;

import com.shrralis.ssblog.dao.base.JdbcBasedDAO;
import com.shrralis.ssblog.dao.interfaces.IUserDAO;
import com.shrralis.ssblog.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            return getByLogin(user.getLogin());
        } catch (SQLException e) {
            logger.debug("Error with inserting data into `users`!", e);
            return null;
        }
    }

    @Override
    public boolean delete(User user) {
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement("DELETE FROM users WHERE id = ?")) {
            preparedStatement.setInt(1, user.getId());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.debug("Error with deleting data from `users`!", e);
            return false;
        }
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
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement("SELECT * FROM users WHERE login LIKE ?")) {
            preparedStatement.setString(1, login);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new User.Builder()
                            .setId(resultSet.getInt("id"))
                            .setLogin(resultSet.getString("login"))
                            .setPassword(resultSet.getString("password"))
                            .setScope(User.Scope.get(resultSet.getString("scope")))
                            .build();
                }
            } catch (SQLException e) {
                logger.debug("Error with getting data from `user` by `login`!", e);
            }
        } catch (SQLException e) {
            logger.debug("Error with getting PreparedStatement for getting data from `user` by `login`!", e);
        }
        return null;
    }

    @Override
    public User getByScope(User.Scope scope) {
        return null;
    }
}
