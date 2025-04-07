package anu.g35.sharebooks.data.search;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import anu.g35.sharebooks.data.model.Book;

/**
 * Test of the Books class
 *
 * @author Diao Fu
 * @since 2024-05-9
 */
@RunWith(RobolectricTestRunner.class)
public class BooksTest {


    private Books books;
    @Before
    public void setUp() {
        books = Books.getInstance();
    }

    @Test
    public void testGetCategories() {
        Set<String> categories = books.getCategories();
        assertEquals(true, categories.size()>0);
    }

    @Test
    public void testGetPublishedYears() {
        Set<Integer> publishedYears = books.getPublishedYears();
        assertEquals(true, publishedYears.size()>0);
    }

    @Test
    public void testSearchByISBNSet() {
        HashSet<Long> isbnSet = new HashSet<>();
        isbnSet.add(9780002005883L);
        isbnSet.add(9780006280934L);
        assertEquals(2, books.searchByISBNSet(isbnSet).size());
    }

    @Test
    public void testGetISBNSet() {
        assertEquals(true, books.getISBNSetByCategory("Art").size()>0);
        assertEquals(true, books.getISBNSetByPublishedYear(2000).size()>0);
        assertEquals(true, books.getISBNSetByOwner("test@t.t").size()>0);
        assertEquals(true, books.getISBNSetByBorrower("test@t.t").size()>0);
        assertEquals(true, books.getISBNSetByAuthors("white").size()>0);
    }




    @Test
    public void testSearch() {
        List<Book> bookList = books.searchByQueryString("");
        assertEquals(true, bookList.size()>0);

        assertEquals(true, books.searchByQueryString("white").size()> 0);
    }




}
