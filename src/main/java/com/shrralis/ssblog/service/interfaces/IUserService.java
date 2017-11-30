package com.shrralis.ssblog.service.interfaces;

import com.shrralis.ssblog.entity.User;
import com.shrralis.tools.model.JsonResponse;

public interface IUserService {
    JsonResponse getAllUsers(User requester);

    JsonResponse signIn(String login, String password);

    JsonResponse signUp(String login, String password);
}
