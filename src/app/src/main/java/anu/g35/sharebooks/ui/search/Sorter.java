package anu.g35.sharebooks.ui.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import anu.g35.sharebooks.data.model.Book;

/**
 * The Sorter class provides functionality to sort lists of books based on various attributes.
 * It allows the dynamic composition of comparators to sort books in ascending or descending order
 * by title, authors, and published year. The class manages a current comparator which can be
 * set, cleared, or combined with other comparators for complex sorting operations.
 *
 * @author Huizhe_Ruan, u7723366
 * @since 2024-05-07
 */
public class Sorter {
    private Comparator<Book> currentComparator;

    // Set the comparator
    public void setComparator(Comparator<Book> comparator) {
        if (comparator == null) {
            clearComparator();
        } else {
            if (currentComparator == null) {
                currentComparator = comparator;
            } else {
                currentComparator = currentComparator.thenComparing(comparator);
            }
        }
    }

    // Clear the comparator
    public void clearComparator() {
        currentComparator = null;
    }

    // Perform sorting
    public void sortBooks(List<Book> books) {
        if (currentComparator != null) {
            Collections.sort(books, currentComparator);
        }
    }

    // Define various sorting methods
    public static Comparator<Book> titleAsc() {
        return Comparator.comparing(Book::getTitle, String.CASE_INSENSITIVE_ORDER);
    }

    public static Comparator<Book> titleDesc() {
        return Comparator.comparing(Book::getTitle, String.CASE_INSENSITIVE_ORDER).reversed();
    }

    public static Comparator<Book> authorsAsc() {
        return Comparator.comparing(Book::getAuthors, String.CASE_INSENSITIVE_ORDER);
    }

    public static Comparator<Book> authorsDesc() {
        return Comparator.comparing(Book::getAuthors, String.CASE_INSENSITIVE_ORDER).reversed();
    }

    public static Comparator<Book> publishedYearAsc() {
        return Comparator.comparingInt(Book::getPublishedYear);
    }

    public static Comparator<Book> publishedYearDesc() {
        return Comparator.comparingInt(Book::getPublishedYear).reversed();
    }
}