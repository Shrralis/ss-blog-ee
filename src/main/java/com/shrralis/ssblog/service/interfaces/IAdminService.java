package com.shrralis.ssblog.service.interfaces;

import com.shrralis.ssblog.entity.User;
import com.shrralis.tools.model.JsonResponse;

public interface IAdminService {
    JsonResponse setUserScope(Integer userId, User.Scope newScope, String adminLogin, String adminPassword);
}
