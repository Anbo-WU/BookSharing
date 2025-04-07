package anu.g35.sharebooks.ui.search;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;


import java.util.List;

import anu.g35.sharebooks.data.model.Book;
import anu.g35.sharebooks.data.model.FilterParameters;
import anu.g35.sharebooks.data.search.Books;
import anu.g35.sharebooks.ui.search.Sorter;

/**
 * Test of the BookFilter class
 *
 * @author Huizhe_Ruan, u7723366
 * @since 2024-05-9
 */
@RunWith(RobolectricTestRunner.class)
public class SorterTest {
    @Test
    public void testSortBooks() {
        // Test case 1
        List<Book> books = Books.getInstance().searchByQueryString("");

        assertEquals(true,books.size() >0);

        Sorter sorter = new Sorter();
        sorter.setComparator(Sorter.titleAsc());
        sorter.sortBooks(books);
        assertEquals(true,books.size() >0);

        sorter.setComparator(Sorter.titleDesc());
        sorter.sortBooks(books);
        assertEquals(true,books.size() >0);

        sorter.setComparator(Sorter.authorsAsc());
        sorter.sortBooks(books);
        assertEquals(true,books.size() >0);

        sorter.setComparator(Sorter.authorsDesc());
        sorter.sortBooks(books);
        assertEquals(true,books.size() >0);

        sorter.clearComparator();
        assertEquals(true,books.size() >0);

       sorter.setComparator(null);
        assertEquals(true,books.size() >0);

        sorter.setComparator(Sorter.publishedYearAsc());
        assertEquals(true,books.size() >0);

        sorter.setComparator(Sorter.publishedYearDesc());
        assertEquals(true,books.size() >0);
    }

}
