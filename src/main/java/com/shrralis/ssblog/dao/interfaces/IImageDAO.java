package com.shrralis.ssblog.dao.interfaces;

import com.shrralis.ssblog.entity.Image;

import java.sql.SQLException;

public interface IImageDAO {
    Image add(Image image) throws SQLException;

    void delete(Image image) throws SQLException;

    Image get(Integer id, boolean withSrc) throws SQLException;
}
