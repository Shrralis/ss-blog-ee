package com.shrralis.ssblog.dao;

import com.shrralis.ssblog.dao.base.JdbcBasedDAO;
import com.shrralis.ssblog.dao.interfaces.IImageDAO;
import com.shrralis.ssblog.entity.Image;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ImageJdbcDAOImpl extends JdbcBasedDAO implements IImageDAO {
    private static final String ID_COLUMN_NAME = "id";
    private static final String SRC_COLUMN_NAME = "src";

    private static ImageJdbcDAOImpl dao;

    private ImageJdbcDAOImpl() throws ClassNotFoundException, SQLException {
        super();
    }

    public static ImageJdbcDAOImpl getDao() throws ClassNotFoundException, SQLException {
        if (dao == null) {
            dao = new ImageJdbcDAOImpl();
        }
        return dao;
    }

    @Override
    public Image add(Image image) throws SQLException {
        PreparedStatement preparedStatement = getConnection()
                .prepareStatement("INSERT INTO images (" + SRC_COLUMN_NAME + ")" +
                        "VALUES (?)", Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setString(1, image.getSrc());

        if (preparedStatement.executeUpdate() == 0) {
            throw new SQLException("Creating image failed, no rows affected.");
        }

        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                image.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating image failed, no ID obtained.");
            }
        }
        return image;
    }

    @Override
    public void delete(Image image) throws SQLException {
        PreparedStatement preparedStatement = getConnection()
                .prepareStatement("DELETE FROM images WHERE " + ID_COLUMN_NAME + " = ?");

        preparedStatement.setInt(1, image.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public Image get(Integer id, boolean withSrc) throws SQLException {
        PreparedStatement preparedStatement = getConnection()
                .prepareStatement("SELECT * FROM images WHERE " + ID_COLUMN_NAME + " = ?");

        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return Image.Builder.anImage()
                    .setId(resultSet.getInt(ID_COLUMN_NAME))
                    .setSrc(withSrc ? resultSet.getString(SRC_COLUMN_NAME) : null)
                    .build();
        }
        return null;
    }
}
