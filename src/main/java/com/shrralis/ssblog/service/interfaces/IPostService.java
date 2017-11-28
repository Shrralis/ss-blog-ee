package com.shrralis.ssblog.service.interfaces;

import com.shrralis.ssblog.dto.*;
import com.shrralis.ssblog.entity.User;
import com.shrralis.tools.model.JsonResponse;

public interface IPostService {
    JsonResponse addUpdater(EditUpdaterDTO dto);

    JsonResponse create(NewPostDTO postDTO);

    JsonResponse delete(DeletePostDTO postDTO);

    JsonResponse edit(EditPostDTO postDTO);

    JsonResponse get(Integer postId);

    JsonResponse getAll(User user);

    JsonResponse revokeUpdater(EditUpdaterDTO dto);

    JsonResponse setPosted(SetPostedDTO postedDTO);
}
