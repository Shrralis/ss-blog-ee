package com.shrralis.ssblog.dao.mybatis.mapper;

import com.shrralis.ssblog.entity.User;
import org.apache.ibatis.annotations.*;

import java.sql.SQLException;
import java.util.List;

import static com.shrralis.ssblog.dao.interfaces.IUserDAO.*;

public interface IUserMapper {

    @Insert("INSERT INTO users (" + ID_COLUMN_NAME + ", " + LOGIN_COLUMN_NAME + ", " + PASS_COLUMN_NAME + ", " +
            SCOPE_COLUMN_NAME + ") " + "VALUES (#{id}, #{login}, #{password}, #{scope})")
    @SelectKey(statement = "SELECT nextVal('users_id_seq')", keyProperty = "id",
            before = true, resultType = int.class)
    int add(User user) throws SQLException;

    @Delete("DELETE FROM users WHERE " + ID_COLUMN_NAME + " = #{id}")
    void delete(User user);

    @Update("UPDATE users SET " + LOGIN_COLUMN_NAME + " = #{login}, " + PASS_COLUMN_NAME + " = #{password}, " +
            SCOPE_COLUMN_NAME + " = #{scope}")
    void edit(User user);

    @Select("SELECT " + ID_COLUMN_NAME + ", " + LOGIN_COLUMN_NAME + ", (CASE WHEN #{withPassword} THEN " +
            PASS_COLUMN_NAME + " ELSE null END) AS " + PASS_COLUMN_NAME + ", " + SCOPE_COLUMN_NAME + " FROM users")
    List<User> getAllUsers(@Param("withPassword") boolean withPassword);

    @Select("SELECT " + ID_COLUMN_NAME + ", " + LOGIN_COLUMN_NAME + ", (CASE WHEN #{withPassword} THEN " +
            PASS_COLUMN_NAME + " ELSE null END) AS " + PASS_COLUMN_NAME + ", " + SCOPE_COLUMN_NAME +
            " FROM users WHERE id = #{id}")
    User getById(@Param("id") Integer id,
                 @Param("withPassword") boolean withPassword);

    @Select("SELECT " + ID_COLUMN_NAME + ", " + LOGIN_COLUMN_NAME + ", null AS " + PASS_COLUMN_NAME + ", " +
            SCOPE_COLUMN_NAME + " FROM users WHERE id = #{id}")
    User getByIdForMapper(@Param("id") Integer id);

    @Select("SELECT " + ID_COLUMN_NAME + ", " + LOGIN_COLUMN_NAME + ", (CASE WHEN #{withPassword} THEN " +
            PASS_COLUMN_NAME + " ELSE null END) AS " + PASS_COLUMN_NAME + ", " + SCOPE_COLUMN_NAME +
            " FROM users WHERE " + LOGIN_COLUMN_NAME + "= #{login}")
    User getByLogin(@Param("login") String login,
                    @Param("withPassword") boolean withPassword);

    @Select("SELECT " + ID_COLUMN_NAME + ", " + LOGIN_COLUMN_NAME + ", (CASE WHEN #{withPassword} THEN " +
            PASS_COLUMN_NAME + " ELSE null END) AS " + PASS_COLUMN_NAME + ", " + SCOPE_COLUMN_NAME +
            " FROM users WHERE " + SCOPE_COLUMN_NAME + " = #{scope}")
    List<User> getByScope(@Param("scope") User.Scope scope,
                          @Param("withPassword") boolean withPassword);
}
