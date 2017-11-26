package com.shrralis.ssblog.service.interfaces;

import com.shrralis.tools.model.JsonResponse;

public interface IUserService {
    JsonResponse getAllUsers();

    JsonResponse signIn(String login, String password);

    JsonResponse signUp(String login, String password);
}
