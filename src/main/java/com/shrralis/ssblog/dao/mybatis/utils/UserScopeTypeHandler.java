package com.shrralis.ssblog.dao.mybatis.utils;

import com.shrralis.ssblog.entity.User;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserScopeTypeHandler extends BaseTypeHandler<User.Scope> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, User.Scope parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setString(i, parameter.name());
    }

    @Override
    public User.Scope getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return User.Scope.get(rs.getString(columnName));
    }

    @Override
    public User.Scope getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return User.Scope.get(rs.getString(columnIndex));
    }

    @Override
    public User.Scope getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return User.Scope.get(cs.getString(columnIndex));
    }
}
