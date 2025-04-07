package anu.g35.sharebooks.data.model;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Test of the Book class
 *
 * @author Diao Fu
 * @since 2024-05-9
 */
public class BookTest {

    @Test
    public void testNewBook() {
        Book book = new Book();

        book.setIsbn(9780002005883L);
        book.setTitle("The Hobbit");
        book.setAuthors("J.R.R. Tolkien");
        book.setCategory("Fantasy");
        book.setThumbnail("https://images-na.ssl-images-amazon.com/images/I/51Zymoq7UnL._AC_SY400_.jpg");
        book.setDescription("The Hobbit is a fantasy novel by J.R.R. Tolkien.");
        book.setPublishedYear(1937);
        book.setAverageRating(4.5);
        book.setRatingsCount(1000);
        book.setLikedCount(500);
        book.setNumPages(310);
        book.setBorrower("Alice");
        book.setOwner("Bob");

        assertEquals(9780002005883L, book.getIsbn().longValue());
        assertEquals("The Hobbit", book.getTitle());
        assertEquals("J.R.R. Tolkien", book.getAuthors());
        assertEquals("Fantasy", book.getCategory());
        assertEquals("https://images-na.ssl-images-amazon.com/images/I/51Zymoq7UnL._AC_SY400_.jpg", book.getThumbnail());
        assertEquals("The Hobbit is a fantasy novel by J.R.R. Tolkien.", book.getDescription());
        assertEquals(1937, book.getPublishedYear());
        assertEquals(4.5, book.getAverageRating(), 0.01);
        assertEquals(1000, book.getRatingsCount());
        assertEquals(500, book.getLikedCount());
        assertEquals(310, book.getNumPages());
        assertEquals("Alice", book.getBorrower());
        assertEquals("Bob", book.getOwner());

        assertEquals(true,book.toString() != null);
    }

}
