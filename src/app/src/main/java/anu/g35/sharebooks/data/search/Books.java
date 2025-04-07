package anu.g35.sharebooks.data.search;

import android.content.Context;
import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import anu.g35.sharebooks.R;
import anu.g35.sharebooks.ShareBooks;
import anu.g35.sharebooks.data.datasource.BookDataSource;
import anu.g35.sharebooks.data.model.Book;
import anu.g35.sharebooks.data.simulate.SimulateSubject;
import anu.g35.sharebooks.exceptions.IllegalStringException;

/**
 * The Books class is used to store all the books in the application.
 * It loads the books from an XML file and stores them in an AVL tree.
 * It also stores the ISBNs of books in various maps to allow for efficient searching.
 * The class provides methods to search for books by ISBN, category, published year, author, and title.
 *
 * @author u7722376 Di'ao Fu
 * @since 2024-04-18
 */
public class Books {

    private static Books instance = null;
    private static BookDataSource bookDataSource;

    /**
     * Constructor for the Books class.
     * It loads the books from an XML file and stores them in the AVL tree and the maps.
     */
    private Books() {
        bookDataSource = BookDataSource.getInstance();
    }

    /**
     * This is a part of the Singleton design pattern which ensures
     * that only one instance of this class can be created.
     *
     * @return instance
     */
    public static Books getInstance() {

        if (instance == null) {
            instance = new Books();
        }
        return instance;
    }

    /**
     * Get all the categories in the application.
     *
     * @return a set of all the categories
     */
    public Set<String> getCategories() {
        return bookDataSource.getCategories().keySet();
    }

    /**
     * Get all the published years in the application.
     *
     * @return a set of all the published years
     */
    public Set<Integer> getPublishedYears() {
        return bookDataSource.getPublishedYears().keySet();
    }

    /**
     * Search books by query string.
     * If the query is empty, return all books.
     *
     * @param query the search query
     * @return the Book list with the given query
     * @throws IllegalStringException if the query is illegal
     */
    public List<Book> searchByQueryString(String query) throws IllegalStringException{
        if (query.isEmpty()) {
            return bookDataSource.getAllBooks();
        } else {
            // Tokenize the query and build the expression
            Tokenizer tokenizer = new Tokenizer(query);
            Parser parser = new Parser(tokenizer);
            Expression expression = parser.buildExp();
            // Evaluate the expression and get the ISBNs
            HashSet<Long> isbnSet = expression.evaluate();
            // Search for books based on the ISBNs
            return searchByISBNSet(isbnSet);
        }
    }

    /**
     * Search for a books by ISBN set.
     *
     * @param isbnSet the ISBN set of the books
     * @return the Book list with the given ISBN set
     */
    public List<Book> searchByISBNSet(HashSet<Long> isbnSet) {
        List<Book> result = new ArrayList<>();
        for (long isbn : isbnSet) {
            result.add(bookDataSource.getBookByISBN(isbn));
        }
        return result;
    }


    /**
     * Search for ISBN set of books by category.
     *
     * @param category the category of the books
     * @return the ISBN set of the books with the given category
     */
    public HashSet<Long> getISBNSetByCategory(String category) {

        HashSet<Long> isbnSet = bookDataSource.getCategories().get(category);
        return isbnSet != null ? isbnSet : new HashSet<>();
    }

    /**
     * Search for ISBN set of books by published year.
     *
     * @param year the published year of the books
     * @return the ISBN set of the books with the given published year
     */
    public HashSet<Long> getISBNSetByPublishedYear(int year) {

        HashSet<Long> isbnSet = bookDataSource.getPublishedYears().get(year);
        return isbnSet != null ? isbnSet : new HashSet<>();
    }

    /**
     * Search for ISBN set of books by owner.
     *
     * @param owner the owner of the books
     * @return the ISBN set of the books with the given owner
     */
    public HashSet<Long> getISBNSetByOwner(String owner) {
        HashSet<Long> isbnSet = bookDataSource.getOwners().get(owner);
        return isbnSet != null ? isbnSet : new HashSet<>();
    }

    /**
     * Search for ISBN set of books by borrower.
     *
     * @param borrower the borrower of the books
     * @return the ISBN set of the books with the given borrower
     */
    public HashSet<Long> getISBNSetByBorrower(String borrower) {
        HashSet<Long> isbnSet = bookDataSource.getBorrower().get(borrower);
        return isbnSet != null ? isbnSet : new HashSet<>();
    }

    /**
     * Search for ISBN set of books by author.
     *
     * @param author the author of the books
     * @return the ISBN set of the books with the given author
     */
    public HashSet<Long> getISBNSetByAuthors(String author) {
        HashSet<Long> isbnSet = new HashSet<Long>();
        String[] words = author.toLowerCase().split(" ");
        for (String word : words) {
            if (word.length() < 3) {
                continue;
            }
            HashSet<Long> set = bookDataSource.getAuthorWords().get(word);
            if (set != null) {
                isbnSet.addAll(set);
            }
        }
        return isbnSet;
    }

    /**
     * Search for ISBN set of books by title.
     *
     * @param title the title of the books
     * @return the ISBN set of the books with the given title
     */
    public HashSet<Long> getISBNSetByTitle(String title) {
        HashSet<Long> isbnSet = new HashSet<Long>();
        String[] words = title.toLowerCase().split(" ");
        for (String word : words) {
            if (word.length() < 3) {
                continue;
            }
            HashSet<Long> set = bookDataSource.getTitleWords().get(word);
            if (set != null) {
                isbnSet.addAll(set);
            }
        }
        return isbnSet;
    }

}
