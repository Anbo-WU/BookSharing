package anu.g35.sharebooks.data.datasource;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import anu.g35.sharebooks.data.model.User;


/**
 * Test of the UserDataSource class
 *
 * @author u7703248 Chuang Ma
 * @since 2024-05-9
 */
@RunWith(RobolectricTestRunner.class)
public class UserDataSourceTest {

        private UserDataSource userDataSource;

        @Before
        public void setUp() {
            userDataSource = UserDataSource.getInstance();
        }

        @Test
        public void testLogin() {
            assertEquals(true, userDataSource.login("admin", "admin") == null);
            assertEquals(true, userDataSource.login("test@t.t", "123456") != null);
        }

        @Test
        public void testGetUser() {
            assertEquals(true, userDataSource.getUser("test@t.t") != null);
            assertEquals(true, userDataSource.getUser("testaaa@t.t") == null);
        }

        @Test
        public void testUpdateUser() {
            User user = userDataSource.getUser("test@t.t");
            user.setName("test");
            assertEquals(true, userDataSource.updateUser(user));
            user.setId("test@t.taa");
            assertEquals(false, userDataSource.updateUser(user));
            assertEquals(false, userDataSource.updateUser(null));

        }
}
