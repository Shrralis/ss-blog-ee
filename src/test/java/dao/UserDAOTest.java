package dao;

import com.shrralis.ssblog.dao.interfaces.IUserDAO;
import com.shrralis.ssblog.dao.mybatis.UserMyBatisDAOImpl;
import com.shrralis.ssblog.entity.User;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(DataProviderRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserDAOTest {
    private static IUserDAO dao;

    public UserDAOTest() throws ClassNotFoundException, SQLException {
//        UserDAOTest.dao = UserJdbcDAOImpl.getDao();
        UserDAOTest.dao = UserMyBatisDAOImpl.getDao();
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
        } catch (SQLException e) {
            e.printStackTrace();
            fail(e.getClass().getName());
        }
        assertNotNull(addingResult);
        System.out.println("Added " + addingResult);
        assertNotNull(addingResult.getId());
        assertEquals(user.getLogin(), addingResult.getLogin());
    }

    @Test
    @UseDataProvider("dataForDeleteUserByLoginWithoutExceptions")
    public void deleteUserByLoginWithoutExceptions(User userWithLogin) {
        try {
            dao.delete(dao.getByLogin(userWithLogin.getLogin(), false));
        } catch (SQLException e) {
            e.printStackTrace();
            fail(e.getClass().getName());
        }
    }

    @Test
    public void getAllUsersIsNotNull() {
        List<User> users = new ArrayList<>();

        try {
            users = dao.getAllUsers(false);

            System.out.println(users.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertNotEquals(users.size(), 0);
    }
}
