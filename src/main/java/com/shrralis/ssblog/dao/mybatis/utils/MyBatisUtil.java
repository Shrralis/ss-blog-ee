package com.shrralis.ssblog.dao.mybatis.utils;

import com.shrralis.ssblog.config.DatabaseConfig;
import com.shrralis.ssblog.dao.mybatis.mapper.IImageMapper;
import com.shrralis.ssblog.dao.mybatis.mapper.IPostMapper;
import com.shrralis.ssblog.dao.mybatis.mapper.IPostUpdaterMapper;
import com.shrralis.ssblog.dao.mybatis.mapper.IUserMapper;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.type.LocalDateTimeTypeHandler;

import javax.sql.DataSource;

public final class MyBatisUtil {
    private static SqlSessionFactory sqlSessionFactory;

    private MyBatisUtil() {
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        if (sqlSessionFactory != null) {
            return sqlSessionFactory;
        }

        DataSource dataSource = new PooledDataSource(
                DatabaseConfig.DRIVER_CLASS_NAME,
                DatabaseConfig.JDBC_PROTOCOL + "://" +
                        DatabaseConfig.HOST + ":" + DatabaseConfig.PORT + "/" + DatabaseConfig.DB_NAME,
                DatabaseConfig.USER,
                DatabaseConfig.PASS);
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);

        configuration.addMapper(IUserMapper.class);
        configuration.addMapper(IImageMapper.class);
        configuration.addMapper(IPostMapper.class);
        configuration.addMapper(IPostUpdaterMapper.class);
        configuration.getTypeHandlerRegistry().register(LocalDateTimeTypeHandler.class);
        configuration.getTypeHandlerRegistry().register(UserScopeTypeHandler.class);

        sqlSessionFactory = new SqlSessionFactoryBuilder()
                .build(configuration);

        return sqlSessionFactory;
    }
}
