package anu.g35.sharebooks.data.datasource;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import anu.g35.sharebooks.data.datasource.DailyStatDataSource;


/**
 * Test of the DailyStatDataSource class
 *
 * @author u7706346 Anbo Wu
 * @since 2024-05-09
 */
@RunWith(RobolectricTestRunner.class)
public class DailyStatDataSourceTest {

    private DailyStatDataSource dailyStatDataSource;

    @Before
    public void setUp() {
        dailyStatDataSource = DailyStatDataSource.getInstance();
    }

    @Test
    public void testGetDailyStats() {
        assertEquals(true, dailyStatDataSource.getDailyStats() != null);
    }
}
