package anu.g35.sharebooks.ui.search;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import anu.g35.sharebooks.data.model.Book;

import anu.g35.sharebooks.data.model.FilterParameters;

/**
 * The BookFilter class provides static methods to filter lists of books based on certain criteria.
 * This class is designed to help implement filtering functionality across various parts of the application where book lists are used.
 *
 * @author Huizhe_Ruan, u7723366
 * @since 2024-05-07
 */
public class BookFilter {

    /**
     * Filters the list of books based on their availability.
     *
     * @param books      List of books to filter.
     * @param parameters Filter parameters to apply.
     * @return A list of books that meets the specified availability filter.
     */
    public static List<Book> filterBooks(List<Book> books, FilterParameters parameters) {
        if(parameters == null){
            return books;
        }

        FilterParameters.Availability availabilityPara = parameters.getAvailability();
        FilterParameters.Likes likesPara = parameters.getLikes();

         List<Book> filteredBooks = null;

        if (likesPara == FilterParameters.Likes.LESS_THAN_50) {
            filteredBooks = books.stream()
                    .filter(book -> book.getLikedCount() < 50)
                    .collect(Collectors.toList());
        } else if(likesPara == FilterParameters.Likes.GREATER_THAN_50){
            filteredBooks = books.stream()
                    .filter(book -> book.getLikedCount() >= 50)
                    .collect(Collectors.toList());

        } else {
            filteredBooks = books;
        }

        if (availabilityPara == FilterParameters.Availability.AVAILABLE) {
            filteredBooks = filteredBooks.stream()
                    .filter(book -> book.getBorrower().isEmpty())
                    .collect(Collectors.toList());
        } else if(availabilityPara == FilterParameters.Availability.BORROWED){
            filteredBooks = filteredBooks.stream()
                    .filter(book -> !book.getBorrower().isEmpty())
                    .collect(Collectors.toList());
        }


        return filteredBooks;
    }
}
