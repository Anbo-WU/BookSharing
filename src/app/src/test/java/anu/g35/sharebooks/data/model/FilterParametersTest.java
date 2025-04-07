package anu.g35.sharebooks.data.model;

import static org.junit.Assert.assertEquals;
import org.junit.Test;


/**
 * Test of the FilterParameters class
 *
 * @author Huizhe_Ruan, u7723366
 * @since 2024-05-9
 */
public class FilterParametersTest {

    @Test
    public void testNewFilterParameters() {
        FilterParameters filterParameters = new FilterParameters();

        filterParameters.setAvailability(FilterParameters.Availability.BORROWED);
        filterParameters.setLikes(FilterParameters.Likes.LESS_THAN_50);

        assertEquals(FilterParameters.Availability.BORROWED, filterParameters.getAvailability());
        assertEquals(FilterParameters.Likes.LESS_THAN_50, filterParameters.getLikes());
        assertEquals(true,filterParameters.isFiltered());
    }

}
