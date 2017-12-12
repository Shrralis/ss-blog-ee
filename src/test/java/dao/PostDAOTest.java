package dao;

import com.shrralis.ssblog.dao.interfaces.IPostDAO;
import com.shrralis.ssblog.dao.mybatis.PostMyBatisDAOImpl;
import com.shrralis.ssblog.entity.Post;
import com.shrralis.ssblog.entity.User;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotEquals;

public class PostDAOTest {
    private static IPostDAO dao;

    public PostDAOTest() throws ClassNotFoundException, SQLException {
        PostDAOTest.dao = PostMyBatisDAOImpl.getDao();
    }

    @Test
    public void getAllPosts() throws Exception {
        List<Post> posts = new ArrayList<>();

        try {
            posts = dao.getAllPosts(null, null, User.Builder.anUser().setId(2).build());

            System.out.println(posts.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertNotEquals(posts.size(), 0);
    }
}