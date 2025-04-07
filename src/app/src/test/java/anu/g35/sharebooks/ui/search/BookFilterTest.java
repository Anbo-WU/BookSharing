package anu.g35.sharebooks.ui.search;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;


import java.util.List;

import anu.g35.sharebooks.data.model.Book;
import anu.g35.sharebooks.data.model.FilterParameters;
import anu.g35.sharebooks.data.search.Books;
import anu.g35.sharebooks.ui.search.BookFilter;

/**
 * Test of the BookFilter class
 *
 * @author Huizhe_Ruan, u7723366
 * @since 2024-05-9
 */
@RunWith(RobolectricTestRunner.class)
public class BookFilterTest {
    @Test
    public void testFilterBooks() {
        // Test case 1
        List<Book> books = Books.getInstance().searchByQueryString("");

        assertEquals(true,books.size() >0);

        FilterParameters parameters = new FilterParameters();
        parameters.setAvailability(FilterParameters.Availability.AVAILABLE);
        List<Book> result = BookFilter.filterBooks(books, parameters);
        assertEquals(true,result.size() >0);


        parameters = new FilterParameters();
        parameters.setAvailability(FilterParameters.Availability.BORROWED);
        result = BookFilter.filterBooks(books, parameters);
        assertEquals(true,result.size() >0);

        parameters = new FilterParameters();
        parameters.setLikes(FilterParameters.Likes.LESS_THAN_50);
        result = BookFilter.filterBooks(books, parameters);
        assertEquals(true,result.size() >0);

        parameters = new FilterParameters();
        parameters.setLikes(FilterParameters.Likes.GREATER_THAN_50);
        result = BookFilter.filterBooks(books, parameters);
        assertEquals(true,result.size() >0);

    }
}
