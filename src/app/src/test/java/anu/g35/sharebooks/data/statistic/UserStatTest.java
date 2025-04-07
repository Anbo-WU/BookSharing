package anu.g35.sharebooks.data.statistic;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import anu.g35.sharebooks.data.statistic.UserStat;

/**
 * Test of the BookStat class
 *
 * @author u7706346 Anbo Wu
 * @since 2024-05-09
 */
@RunWith(RobolectricTestRunner.class)
public class UserStatTest {

    private UserStat userStat;
    @Before
    public void setUp() {
        userStat = new UserStat();
    }

    @Test
    public void testUserCount() {
        assertEquals(true, userStat.dailyCount().size()>0);
    }

}
