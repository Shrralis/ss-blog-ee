package com.shrralis.ssblog.dao.mybatis.mapper;

import com.shrralis.ssblog.entity.Image;
import org.apache.ibatis.annotations.*;

import static com.shrralis.ssblog.dao.interfaces.IImageDAO.ID_COLUMN_NAME;
import static com.shrralis.ssblog.dao.interfaces.IImageDAO.SRC_COLUMN_NAME;

public interface IImageMapper {
    @Insert("INSERT INTO images (" + ID_COLUMN_NAME + ", " + SRC_COLUMN_NAME + ") VALUES (#{id}, #{src})")
    @SelectKey(statement = "SELECT nextVal('images_id_seq')", keyProperty = "id", before = true, resultType = int.class)
    int add(Image image);

    @Delete("DELETE FROM images WHERE " + ID_COLUMN_NAME + " = #{id}")
    void delete(Image image);

    @Select("SELECT id, (CASE WHEN #{withSrc} THEN " + SRC_COLUMN_NAME + " ELSE null END) AS " + SRC_COLUMN_NAME +
            " FROM images WHERE " + ID_COLUMN_NAME + " = #{id}")
    Image get(@Param("id") Integer id, @Param("withSrc") boolean withSrc);

    @Select("SELECT id, null AS " + SRC_COLUMN_NAME + " FROM images WHERE " + ID_COLUMN_NAME + " = #{id}")
    Image getForMapper(@Param("id") Integer id);
}
