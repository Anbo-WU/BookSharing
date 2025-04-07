package anu.g35.sharebooks.data.statistic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import anu.g35.sharebooks.data.datasource.BookDataSource;

/** Class for getting statistics of books.
 * It provides methods to get the count of books in each category and
 * the count of books published in each year.
 * The data source for the statistics is the BookDataSource.
 * @author u7706346 Anbo Wu
 * @since 2024-04-26
 */
public class BookStat {
    private BookDataSource bookDataSource;

    /**
     * Constructor for the BookStat class.
     * It initializes the bookDataSource.
     */
    public BookStat() {
        bookDataSource = BookDataSource.getInstance();
    }

    /**
     * Get all categories of books.
     * The categories are stored in a map where the key is the category name
     * and the value is the number of books in that category.
     * @return the map of categories
     */
    public Map<String, Integer> categoryCount() {
        Map<String, HashSet<Long>> categories= bookDataSource.getCategories();
        Map<String, Integer> result = new HashMap<>();

        for (String category : categories.keySet()) {
            result.put(category, categories.get(category).size());
        }
        return result;
    }


    /**
     * Get all published years of books.
     * The published years are stored in a map where the key is the year
     * and the value is the number of books published in that year.
     * @return the map of published years
     */
    public Map<String, Integer> publishedYearCount() {
        Map<Integer, HashSet<Long>> years = bookDataSource.getPublishedYears();
        Map<String, Integer> result = new HashMap<>();

        for (Integer year : years.keySet()) {
            result.put(String.valueOf(year), years.get(year).size());
        }
        return result;
    }
}
