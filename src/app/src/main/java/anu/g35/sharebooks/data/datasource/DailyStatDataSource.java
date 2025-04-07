package anu.g35.sharebooks.data.datasource;

import android.content.res.Resources;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import anu.g35.sharebooks.R;
import anu.g35.sharebooks.ShareBooks;
import anu.g35.sharebooks.data.model.DailyStat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


/**
 * This class is a singleton.
 * This class is responsible for loading daily statistics data from a JSON file
 *
 * @author u7706346 Anbo Wu
 * @since 2024-04-26
 */
public class DailyStatDataSource {
    private static DailyStatDataSource instance = null;

    private final List<DailyStat> dailyStats = new ArrayList<>();
    
    private DailyStatDataSource() {
        try {
            loadDailyStatFromJSON();
        } catch (Exception e) {
            Log.e("DailyStatDataSource", "Loading daily stat failed", e);
        }
    }

    /**
     * Returns the instance of the DailyStatDataSource.
     * @return  the instance of the DailyStatDataSource
     */
    public static DailyStatDataSource getInstance() {
        if (instance == null) {
            instance = new DailyStatDataSource();
        }
        return instance;
    }

    /**
     * Get the list of daily statistics, new list to avoid modification of the original list.
     * @return the list of daily statistics
     */
    public List<DailyStat> getDailyStats() {
        List<DailyStat> newDailyStats = new ArrayList<>();
        for (DailyStat dailyStat : dailyStats) {
            newDailyStats.add(new DailyStat(dailyStat));
        }
        return newDailyStats;
    }

    /**
     * Load daily statistics from a JSON file.
     * @throws Exception if loading daily statistics failed
     */
    private void loadDailyStatFromJSON()  throws Exception {
        JSONObject jsonObject = getJsonObject();
        JSONArray dailyArray = jsonObject.getJSONArray("daily_stats");

        for (int i = 0; i < dailyArray.length(); i++) {
            JSONObject dailyStat = dailyArray.getJSONObject(i);
            DailyStat stat = new DailyStat(
                    dailyStat.getString("date"),
                    dailyStat.getInt("registered_users"),
                    dailyStat.getInt("active_users"),
                    dailyStat.getInt("books_borrowed"),
                    dailyStat.getInt("books_shared")
            );
            dailyStats.add(stat);
        }
    }

    /**
     * Get the JSON object from the JSON file.
     * @return the JSON object
     * @throws IOException if an I/O error occurs
     * @throws JSONException if the JSON object cannot be created
     */
    @NonNull
    private static JSONObject getJsonObject() throws IOException, JSONException {
        Resources resources = ShareBooks.getContext().getResources();
        InputStream inputStream = resources.openRawResource(R.raw.daily_stat);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        String json = builder.toString();
        JSONObject jsonObject = new JSONObject(json);
        return jsonObject;
    }

}
