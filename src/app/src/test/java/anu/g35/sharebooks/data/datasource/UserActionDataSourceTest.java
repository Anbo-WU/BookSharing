package anu.g35.sharebooks.data.datasource;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;


/**
 *  Test of the UserActionDataSource class
 *
 * @author u7703248 Chuang Ma
 * @since 2024-05-9
 */
@RunWith(RobolectricTestRunner.class)
public class UserActionDataSourceTest {

        private UserActionDataSource userActionDataSource;

        @Before
        public void setUp() {
            userActionDataSource = UserActionDataSource.getInstance();
        }

        @Test
        public void testGetUserActions() {
            assertEquals(true, userActionDataSource.getUserActions() != null);
        }
}
