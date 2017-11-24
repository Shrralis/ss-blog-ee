package dao;

import com.shrralis.ssblog.dao.UserJdbcDAOImpl;
import com.shrralis.ssblog.entity.User;
import org.junit.Test;

import static org.junit.Assert.assertNull;

public class UserJdbcDAOTest {
    @Test
    public void addingUser() {
        assertNull(UserJdbcDAOImpl.getDao().add(new User.Builder()
                .setLogin("test")
                .setPassword("test")
                .setScope(User.Scope.WRITER)
                .build()));
    }
}
