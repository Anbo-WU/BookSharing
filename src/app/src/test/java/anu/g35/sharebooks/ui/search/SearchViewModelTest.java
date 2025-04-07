package anu.g35.sharebooks.ui.search;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import anu.g35.sharebooks.data.model.FilterParameters;
import anu.g35.sharebooks.data.session.UserSession;


/**
 * This class is used to test the SearchViewModel class.
 *
 * @author Huizhe_Ruan, u7723366
 * @since 2024-05-9
 */
@RunWith(RobolectricTestRunner.class)
public class SearchViewModelTest {
    SearchViewModel searchViewModel;
    UserSession userSession;

    @Before
    public void setUp() {
        searchViewModel = new SearchViewModel();
        userSession = UserSession.getInstance();
    }

    @Test
    public void testSearchFunction() {
        if (!userSession.isLogin()) {
            userSession.login("test@t.t", "123456");
        }

        searchViewModel.search("man");
        assertEquals(true, searchViewModel.getMessage() != null);
        assertEquals(true, searchViewModel.getSearchResults() != null);
    }

    @Test
    public void testSortAndFilter() {
        searchViewModel.search("");
        searchViewModel.setComparator(Sorter.titleAsc());
        assertEquals(true, searchViewModel.getComparator() != null);

        FilterParameters parameters = new FilterParameters();
        parameters.setAvailability(FilterParameters.Availability.AVAILABLE);
        searchViewModel.setFilterParameters(parameters);
        assertEquals(true, searchViewModel.getFilterParameters() != null);
        searchViewModel.sortAndFilter();
        assertEquals(true, searchViewModel.getSearchResults() != null);
    }

}
