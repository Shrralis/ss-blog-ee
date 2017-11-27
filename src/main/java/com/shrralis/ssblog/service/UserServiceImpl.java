package com.shrralis.ssblog.service;

import com.shrralis.ssblog.dao.UserJdbcDAOImpl;
import com.shrralis.ssblog.dao.interfaces.IUserDAO;
import com.shrralis.ssblog.entity.User;
import com.shrralis.ssblog.service.interfaces.IUserService;
import com.shrralis.tools.SecurityTool;
import com.shrralis.tools.TextUtil;
import com.shrralis.tools.model.JsonError;
import com.shrralis.tools.model.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class UserServiceImpl implements IUserService {
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private IUserDAO dao;

    public UserServiceImpl() throws ClassNotFoundException, SQLException {
        dao = UserJdbcDAOImpl.getDao();
    }

    @Override
    public JsonResponse getAllUsers() {
        try {
            return new JsonResponse(dao.getAllUsers(false));
        } catch (SQLException e) {
            logger.debug("Exception with getting all users!", e);
            return new JsonResponse(JsonError.Error.DATABASE);
        }
    }

    @Override
    public JsonResponse signIn(String login, String password) {
        if (TextUtil.isEmpty(login)) {
            return new JsonResponse(JsonError.Error.EMPTY_LOGIN);
        }

        if (TextUtil.isEmpty(password)) {
            return new JsonResponse(JsonError.Error.EMPTY_PASSWORD);
        }

        try {
            password = SecurityTool.md5(SecurityTool.md5(SecurityTool.md5(password)));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            logger.debug("Exception with getting MD5 of string!", e);
            return new JsonResponse(JsonError.Error.UNEXPECTED);
        }

        User user;

        try {
            user = dao.getByLogin(login, true);
        } catch (SQLException e) {
            logger.debug("Exception with getting user by login!", e);
            return new JsonResponse(JsonError.Error.DATABASE);
        }

        if (user == null) {
            return new JsonResponse(JsonError.Error.USER_NOT_EXISTS);
        }

        if (!password.equals(user.getPassword())) {
            return new JsonResponse(JsonError.Error.INCORRECT_PASSWORD);
        }
        return new JsonResponse(user);
    }

    @Override
    public JsonResponse signUp(String login, String password) {
        if (TextUtil.isEmpty(login)) {
            return new JsonResponse(JsonError.Error.EMPTY_LOGIN);
        }

        if (TextUtil.isEmpty(password)) {
            return new JsonResponse(JsonError.Error.EMPTY_PASSWORD);
        }

        try {
            password = SecurityTool.md5(SecurityTool.md5(SecurityTool.md5(password)));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            logger.debug("Exception with getting MD5 of string!", e);
            return new JsonResponse(JsonError.Error.UNEXPECTED);
        }

        try {
            if (dao.getByLogin(login, false) != null) {
                return new JsonResponse(JsonError.Error.USER_ALREADY_EXISTS);
            }
            return new JsonResponse(dao.add(new User.Builder()
                    .setLogin(login)
                    .setPassword(password)
                    .build()));
        } catch (SQLException e) {
            logger.debug("Exception with adding new user!", e);
            return new JsonResponse(JsonError.Error.DATABASE);
        }
    }
}
