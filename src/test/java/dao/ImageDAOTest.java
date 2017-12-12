package dao;

import com.shrralis.ssblog.dao.interfaces.IImageDAO;
import com.shrralis.ssblog.dao.mybatis.ImageMyBatisDAOImpl;
import com.shrralis.ssblog.entity.Image;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;

@RunWith(DataProviderRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ImageDAOTest {
    private static IImageDAO dao;
    private static Image[] data;

    @DataProvider
    public static Object[] dataForTestsAddAndDelete() {
        if (ImageDAOTest.data == null) {
            ImageDAOTest.data = new Image[]{
                    Image.Builder.anImage()
                            .setSrc("test1.jpg")
                            .build(),
                    Image.Builder.anImage()
                            .setSrc("test2.jpg")
                            .build(),
            };
        }
        return ImageDAOTest.data;
    }

    @DataProvider
    public static Object[][] dataForTestGet() {
        return new Object[][]{
                {2, true},
                {3, false}
        };
    }

    @Before
    public void setUp() throws Exception {
        dao = ImageMyBatisDAOImpl.getDao();
    }

    @After
    public void tearDown() throws Exception {
        ImageDAOTest.dao = null;
    }

    @Test
    @UseDataProvider("dataForTestsAddAndDelete")
    public void test1Add(Image image) throws Exception {
        Image result = dao.add(image);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertTrue(result.getId() > 0);
        assertEquals(image.getSrc(), result.getSrc());
        image.setId(result.getId());
    }

    @Test
    @UseDataProvider("dataForTestsAddAndDelete")
    public void test3Delete(Image image) throws Exception {
        try {
            dao.delete(image);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    @UseDataProvider("dataForTestGet")
    public void test2Get(Integer id, boolean withSrc) throws Exception {
        Image result = dao.get(id, withSrc);

        assertNotNull(result);

        if (withSrc) {
            assertNotNull(result.getSrc());
        } else {
            assertNull(result.getSrc());
        }
    }
}