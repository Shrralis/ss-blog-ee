package com.shrralis.ssblog.service;

import com.shrralis.ssblog.dao.interfaces.IUserDAO;
import com.shrralis.ssblog.dao.mybatis.UserMyBatisDAOImpl;
import com.shrralis.ssblog.dto.SetUserScopeDTO;
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
        userDAO = UserMyBatisDAOImpl.getDao();
    }

    @Override
    public JsonResponse setUserScope(SetUserScopeDTO dto) {
        if (dto.getUserId() == null || dto.getUserId() < 1) {
            return new JsonResponse(JsonError.Error.USER_ID_BAD);
        }

        if (dto.getUserScope() == null) {
            return new JsonResponse(JsonError.Error.SCOPE_BAD);
        }

        if (dto.getCookieUser() == null) {
            return new JsonResponse(JsonError.Error.NO_ACCESS);
        }

        try {
            User user = userDAO.getById(dto.getCookieUser().getId(), true);

            if (user == null ||
                    !user.getPassword().equals(dto.getCookieUser().getPassword()) ||
                    !User.Scope.ADMIN.equals(user.getScope())) {
                return new JsonResponse(JsonError.Error.NO_ACCESS);
            }

            user = userDAO.getById(dto.getUserId(), false);

            if (user == null) {
                return new JsonResponse(JsonError.Error.USER_NOT_EXISTS);
            }
            user.setScope(dto.getUserScope());
            return new JsonResponse(userDAO.edit(user));
        } catch (SQLException e) {
            logger.debug("Exception with setting user's scope!", e);
            return new JsonResponse(JsonError.Error.DATABASE);
        }
    }
}
