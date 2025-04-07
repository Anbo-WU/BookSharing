package anu.g35.sharebooks.data.datasource;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;


import anu.g35.sharebooks.ShareBooks;

/**
 * Test of the BookDataSource class
 *
 * @author Diao Fu
 * @since 2024-05-9
 */
@RunWith(RobolectricTestRunner.class)
public class BookDataSourceTest {


    private ShareBooks app;
    BookDataSource bookDataSource;

    @Before
    public void setUp() {
        // app = (ShareBooks) ApplicationProvider.getApplicationContext();
        bookDataSource = BookDataSource.getInstance();
    }

    /**
     * Test of getBook method, of class BookDataSource.
     */
    @Test
    public void testGetBook() {
        assertEquals(9780002005883L, bookDataSource.getBookByISBN(9780002005883L).getIsbn().longValue());
        assertEquals(true, bookDataSource.getAllBooks() != null);
    }

    @Test
    public void testBorrowBook() {
        assertEquals(false, bookDataSource.borrowBook(111L, "abc"));
        assertEquals(false, bookDataSource.borrowBook(9780002005883L, "test@t.t"));
        assertEquals(true, bookDataSource.borrowBook(9780006646006L, "test@t.t"));
        assertEquals(true, bookDataSource.borrowBook(9780007103676L, "test2@t.t"));

        assertEquals(false, bookDataSource.returnBook(111L, "abc"));
        assertEquals(true, bookDataSource.returnBook(9780006646006L, "test@t.t"));
    }

    @Test
    public void testGetAtrributes() {
        assertEquals(true, bookDataSource.getCategories() != null);
        assertEquals(true, bookDataSource.getPublishedYears() != null);
        assertEquals(true, bookDataSource.getBorrower() != null);
        assertEquals(true, bookDataSource.getOwners() != null);
        assertEquals(true, bookDataSource.getAuthorWords() != null);
        assertEquals(true, bookDataSource.getTitleWords() != null);

    }


}