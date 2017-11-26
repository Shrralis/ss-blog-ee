package com.shrralis.ssblog.dao;

import com.shrralis.ssblog.dao.base.JdbcBasedDAO;
import com.shrralis.ssblog.dao.interfaces.IUserDAO;
import com.shrralis.ssblog.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserJdbcDAOImpl extends JdbcBasedDAO implements IUserDAO {
    private static final String ID_COLUMN_NAME = "id";
    private static final String LOGIN_COLUMN_NAME = "login";
    private static final String PASS_COLUMN_NAME = "password";
    private static final String SCOPE_COLUMN_NAME = "scope";

    private static UserJdbcDAOImpl dao;

    private UserJdbcDAOImpl() throws ClassNotFoundException, SQLException {
        super();
    }

    public static UserJdbcDAOImpl getDao() throws ClassNotFoundException, SQLException {
        if (dao == null) {
            dao = new UserJdbcDAOImpl();
        }
        return dao;
    }

    @Override
    public User add(User user) throws SQLException {
        PreparedStatement preparedStatement = getConnection()
                .prepareStatement("INSERT INTO users (login, password, scope) VALUES (?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setString(1, user.getLogin());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getScope().name());

        if (preparedStatement.executeUpdate() == 0) {
            throw new SQLException("Creation user failed, no rows affected.");
        }

        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }
        return user;
    }

    @Override
    public void delete(User user) throws SQLException {
        PreparedStatement preparedStatement = getConnection()
                .prepareStatement("DELETE FROM users WHERE id = ?");
        preparedStatement.setInt(1, user.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public User edit(User user) throws SQLException {
        PreparedStatement preparedStatement = getConnection()
                .prepareStatement("UPDATE users SET login = ?, password = ?, scope = ? WHERE id = ?");

        preparedStatement.setString(1, user.getLogin());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getScope().name());
        preparedStatement.setInt(4, user.getId());
        preparedStatement.executeUpdate();
        return getByLogin(user.getLogin(), true);
    }

    @Override
    public List<User> getAllUsers(boolean withPassword) throws SQLException {
        ArrayList<User> result = new ArrayList<>();
        PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM users");
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            result.add(new User.Builder()
                    .setId(resultSet.getInt(ID_COLUMN_NAME))
                    .setLogin(resultSet.getString(LOGIN_COLUMN_NAME))
                    .setPassword(withPassword ? resultSet.getString(PASS_COLUMN_NAME) : null)
                    .setScope(User.Scope.get(resultSet.getString(SCOPE_COLUMN_NAME)))
                    .build());
        }
        return result;
    }

    @Override
    public User getById(Integer id, boolean withPassword) throws SQLException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM users WHERE id = ?");

        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return new User.Builder()
                    .setId(resultSet.getInt(ID_COLUMN_NAME))
                    .setLogin(resultSet.getString(LOGIN_COLUMN_NAME))
                    .setPassword(withPassword ? resultSet.getString(PASS_COLUMN_NAME) : null)
                    .setScope(User.Scope.get(resultSet.getString(SCOPE_COLUMN_NAME)))
                    .build();
        }
        return null;
    }

    User getById(Integer id) throws SQLException {
        return getById(id, false);
    }

    @Override
    public User getByLogin(String login, boolean withPassword) throws SQLException {
        PreparedStatement preparedStatement = getConnection()
                .prepareStatement("SELECT * FROM users WHERE login LIKE ?");

        preparedStatement.setString(1, login);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return new User.Builder()
                    .setId(resultSet.getInt(ID_COLUMN_NAME))
                    .setLogin(resultSet.getString(LOGIN_COLUMN_NAME))
                    .setPassword(withPassword ? resultSet.getString(PASS_COLUMN_NAME) : null)
                    .setScope(User.Scope.get(resultSet.getString(SCOPE_COLUMN_NAME)))
                    .build();
        }
        return null;
    }

    @Override
    public User getByScope(User.Scope scope, boolean withPassword) throws SQLException {
        if (scope == null) {
            return null;
        }

        PreparedStatement preparedStatement = getConnection()
                .prepareStatement("SELECT * FROM users WHERE scope LIKE ?");

        preparedStatement.setString(1, scope.name());

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return new User.Builder()
                    .setId(resultSet.getInt(ID_COLUMN_NAME))
                    .setLogin(resultSet.getString(LOGIN_COLUMN_NAME))
                    .setPassword(withPassword ? resultSet.getString(PASS_COLUMN_NAME) : null)
                    .setScope(User.Scope.get(resultSet.getString(SCOPE_COLUMN_NAME)))
                    .build();
        }
        return null;
    }
}
