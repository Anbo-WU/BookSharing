package anu.g35.sharebooks.data.datasource;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import anu.g35.sharebooks.data.model.Book;

/**
 * Test the AVL tree implementation of the BookAVLTree class
 *
 * @author Diao Fu
 * @since 2024-05-9
 */
public class BookAVLTreeTest {
    Long bookArray[] = {
            9780002005883L,
            9780002261982L,
            9780006280897L,
            9780006280934L,
            9780006380832L,
            9780006483892L,
            9780006483908L,
            9780006496434L,
            9780006498865L,
            9780006511489L,
            9780006512677L,
            9780006513087L,
            9780006514640L,
            9780006514855L,
            9780006550433L,
            9780006551393L,
            9780006551812L,
            9780006646006L,
            9780006754893L,
            9780007103676L,
            9780007105045L,
            9780007113804L,
            9780007116263L,
            9780007117536L,
            9780007119332L,
            9780007119356L,
            9780007120697L,
            9780007120819L,
            9780007120871L,
            9789042003408L,
            9788185300535L,
            9788179921623L,
            9788173031014L,
            9788171565641L,
            9784766113389L,
            9783895086908L,
            9783861878759L,
            9783791329284L,
            9783110172799L,
            9782940373154L,
            9781934169070L,
            9781933771175L,
            9781933771137L,
            9781933648279L,
            9781933633107L,
            9781933618081L,
            9781933615097L,
            9781933372198L
    };

    /**
     *  Test the insert, rightRotate, leftRotate, getBalance, getBalance methods of the AVL tree
     */
    @Test
    public void testInsert() {
        BookAVLTree tree = new BookAVLTree();
        for (Long isbn : bookArray) {
            Book book = new Book();
            book.setIsbn(isbn);
            tree.rootNode = tree.insert(tree.rootNode, book);
            // getBalance(node) should be between -1 and 1
            assertEquals(true, tree.getBalance(tree.rootNode) >= -1 && tree.getBalance(tree.rootNode) <= 1);
        }

    }

    /**
     *  Test the search method of the AVL tree
     */
    @Test
    public void testSearch() {
        BookAVLTree tree = new BookAVLTree();
        for (Long isbn : bookArray) {
            Book book = new Book();
            book.setIsbn(isbn);
            tree.rootNode = tree.insert(tree.rootNode, book);
        }

        for (Long isbn : bookArray) {
            // search should return the book with the given isbn
            assertEquals(isbn.longValue(), tree.search(tree.rootNode, isbn).bookData.getIsbn().longValue());
        }
    }

    /**
     *  Test the getInorder method of the AVL tree
     */
    @Test
    public void testGetInorder() {
        BookAVLTree tree = new BookAVLTree();
        for (Long isbn : bookArray) {
            Book book = new Book();
            book.setIsbn(isbn);
            tree.rootNode = tree.insert(tree.rootNode, book);
        }

        List<Book> result = new ArrayList<>();
        tree.getInorder(tree.rootNode, result);
        // getInorder should return the books in the order of their ISBNs
        assertEquals(bookArray.length, result.size());
        Arrays.sort(bookArray);
        for (int i = 0; i < bookArray.length; i++) {
            assertEquals(bookArray[i].longValue(), result.get(i).getIsbn().longValue());
        }
    }


}
