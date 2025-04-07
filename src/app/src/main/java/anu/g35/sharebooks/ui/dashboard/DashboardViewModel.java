package anu.g35.sharebooks.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Map;

import anu.g35.sharebooks.data.model.DailyStat;
import anu.g35.sharebooks.data.statistic.BookStat;
import anu.g35.sharebooks.data.statistic.UserStat;


/**
 * ViewModel for the DashboardFragment
 * @author u7706346 Anbo Wu
 * @since 2024-04-28
 */
public class DashboardViewModel extends ViewModel {

    private final BookStat bookStat;
    private final UserStat userstat;
    private final MutableLiveData<Map<String, Integer>> publishedYears = new MutableLiveData<>();
    private final MutableLiveData<Map<String, Integer>> categories = new MutableLiveData<>();

    private final MutableLiveData<List<DailyStat>> dailyStat = new MutableLiveData<>();

    public DashboardViewModel() {

        bookStat = new BookStat();
        userstat = new UserStat();
    }

    public LiveData<Map<String, Integer>> getPublishedYears() {
        return publishedYears;
    }

    public LiveData<Map<String, Integer>> getCategories() {
        return categories;
    }

    public LiveData<List<DailyStat>> getDailyStat() {
        return dailyStat;
    }

    /**
     * Update the data
     * the data is the statistics of the books and users
     */
    public void update() {
        publishedYears.setValue(bookStat.publishedYearCount());
        categories.setValue(bookStat.categoryCount());
        dailyStat.setValue(userstat.dailyCount());
    }
}