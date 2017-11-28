package com.shrralis.ssblog.service.interfaces;

import com.shrralis.ssblog.dto.DeletePostDTO;
import com.shrralis.ssblog.dto.EditUpdaterDTO;
import com.shrralis.ssblog.dto.NewEditPostDTO;
import com.shrralis.ssblog.dto.SetPostedDTO;
import com.shrralis.ssblog.entity.User;
import com.shrralis.tools.model.JsonResponse;

public interface IPostService {
    JsonResponse addUpdater(EditUpdaterDTO dto);

    JsonResponse create(NewEditPostDTO postDTO);

    JsonResponse delete(DeletePostDTO postDTO);

    JsonResponse edit(NewEditPostDTO postDTO);

    JsonResponse get(Integer postId);

    JsonResponse getAll(User user);

    JsonResponse getUsersWithAccess(Integer postId, User user);

    JsonResponse revokeUpdater(EditUpdaterDTO dto);

    JsonResponse setPosted(SetPostedDTO postedDTO);
}
