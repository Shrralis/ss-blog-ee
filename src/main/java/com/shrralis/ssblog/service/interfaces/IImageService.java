package com.shrralis.ssblog.service.interfaces;

import com.shrralis.ssblog.dto.AddImageDTO;
import com.shrralis.ssblog.dto.DeleteImageDTO;
import com.shrralis.ssblog.dto.GetImageDTO;
import com.shrralis.tools.model.JsonResponse;

import java.sql.SQLException;

public interface IImageService {
    JsonResponse add(AddImageDTO dto);

    JsonResponse delete(DeleteImageDTO dto);

    JsonResponse get(GetImageDTO dto) throws SQLException;
}
