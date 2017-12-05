package dao;

import com.shrralis.ssblog.dao.UserJdbcDAOImpl;
import com.shrralis.ssblog.dao.interfaces.IUserDAO;
import com.shrralis.ssblog.entity.User;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.sql.SQLException;

import static org.junit.Assert.*;

@RunWith(DataProviderRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserDAOTest {
    private static IUserDAO dao;

    public UserDAOTest() throws ClassNotFoundException, SQLException {
        UserDAOTest.dao = UserJdbcDAOImpl.getDao();
    }

//    @Rule
//    public ExpectedException thrown = ExpectedException.none();

    @DataProvider
    public static Object[] dataForAddUserAndGetSameAddedUser() {
        return new User[]{
                User.Builder.anUser()
                        .setLogin("test1")
                        .setPassword("test1")
                        .setScope(User.Scope.WRITER)
                        .build()
        };
    }

    @DataProvider
    public static Object[] dataForAddWrongUserAndGetSQLException() {
        return new User[]{
                User.Builder.anUser()
                        .setLogin("test1")
                        .setScope(User.Scope.WRITER)
                        .build(),
                User.Builder.anUser()
                        .setPassword("test1")
                        .setScope(User.Scope.WRITER)
                        .build(),
                User.Builder.anUser()
                        .setLogin("test1")
                        .setPassword("test1")
                        .build(),
        };
    }

    @DataProvider
    public static Object[] dataForDeleteUserByLoginWithoutExceptions() {
        return new User[]{
                User.Builder.anUser()
                        .setLogin("test1")
                        .build()
        };
    }

    @Test
    @UseDataProvider("dataForAddUserAndGetSameAddedUser")
    public void addUserAndGetSameAddedUser(User user) {
        User addingResult = null;

        try {
            addingResult = dao.add(user);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            fail(e.getClass().getName());
        }
        assertNotNull(addingResult);
        assertEquals(user.getLogin(), addingResult.getLogin());
    }

    @Test(expected = SQLException.class)
    @UseDataProvider("dataForAddWrongUserAndGetSQLException")
    public void addWrongUserAndGetSQLException(User user) throws SQLException {
        assertNull(dao.add(user));
    }

    @Test
    @UseDataProvider("dataForDeleteUserByLoginWithoutExceptions")
    public void deleteUserByLoginWithoutExceptions(User userWithLogin) {
        try {
            dao.delete(dao.getByLogin(userWithLogin.getLogin(), false));
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            fail(e.getClass().getName());
        }
    }
}
