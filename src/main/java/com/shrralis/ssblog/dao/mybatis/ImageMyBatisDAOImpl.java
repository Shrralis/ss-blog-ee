package com.shrralis.ssblog.dao.mybatis;

import com.shrralis.ssblog.dao.base.MyBatisBasedDAO;
import com.shrralis.ssblog.dao.interfaces.IImageDAO;
import com.shrralis.ssblog.dao.mybatis.mapper.IImageMapper;
import com.shrralis.ssblog.entity.Image;
import org.apache.ibatis.session.SqlSession;

import java.sql.SQLException;

public class ImageMyBatisDAOImpl extends MyBatisBasedDAO implements IImageDAO {
    private static ImageMyBatisDAOImpl dao;

    private ImageMyBatisDAOImpl() {
        super();
    }

    public static ImageMyBatisDAOImpl getDao() {
        if (dao == null) {
            dao = new ImageMyBatisDAOImpl();
        }
        return dao;
    }

    @Override
    public Image add(Image image) throws SQLException {
        try (SqlSession session = getSqlSessionFactory().openSession(true)) {
            image.setId(session.getMapper(IImageMapper.class).add(image));
        }
        return image;
    }

    @Override
    public void delete(Image image) throws SQLException {
        try (SqlSession session = getSqlSessionFactory().openSession(true)) {
            session.getMapper(IImageMapper.class).delete(image);
        }
    }

    @Override
    public Image get(Integer id, boolean withSrc) throws SQLException {
        try (SqlSession session = getSqlSessionFactory().openSession(true)) {
            return session.getMapper(IImageMapper.class).get(id, withSrc);
        }
    }
}
