package anu.g35.sharebooks.data.model;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import anu.g35.sharebooks.data.model.DailyStat;

/**
 * Test of the DailyStat class
 *
 * @author u7706346 Anbo Wu
 * @since 2024-05-09
 */
public class DailyStatTest {

    @Test
    public void testNewDailyStat() {
        DailyStat dailyStat = new DailyStat();

        dailyStat.setDate("2024-05-06");
        dailyStat.setRegisteredUsers(100);
        dailyStat.setActiveUsers(50);
        dailyStat.setBooksBorrowed(20);
        dailyStat.setBooksShared(30);

        assertEquals("2024-05-06", dailyStat.getDate());
        assertEquals(100, dailyStat.getRegisteredUsers());
        assertEquals(50, dailyStat.getActiveUsers());
        assertEquals(20, dailyStat.getBooksBorrowed());
        assertEquals(30, dailyStat.getBooksShared());

        assertEquals(true,dailyStat.toString() != null);
    }

}
