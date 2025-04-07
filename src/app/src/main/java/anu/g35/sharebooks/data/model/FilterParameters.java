package anu.g35.sharebooks.data.model;

/**
 * FilterParameters class provides the parameters for filtering books based on availability and likes.
 * It is used to filter books based on whether they are borrowed or available, and whether they have more or less than 50 likes.
 *
 * @author Huizhe_Ruan, u7723366
 * @since 2024-05-7
 */
public class FilterParameters {
    public enum Availability {
        BORROWED, AVAILABLE, ALL
    }

    public enum Likes {
        LESS_THAN_50, GREATER_THAN_50, ALL
    }

    private Availability availability;
    private Likes likes;

    public FilterParameters() {
        availability = Availability.ALL;
        likes = Likes.ALL;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    public void setLikes(Likes likes) {
        this.likes = likes;
    }

    public Availability getAvailability() {
        return availability;
    }

    public Likes getLikes() {
        return likes;
    }

    public boolean isFiltered() {
        return availability != Availability.ALL || likes != Likes.ALL;
    }
}