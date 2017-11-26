package com.shrralis.ssblog.service;

import com.shrralis.ssblog.dao.UserJdbcDAOImpl;
import com.shrralis.ssblog.dao.interfaces.IUserDAO;
import com.shrralis.ssblog.entity.User;
import com.shrralis.ssblog.service.interfaces.IAdminService;
import com.shrralis.tools.model.JsonError;
import com.shrralis.tools.model.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class AdminServiceImpl implements IAdminService {
    private static Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
    private IUserDAO userDAO;

    public AdminServiceImpl() throws ClassNotFoundException, SQLException {
        userDAO = UserJdbcDAOImpl.getDao();
    }

    @Override
    public JsonResponse setUserScope(Integer userId, User.Scope newScope) {
        if (userId == null || userId < 1) {
            return new JsonResponse(JsonError.Error.BAD_USER_ID);
        }

        if (newScope == null) {
            return new JsonResponse(JsonError.Error.BAD_SCOPE);
        }

        try {
            User user = userDAO.getById(userId, false);

            if (user == null) {
                return new JsonResponse(JsonError.Error.USER_NOT_EXISTS);
            }
            user.setScope(newScope);
            return new JsonResponse(userDAO.edit(user));
        } catch (SQLException e) {
            logger.debug("Exception with setting user's scope!", e);
            return new JsonResponse(JsonError.Error.DATABASE);
        }
    }
}
