package com.shrralis.ssblog.dao.base;

import com.shrralis.ssblog.dao.mybatis.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSessionFactory;

public class MyBatisBasedDAO {
    private SqlSessionFactory sqlSessionFactory;

    protected MyBatisBasedDAO() {
        sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
    }

    protected SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}
