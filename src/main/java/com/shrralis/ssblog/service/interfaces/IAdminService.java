package com.shrralis.ssblog.service.interfaces;

import com.shrralis.ssblog.dto.SetUserScopeDTO;
import com.shrralis.tools.model.JsonResponse;

public interface IAdminService {
    JsonResponse setUserScope(SetUserScopeDTO dto);
}
