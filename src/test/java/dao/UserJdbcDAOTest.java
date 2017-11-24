package dao;

import com.shrralis.ssblog.dao.UserJdbcDAOImpl;
import com.shrralis.ssblog.entity.User;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserJdbcDAOTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void addUserAndSameAddedUser() {
        User addingResult = UserJdbcDAOImpl.getDao().add(new User.Builder()
                .setLogin("test1")
                .setPassword("test1")
                .setScope(User.Scope.WRITER)
                .build());

        assertNotNull(addingResult);
        assertEquals("test1", addingResult.getLogin());
    }

    @Test
    public void deleteUserByLoginAndGetTrue() {
        assertTrue(UserJdbcDAOImpl.getDao().delete(UserJdbcDAOImpl.getDao().getByLogin("test1")));
    }
}
