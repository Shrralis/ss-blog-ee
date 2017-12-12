package com.shrralis.ssblog.dao.interfaces;

import com.shrralis.ssblog.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserDAO {
    String ID_COLUMN_NAME = "id";
    String LOGIN_COLUMN_NAME = "login";
    String PASS_COLUMN_NAME = "password";
    String SCOPE_COLUMN_NAME = "scope";

    User add(User user) throws SQLException;

    void delete(User user) throws SQLException;

    User edit(User user) throws SQLException;

    List<User> getAllUsers(boolean withPassword) throws SQLException;

    User getById(Integer id, boolean withPassword) throws SQLException;

    User getByLogin(String login, boolean withPassword) throws SQLException;

    List<User> getByScope(User.Scope scope, boolean withPassword) throws SQLException;
}
