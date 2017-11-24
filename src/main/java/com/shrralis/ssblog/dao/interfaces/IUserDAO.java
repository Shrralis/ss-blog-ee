package com.shrralis.ssblog.dao.interfaces;

import com.shrralis.ssblog.entity.User;

import java.util.List;

public interface IUserDAO {
    User add(User user);

    boolean delete(User user);

    User edit(User user);

    List<User> getAllUsers();

    User getById(Integer id);

    User getByLogin(String login);

    User getByScope(User.Scope scope);
}
