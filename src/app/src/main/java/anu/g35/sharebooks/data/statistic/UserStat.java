package anu.g35.sharebooks.data.statistic;


import java.util.List;

import anu.g35.sharebooks.data.datasource.DailyStatDataSource;
import anu.g35.sharebooks.data.model.DailyStat;

/**
 * Class for getting statistics of users.
 * Registered Users: The number of new users registered each day.
 * Active Users: The number of users who performed at least one action each day.
 * Books Borrowed: The total number of books borrowed each day.
 * Books Shared: The total number of books shared each day.
 *
 * @author u7706346 Anbo Wu
 * @since 2024-04-26
 */
public class UserStat {

    private DailyStatDataSource dailyStatDataSource = DailyStatDataSource.getInstance();

    /**
     * Get the daily statistics of users.
     * The daily statistics are stored in a list of DailyStat objects.
     * @return the list of daily statistics
     */
    public List<DailyStat> dailyCount() {
        return dailyStatDataSource.getDailyStats();
    }
}
