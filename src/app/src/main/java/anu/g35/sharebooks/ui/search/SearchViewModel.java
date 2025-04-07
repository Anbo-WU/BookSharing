package anu.g35.sharebooks.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import anu.g35.sharebooks.data.model.Book;
import anu.g35.sharebooks.data.model.FilterParameters;
import anu.g35.sharebooks.data.search.Books;
import anu.g35.sharebooks.exceptions.IllegalStringException;

/**
 * This ViewModel is responsible for handling the search logic
 * It communicates with the Books class to search for books
 *
 * @author Junfeng_Gao, u7615533
 * @since 2024-04-23
 */
public class SearchViewModel extends ViewModel {
    private final MutableLiveData<List<Book>> searchResults;
    private final MutableLiveData<String> message = new MutableLiveData<>();
    private final MutableLiveData<FilterParameters> filterParameters = new MutableLiveData<>();
    private final MutableLiveData<Comparator<Book>> comparator = new MutableLiveData<>();
    private final Books books;
    List<Book> originalQueryResults;

    /**
     * Constructor for the SearchViewModel
     */
    public SearchViewModel() {
        searchResults = new MutableLiveData<>();
        books = Books.getInstance();
    }

    /**
     * Getter for the searchResults LiveData
     * @return The searchResults LiveData
     */
    public LiveData<List<Book>> getSearchResults() {
        return searchResults;
    }

    /** Getter for the message LiveData
     * @return The message LiveData
     */
    public LiveData<String> getMessage() {
        return message;
    }

    /**
     * Getter for the filterParameters LiveData
     * @return The filterParameters LiveData
     */
    public LiveData<FilterParameters> getFilterParameters() {
        return filterParameters;
    }

    /**
     * Getter for the comparator LiveData
     * @return The comparator LiveData
     */
    public LiveData<Comparator<Book>> getComparator() {
        return comparator;
    }

    /**
     * Set the comparator for sorting the books
     * @param comparator The comparator to set
     */
    public void setComparator(Comparator<Book> comparator) {
        this.comparator.setValue(comparator);
    }

    /**
     * Set the filter parameters
     * @param parameters The filter parameters
     */
    public void setFilterParameters(FilterParameters parameters) {
        filterParameters.setValue(parameters);
    }


    /**
     * Search for books based on the query
     * If the query is empty, return all books
     *
     * @param query The search query
     */
    public void search(String query) {

        try {
            // Search for books based on the query
            List<Book> queryResults = books.searchByQueryString(query);
            originalQueryResults = new ArrayList<>(queryResults);

            // Update the searchResults LiveData
            searchResults.setValue(queryResults);
            message.setValue("Return " + queryResults.size() + " records.");
        } catch (IllegalStringException e) {
            // Handle the exception
            message.setValue("Error:" + e.getMessage());
            return;
        }

        // Reset the filter parameters and comparator
        comparator.setValue(null);
        filterParameters.setValue(new FilterParameters());
    }

    /**
     * Sort and filter the books based on the current comparator and filter parameters
     */
    public void sortAndFilter() {
        if (originalQueryResults == null) {
            return;
        }
        List<Book> booksSort = new ArrayList<>(originalQueryResults);
        Sorter sorter = new Sorter();
        sorter.setComparator(comparator.getValue());
        sorter.sortBooks(booksSort);
        List<Book> filteredBooks = BookFilter.filterBooks(booksSort, filterParameters.getValue());
        searchResults.setValue(filteredBooks);
    }



}