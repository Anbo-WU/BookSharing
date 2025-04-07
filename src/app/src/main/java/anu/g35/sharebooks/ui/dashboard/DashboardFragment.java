package anu.g35.sharebooks.ui.dashboard;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import anu.g35.sharebooks.data.model.DailyStat;
import anu.g35.sharebooks.databinding.FragmentDashboardBinding;


/**
 * Draw charts of the statistics of the books and users
 * Reference: https://weeklycoding.com/mpandroidchart-documentation/getting-started/
 * Reference: https://javadoc.jitpack.io/com/github/PhilJay/MPAndroidChart/v3.1.0/javadoc/
 *
 * @author u7706346 Anbo Wu
 * @since 2024-04-28
 */

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dashboardViewModel.getCategories().observe(getViewLifecycleOwner(), categories -> {
            categoryChart(categories);
        });

        dashboardViewModel.getPublishedYears().observe(getViewLifecycleOwner(), this::publishedYearChart);

        dashboardViewModel.getDailyStat().observe(getViewLifecycleOwner(), this::createLineChart);

        // Update the data
        dashboardViewModel.update();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Create a line chart with the given daily statistics data
     *
     * @param dailyStats the data to be displayed in the chart
     */
    public void createLineChart(List<DailyStat> dailyStats) {
        LineChart lineChart = binding.dailyStatChart;

        List<Entry> entriesRegisteredUsers = new ArrayList<>();
        List<Entry> entriesActiveUsers = new ArrayList<>();
        List<Entry> entriesBooksBorrowed = new ArrayList<>();
        List<Entry> entriesBooksShared = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        for (int i = 0; i < dailyStats.size(); i++) {
            DailyStat dailyStat = dailyStats.get(i);
            labels.add(dailyStat.getDate().substring(5));
            entriesRegisteredUsers.add(new Entry(i, dailyStat.getRegisteredUsers()));
            entriesActiveUsers.add(new Entry(i, dailyStat.getActiveUsers()));
            entriesBooksBorrowed.add(new Entry(i, dailyStat.getBooksBorrowed()));
            entriesBooksShared.add(new Entry(i, dailyStat.getBooksShared()));
        }

        LineDataSet dataSetRegisteredUsers = new LineDataSet(entriesRegisteredUsers, "Reg. Users");
        dataSetRegisteredUsers.setColor(Color.RED);
        dataSetRegisteredUsers.setLineWidth(2f);
        dataSetRegisteredUsers.setValueTextColor(Color.BLACK);
        dataSetRegisteredUsers.setDrawCircles(false);

        LineDataSet dataSetActiveUsers = new LineDataSet(entriesActiveUsers, "Act. Users");
        dataSetActiveUsers.setColor(Color.GREEN);
        dataSetActiveUsers.setLineWidth(2f);
        dataSetActiveUsers.setValueTextColor(Color.BLACK);
        dataSetActiveUsers.setDrawCircles(false);

        LineDataSet dataSetBooksBorrowed = new LineDataSet(entriesBooksBorrowed, "Bks. Brwd.");
        dataSetBooksBorrowed.setColor(Color.BLUE);
        dataSetBooksBorrowed.setLineWidth(2f);
        dataSetBooksBorrowed.setValueTextColor(Color.BLACK);
        dataSetBooksBorrowed.setDrawCircles(false);

        LineDataSet dataSetBooksShared = new LineDataSet(entriesBooksShared, "Bks. Shared");
        dataSetBooksShared.setColor(Color.MAGENTA);
        dataSetBooksShared.setLineWidth(2f);
        dataSetBooksShared.setValueTextColor(Color.BLACK);
        dataSetBooksShared.setDrawCircles(false);

        LineData lineData = new LineData();
        lineData.addDataSet(dataSetRegisteredUsers);
        lineData.addDataSet(dataSetActiveUsers);
        lineData.addDataSet(dataSetBooksBorrowed);
        lineData.addDataSet(dataSetBooksShared);

        lineChart.setData(lineData);

        Legend legend = lineChart.getLegend();
        legend.setEnabled(true);
        legend.setDrawInside(false);
        legend.setTextSize(14f);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);

        // Set the X axis properties
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(labels.size(), false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setLabelRotationAngle(90);
        xAxis.setTextSize(12f);
        xAxis.setDrawGridLines(false);

        lineChart.getDescription().setEnabled(false);

        lineChart.invalidate(); // refreshes the chart
    }


    /**
     * Create a pie chart with the given published years data
     *
     * @param years the data to be displayed in the chart
     */
    private void publishedYearChart(Map<String, Integer> years) {

        PieChart pieChart = binding.publishedYearChart;
        // Set the chart properties
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(15f);
        pieChart.setDrawHoleEnabled(false);

        // Create the data set
        List<PieEntry> entries = new ArrayList<>();
        for (Map.Entry<String, Integer> year : years.entrySet()) {
            entries.add(new PieEntry(year.getValue(), year.getKey()));
        }
        PieDataSet dataSet = new PieDataSet(entries, "legend label");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        // Set the data with the data set
        PieData data = new PieData(dataSet);
        // Set value formatter to percent
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(16f);

        // Set the data to the chart
        pieChart.setData(data);
        pieChart.invalidate();
    }


    /**
     * Create a horizontal bar chart with the given categories data
     *
     * @param categories the data to be displayed in the chart
     */
    private void categoryChart(Map<String, Integer> categories) {
        HorizontalBarChart barChart = binding.categoryChart;

        // Set the chart properties
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);
        barChart.setDrawGridBackground(false);
        barChart.setDrawBorders(false);

        // Create the data set
        List<BarEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        int index = 0;
        for (Map.Entry<String, Integer> category : categories.entrySet()) {
            entries.add(new BarEntry(index, category.getValue()));
            labels.add(category.getKey());
            index++;
        }
        BarDataSet dataSet = new BarDataSet(entries, "Categories");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        // Set the X axis properties
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(labels.size(), false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setTextSize(14f); // Set the label text size
        xAxis.setDrawGridLines(false);

        // Set the data with the data set
        BarData data = new BarData(dataSet);
        data.setValueTextSize(14f);
        data.setBarWidth(0.9f);

        // Set the data to the chart
        barChart.setData(data);
        barChart.invalidate();
    }
}