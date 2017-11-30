package com.shrralis.ssblog.service.interfaces;

import com.shrralis.ssblog.dto.*;
import com.shrralis.ssblog.entity.User;
import com.shrralis.tools.model.JsonResponse;

public interface IPostService {
    JsonResponse addUpdater(EditUpdaterDTO dto);

    JsonResponse create(NewEditPostDTO postDTO);

    JsonResponse delete(DeletePostDTO postDTO);

    JsonResponse edit(NewEditPostDTO postDTO);

    JsonResponse get(GetPostDTO dto);

    JsonResponse getAll(User requester);

    JsonResponse getByUser(GetPostDTO dto);

    JsonResponse getUsersWithAccess(GetPostDTO dto);

    JsonResponse revokeUpdater(EditUpdaterDTO dto);

    JsonResponse search(String word, User requester);

    JsonResponse setPosted(SetPostedDTO dto);
}
