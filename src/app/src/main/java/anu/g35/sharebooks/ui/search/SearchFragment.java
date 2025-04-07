package anu.g35.sharebooks.ui.search;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import anu.g35.sharebooks.R;
import anu.g35.sharebooks.data.model.Book;
import anu.g35.sharebooks.data.model.FilterParameters;

/**
 * Fragment for the search screen
 * This fragment allows the user to search for books
 * The user can enter a search query and click the search button
 * The user can also click the advanced search button to build a complex query
 * The search results are displayed in a RecyclerView
 *
 * @author Junfeng_Gao, u7615533
 * @since 2024-04-23
 */
public class SearchFragment extends Fragment {

    private SearchViewModel mViewModel;
    private SearchView queryInput;

    // Adapter for the RecyclerView
    BooksAdapter bookAdapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        queryInput = view.findViewById(R.id.query_input);

        // Set the spinner for sorting
        Spinner spinnerSort = view.findViewById(R.id.spinner_sort);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.spinner_data, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSort.setAdapter(spinnerAdapter);
        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Comparator<Book> comparator = null;
                switch (position) {
                    case 1:
                        comparator = Sorter.titleAsc();
                        break;
                    case 2:
                        comparator = Sorter.titleDesc();
                        break;
                    case 3:
                        comparator = Sorter.authorsAsc();
                        break;
                    case 4:
                        comparator = Sorter.authorsDesc();
                        break;
                    case 5:
                        comparator = Sorter.publishedYearAsc();
                        break;
                    case 6:
                        comparator = Sorter.publishedYearDesc();
                    default:
                        break;
                }
                mViewModel.setComparator(comparator);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Set up the SearchView
        queryInput.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mViewModel.search(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        // Set the filter button
        LinearLayout filter = view.findViewById(R.id.layout_filter);
        filter.setOnClickListener(v -> filterDialog());


        // Set up the search button click listener
        Button queryButton = view.findViewById(R.id.query_button);
        queryButton.setOnClickListener(v -> {
            String input = queryInput.getQuery().toString();
            mViewModel.search(input);
        });

        // Set the onClickListener for the advancedButton
        Button advancedButton = view.findViewById(R.id.advanced_button);
        advancedButton.setOnClickListener(v -> {
            // Create the Intent and launch the activity
            Intent intent = new Intent(getActivity(), BuildQueryActivity.class);
            activityResultLauncher.launch(intent);
        });

        // Set up the RecyclerView
        RecyclerView searchResultsList = view.findViewById(R.id.search_results);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        searchResultsList.setLayoutManager(layoutManager);
        bookAdapter = new BooksAdapter(new ArrayList<>());
        searchResultsList.setAdapter(bookAdapter);

        // Observe the sort comparator
        mViewModel.getComparator().observe(getViewLifecycleOwner(), comparator -> {
            if(comparator == null && spinnerSort.getSelectedItemPosition() != 0) {
                spinnerSort.setSelection(0);
            }
            mViewModel.sortAndFilter();
        });

        // Observe the filter parameters
        mViewModel.getFilterParameters().observe(getViewLifecycleOwner(), parameters -> {
            mViewModel.sortAndFilter();
            TextView textFilter = view.findViewById(R.id.text_filter);
            if (parameters != null && parameters.isFiltered()) {
                textFilter.setTextColor(ContextCompat.getColor(getActivity(), R.color.teal_200));
            } else {
                textFilter.setTextColor(Color.BLACK);
            }
        });

        // Observe the search results
        mViewModel.getSearchResults().observe(getViewLifecycleOwner(), results -> {
            bookAdapter.updateData(results);
        });

        // Observe the message
        mViewModel.getMessage().observe(getViewLifecycleOwner(), message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        });

        // Initial search
        // queryInput.setQuery("", true);
        mViewModel.search("");
    }








    // An instance of ActivityResultLauncher that is used to launch the BuildQueryActivity.
    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        queryInput.setQuery(data.getStringExtra("searchQuery"), false);
                        mViewModel.search(data.getStringExtra("searchQuery"));
                    }
                }
            }
    );

    /**
     * Show the filter dialog
     * The dialog allows the user to filter the search results based on availability and likes
     */
    private void filterDialog() {
        // Create the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.WrapContentDialog);

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_filter, null);

        builder.setView(view);

        AlertDialog dialog = builder.create();

        Chip clipAvailable = view.findViewById(R.id.available);
        Chip clipBorrowed = view.findViewById(R.id.borrowed);
        Chip likesGt49 = view.findViewById(R.id.likes_gt_49);
        Chip likesLt50 = view.findViewById(R.id.likes_lt_50);

        // Set the initial state of the chips
        FilterParameters parameters = mViewModel.getFilterParameters().getValue();
        if (parameters != null) {
            if (parameters.getAvailability() == FilterParameters.Availability.AVAILABLE) {
                clipAvailable.setChecked(true);
            } else if (parameters.getAvailability() == FilterParameters.Availability.BORROWED) {
                clipBorrowed.setChecked(true);
            }

            if (parameters.getLikes() == FilterParameters.Likes.GREATER_THAN_50) {
                likesGt49.setChecked(true);
            } else if (parameters.getLikes() == FilterParameters.Likes.LESS_THAN_50) {
                likesLt50.setChecked(true);
            }
        }


        Button buttonDone = view.findViewById(R.id.button_done);
        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FilterParameters.Availability availability;
                if (!clipAvailable.isChecked() && !clipBorrowed.isChecked())
                    availability = FilterParameters.Availability.ALL;
                else if (clipBorrowed.isChecked()) {
                    availability = FilterParameters.Availability.BORROWED;
                } else {
                    availability = FilterParameters.Availability.AVAILABLE;
                }

                FilterParameters.Likes likes;
                if (!likesGt49.isChecked() && !likesLt50.isChecked())
                    likes = FilterParameters.Likes.ALL;
                else if (likesGt49.isChecked()) {
                    likes = FilterParameters.Likes.GREATER_THAN_50;
                } else {
                    likes = FilterParameters.Likes.LESS_THAN_50;
                }

                FilterParameters parameters = new FilterParameters();
                parameters.setAvailability(availability);
                parameters.setLikes(likes);
                mViewModel.setFilterParameters(parameters);
                dialog.dismiss();
            }
        });

        Button buttonReset = view.findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clipAvailable.setChecked(false);
                clipBorrowed.setChecked(false);
                likesGt49.setChecked(false);
                likesLt50.setChecked(false);
            }
        });

        dialog.show();
    }

}
