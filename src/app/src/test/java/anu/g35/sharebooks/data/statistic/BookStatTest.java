package anu.g35.sharebooks.data.statistic;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import anu.g35.sharebooks.data.statistic.BookStat;

/**
 * Test of the BookStat class
 *
 * @author u7706346 Anbo Wu
 * @since 2024-05-09
 */
@RunWith(RobolectricTestRunner.class)
public class BookStatTest {

    private BookStat bookStat;
    @Before
    public void setUp() {
        bookStat = new BookStat();
    }

    @Test
    public void testCategoryCount() {
        assertEquals(true, bookStat.categoryCount() != null);
    }

    @Test
    public void testPublishedYearCount() {
        assertEquals(true, bookStat.publishedYearCount() != null);
    }
}
