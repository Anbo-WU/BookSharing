package anu.g35.sharebooks.ui.dashboard;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;


/**
 * This class is used to test the DashboardViewModel class.
 *
 * @author u7706346 Anbo Wu
 * @since 2024-05-09
 */
@RunWith(RobolectricTestRunner.class)
public class DashboardViewModelTest {
    DashboardViewModel dashboardViewModel;

    @Before
    public void setUp() {
        dashboardViewModel = new DashboardViewModel();
    }


    @Test
    public void testUpdateAndGetFunction() {

        dashboardViewModel.update();


        assertEquals(true, dashboardViewModel.getPublishedYears() != null);
        assertEquals(true, dashboardViewModel.getCategories() != null);
        assertEquals(true, dashboardViewModel.getDailyStat() != null);
    }
}
