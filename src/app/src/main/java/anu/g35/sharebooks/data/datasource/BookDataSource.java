package anu.g35.sharebooks.data.datasource;

import android.content.Context;
import android.content.res.AssetManager;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import anu.g35.sharebooks.ShareBooks;
import anu.g35.sharebooks.data.model.Book;

/**
 * The Books class is used to store all the books in the application.
 * It loads the books from an XML file and stores them in an AVL tree.
 * It also stores the ISBNs of books in various maps to allow for efficient searching.
 *
 * @author u7722376 Di'ao Fu
 * @since 2024-04-18
 */
public class BookDataSource {

    // avlTree is used to store all the books in an AVL tree.
    // The key is the ISBN of the book, and the value is the Book object.
    // This tree is used to search for books by ISBN.
    private final BookAVLTree avlTree = new BookAVLTree();

    // categories is used to store each category and its corresponding set of ISBNs.
    // The key is the category name, and the value is a HashSet
    // that stores all the ISBNs of the books that belong to that category.
    private final HashMap<String, HashSet<Long>> categories = new HashMap<>();

    // publishedYears is used to store each year and its corresponding set of ISBNs.
    // The key is the year, and the value is a HashSet that stores all the ISBNs
    // of the books that were published in that year.
    private final HashMap<Integer, HashSet<Long>> publishedYears = new HashMap<>();

    // authorWords is used to store each author word and its corresponding set of ISBNs.
    // The key is the author word, and the value is a HashSet that stores all the ISBNs
    // of the books that contain that word in the authors field.
    private final HashMap<String, HashSet<Long>> authorWords = new HashMap<>();

    // titleWords is used to store each title word and its corresponding set of ISBNs.
    // The key is the title word, and the value is a HashSet that stores all the ISBNs
    // of the books that contain that word in the title field.
    private final HashMap<String, HashSet<Long>> titleWords = new HashMap<>();

    // owners is used to store each owner and its corresponding set of ISBNs.
    // The key is the owner name, and the value is a HashSet that stores all the ISBNs
    // of the books that are owned by that owner.
    private final HashMap<String, HashSet<Long>> owners = new HashMap<>();

    // borrowers is used to store each borrower and its corresponding set of ISBNs.
    // The key is the borrower name, and the value is a HashSet that stores all the ISBNs
    // of the books that are borrowed by that borrower.
    private final HashMap<String, HashSet<Long>> borrowers = new HashMap<>();

    private static BookDataSource instance = null;

    /**
     * Constructor for the Books class.
     * It loads the books from an XML file and stores them in the AVL tree and the maps.
     */
    private BookDataSource() {
        loadBooksFromXML();
    }

    /**
     * This is a part of the Singleton design pattern which ensures
     * that only one instance of this class can be created.
     *
     * @return instance
     */
    public static BookDataSource getInstance() {

        if (instance == null) {
            instance = new BookDataSource();
        }
        return instance;
    }


    /**
     * Get all the books in the application. Copy from storage
     *
     * @return a list of all the books
     */
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        avlTree.getInorder(avlTree.rootNode, books);

        List<Book> booksCopy = new ArrayList<>();
        for (Book book : books) {
            booksCopy.add(new Book(book));
        }
        return booksCopy;
    }

    /**
     * Search for a book by ISBN. Copy from storage
     *
     * @param isbn the ISBN of the book
     * @return the Book object with the given ISBN, or null if the book is not found
     */
    public Book getBookByISBN(long isbn) {
        BookAVLTree.TreeNode node = avlTree.search(avlTree.rootNode, isbn);
        if (node == null) {
            return null;
        }
        return new Book(node.bookData);
    }

    /**
     * Get the handle of the category map.
     *
     * @return the category map
     */
    public Map<String, HashSet<Long>> getCategories() {
        return Collections.unmodifiableMap(categories);
    }

    /**
     * Get the handle of the published years map.
     *
     * @return the published years map
     */
    public Map<Integer, HashSet<Long>> getPublishedYears() {
        return Collections.unmodifiableMap(publishedYears);
    }

    /**
     * Get the handle of the owners map.
     *
     * @return the owners map
     */
    public Map<String, HashSet<Long>> getOwners() {
        return Collections.unmodifiableMap(owners);
    }

    /**
     * Get the handle of the borrowers map.
     *
     * @return the borrowers map
     */
    public Map<String, HashSet<Long>> getBorrower() {
        return Collections.unmodifiableMap(borrowers);
    }

    /**
     * Get the handle of the authorWords map.
     *
     * @return the authorWords map
     */
    public Map<String, HashSet<Long>> getAuthorWords() {
        return Collections.unmodifiableMap(authorWords);
    }

    /**
     * Get the handle of the titleWords map.
     *
     * @return the titleWords map
     */
    public Map<String, HashSet<Long>> getTitleWords() {
        return Collections.unmodifiableMap(titleWords);
    }

    /**
     * Set the liked count of a book.
     *
     * @param isbn the ISBN of the book
     * @param count the new liked count
     * @return true if the liked count is set successfully, false otherwise
     */
    public boolean setLikeCount(long isbn, int count) {
        BookAVLTree.TreeNode node = avlTree.search(avlTree.rootNode, isbn);
        if (node == null) {
            return false;
        }
        Book book = node.bookData;
        if (book != null) {
            book.setLikedCount(count);
            return true;
        }
        return false;
    }

    /**
     * borrow a book.
     * Borrow a book by adding the book's ISBN to the borrower's set of ISBNs.
     * @param isbn the ISBN of the book
     * @param borrowerId the id of the borrower
     * @return true if the book is borrowed successfully, false otherwise
     */
    public boolean borrowBook(long isbn, String borrowerId) {
        BookAVLTree.TreeNode node = avlTree.search(avlTree.rootNode, isbn);
        if (node == null) {
            return false;
        }
        Book book = node.bookData;

        if (book != null && book.getBorrower().isEmpty()) {
            // Add the book's ISBN to the borrowers map
            HashSet<Long> isbnSet = borrowers.get(borrowerId);
            if (isbnSet != null) {
                isbnSet.add(book.getIsbn());
            } else {
                isbnSet = new HashSet<>();
                isbnSet.add(book.getIsbn());
                borrowers.put(borrowerId, isbnSet);
            }
            book.setBorrower(borrowerId);
            return true;
        }
        return false;
    }

    /**
     * Return a book.
     * Return a book by removing the book's ISBN from the borrower's set of ISBNs.
     *
     * @param isbn the ISBN of the book
     * @param borrowerId the id of the borrower
     * @return true if the book is returned successfully, false otherwise
     */
    public boolean returnBook(long isbn, String borrowerId) {
        BookAVLTree.TreeNode node = avlTree.search(avlTree.rootNode, isbn);
        if (node == null) {
            return false;
        }
        Book book = node.bookData;

        if (book != null && book.getBorrower().equals(borrowerId)) {
            book.setBorrower("");
            borrowers.get(borrowerId).remove(isbn);
            return true;
        }
        return false;
    }

    /**
     * Load the books from an XML file and store them in the AVL tree and the maps.
     */
    public void loadBooksFromXML() {

        Context context = ShareBooks.getContext();
        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("data/books.xml");
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(inputStream, "UTF-8");

            Book book = null;
            int eventType = xpp.getEventType();

            // Loop through the XML document
            while (eventType != XmlPullParser.END_DOCUMENT) {
                // Start of a new book, create a new Book object
                if (eventType == XmlPullParser.START_TAG) {
                    // Inside a book, set the properties of the Book object
                    if (xpp.getName().equals("book")) {
                        book = new Book();
                    } else if (book != null) {
                        switch (xpp.getName()) {
                            case "isbn":
                                book.setIsbn(Long.valueOf(xpp.nextText()));
                                break;
                            case "title":
                                book.setTitle(xpp.nextText());
                                break;
                            case "authors":
                                book.setAuthors(xpp.nextText());
                                break;
                            case "category":
                                book.setCategory(xpp.nextText());
                                break;
                            case "thumbnail":
                                book.setThumbnail(xpp.nextText());
                                break;
                            case "description":
                                book.setDescription(xpp.nextText());
                                break;
                            case "published_year":
                                book.setPublishedYear(Integer.parseInt(xpp.nextText()));
                                break;
                            case "average_rating":
                                book.setAverageRating(Double.parseDouble(xpp.nextText()));
                                break;
                            case "num_pages":
                                book.setNumPages(Integer.parseInt(xpp.nextText()));
                                break;
                            case "ratings_count":
                                book.setRatingsCount(Integer.parseInt(xpp.nextText()));
                                break;
                            case "liked_count":
                                book.setLikedCount(Integer.parseInt(xpp.nextText()));
                                break;
                            case "owner":
                                book.setOwner(xpp.nextText());
                                break;
                            case "borrower":
                                book.setBorrower(xpp.nextText());
                                break;
                            default:
                        }
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    // End of a book,
                    if (xpp.getName().equals("book") && book != null) {

                        // add the book object to the AVL tree
                        avlTree.rootNode = avlTree.insert(avlTree.rootNode, book);

                        // Add the book's ISBN to the categories map
                        String category = book.getCategory();
                        HashSet<Long> isbnSet = categories.get(category);
                        if (isbnSet != null) {
                            isbnSet.add(book.getIsbn());
                        } else {
                            isbnSet = new HashSet<Long>();
                            isbnSet.add(book.getIsbn());
                            categories.put(book.getCategory(), isbnSet);
                        }

                        // Add the book's ISBN to the publishedYears map
                        int year = book.getPublishedYear();
                        isbnSet = publishedYears.get(year);
                        if (isbnSet != null) {
                            isbnSet.add(book.getIsbn());
                        } else {
                            isbnSet = new HashSet<Long>();
                            isbnSet.add(book.getIsbn());
                            publishedYears.put(year, isbnSet);
                        }

                        // Add the book's ISBN to the owners map
                        String owner = book.getOwner();
                        isbnSet = owners.get(owner);
                        if (isbnSet != null) {
                            isbnSet.add(book.getIsbn());
                        } else {
                            isbnSet = new HashSet<Long>();
                            isbnSet.add(book.getIsbn());
                            owners.put(owner, isbnSet);
                        }

                        // Add the book's ISBN to the borrowers map
                        String borrower = book.getBorrower();
                        isbnSet = borrowers.get(borrower);
                        if (isbnSet != null) {
                            isbnSet.add(book.getIsbn());
                        } else {
                            isbnSet = new HashSet<Long>();
                            isbnSet.add(book.getIsbn());
                            borrowers.put(borrower, isbnSet);
                        }

                        // Add the book's ISBN to the authorWords maps
                        String[] words = book.getAuthors().toLowerCase().split(" ");
                        for (String word : words) {
                            if (word.length() < 3) {
                                continue;
                            }
                            isbnSet = this.authorWords.get(word);
                            if (isbnSet != null) {
                                isbnSet.add(book.getIsbn());
                            } else {
                                isbnSet = new HashSet<Long>();
                                isbnSet.add(book.getIsbn());
                                this.authorWords.put(word, isbnSet);
                            }
                        }

                        // Add the book's ISBN to the titleWords map
                        words = book.getTitle().toLowerCase().split(" ");
                        for (String word : words) {
                            if (word.length() < 3) {
                                continue;
                            }
                            isbnSet = this.titleWords.get(word);
                            if (isbnSet != null) {
                                isbnSet.add(book.getIsbn());
                            } else {
                                isbnSet = new HashSet<Long>();
                                isbnSet.add(book.getIsbn());
                                this.titleWords.put(word, isbnSet);
                            }
                        }

                        book = null;
                    }
                }
                eventType = xpp.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }
}
