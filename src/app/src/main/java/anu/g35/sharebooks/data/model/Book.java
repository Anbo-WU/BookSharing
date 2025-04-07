package anu.g35.sharebooks.data.model;

import java.io.Serializable;

/**
 * Book model class.
 * This class represents a book with various properties like isbn, title, authors, etc.
 *
 * @author u7722376 Di'ao Fu
 * @since 2024-04-18
 */
public class Book implements Serializable {
    private Long isbn; // ISBN of the book
    private String title; // Title of the book
    private String authors; // Authors of the book
    private String category; // Category of the book
    private String thumbnail; // Thumbnail URL of the book
    private String description; // Description of the book
    private int publishedYear; // Year the book was published
    private double averageRating; // Average rating of the book
    private int numPages; // Number of pages in the book
    private int ratingsCount; // Number of ratings the book has received
    private int likedCount; // Number of likes the book has received
    private String owner;
    private String borrower;

    public Book() {
    }

    /**
     * Copy a Book object
     * @param from Book object
     */
    public Book(Book from) {
        this.isbn = from.isbn;
        this.title = from.title;
        this.authors = from.authors;
        this.category = from.category;
        this.thumbnail = from.thumbnail;
        this.description = from.description;
        this.publishedYear = from.publishedYear;
        this.averageRating = from.averageRating;
        this.numPages = from.numPages;
        this.ratingsCount = from.ratingsCount;
        this.likedCount = from.likedCount;
        this.owner = from.owner;
        this.borrower = from.borrower;
    }

    // Getters and setters for all the fields

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public int getNumPages() {
        return numPages;
    }

    public void setNumPages(int numPages) {
        this.numPages = numPages;
    }

    public int getRatingsCount() {
        return ratingsCount;
    }

    public void setRatingsCount(int ratingsCount) {
        this.ratingsCount = ratingsCount;
    }

    public int getLikedCount() {
        return likedCount;
    }

    public void setLikedCount(int likedCount) {
        this.likedCount = likedCount;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn=" + isbn +
                ", title='" + title + '\'' +
                ", authors='" + authors + '\'' +
                ", category='" + category + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", description='" + description + '\'' +
                ", publishedYear=" + publishedYear +
                ", averageRating=" + averageRating +
                ", numPages=" + numPages +
                ", ratingsCount=" + ratingsCount +
                ", likedCount=" + likedCount +
                ", owner='" + owner + '\'' +
                ", borrower='" + borrower + '\'' +
                '}';

    }
}