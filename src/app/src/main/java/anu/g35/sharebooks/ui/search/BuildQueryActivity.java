package anu.g35.sharebooks.ui.search;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import anu.g35.sharebooks.R;
import anu.g35.sharebooks.data.search.Books;

/**
 * Activity for building a search query
 * The user can enter search criteria such as ISBN, title, authors, categories, and published year
 *
 * @author Junfeng_Gao, u7615533
 * @since 2024-04-23
 */
public class BuildQueryActivity extends AppCompatActivity {
    private EditText queryString, isbnInput, titleInput, authorsInput, categoriesInput, yearInput;
    private final Books books = Books.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_build_query);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.container), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setTitle("Build Query");

        // Initialize the UI components
        queryString = findViewById(R.id.query_string);
        isbnInput = findViewById(R.id.isbn_input);
        titleInput = findViewById(R.id.title_input);
        authorsInput = findViewById(R.id.authors_input);
        categoriesInput = findViewById(R.id.categories_input);
        yearInput = findViewById(R.id.year_input);

        isbnInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                buildQuery();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        titleInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                buildQuery();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        authorsInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                buildQuery();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        categoriesInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                buildQuery();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        yearInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                buildQuery();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        // Set the listeners for the buttons
        categoriesInput.setOnClickListener(v -> showCategoriesDialog());
        yearInput.setOnClickListener(v -> showPublishedYearDialog());
        Button doneButton = findViewById(R.id.done_button);
        Button clearButton = findViewById(R.id.clear_button);
        clearButton.setOnClickListener(v -> clearInputs());
        doneButton.setOnClickListener(v ->{
            buildQuery();
            returnResult(queryString.getText().toString());
        });
    }

    /**
     * Build the search query based on the user input
     *
     * @return The search query
     */
    private void buildQuery() {
        String isbn = isbnInput.getText().toString();
        String title = titleInput.getText().toString();
        String authors = authorsInput.getText().toString();
        String categories = categoriesInput.getText().toString();
        String year = yearInput.getText().toString();

        // special characters in the search string
        String regex = "[&|()]";

        // Build the search query
        String searchQuery = "";
        if (!isbn.isEmpty()  && isbn.matches("[0-9]+")) {
            searchQuery += "ISBN:" + isbn + "&";
        }
        if (!title.isEmpty()) {
            // Replace "[&|()]" which are special characters in the search string with space
            title = title.replaceAll(regex, " ");
            searchQuery += "TITLE:" + title + "&";
        }
        if (!authors.isEmpty()) {
            authors = authors.replaceAll(regex, " ");
            searchQuery += "AUTHORS:" + authors + "&";
        }
        if (!categories.isEmpty()) {
            // Split the categories by "|" to List<String>
            List<String> categoriesList = Arrays.asList(categories.split("\\|"));
            if(categoriesList.size() == 1) {
                searchQuery += "CATEGORY:" + categories + "&";
            } else {
                searchQuery += "(";
                for (String category : categoriesList) {
                    searchQuery += "CATEGORY:" + category + "|";
                }
                // Remove the last "|"
                searchQuery = searchQuery.substring(0, searchQuery.length() - 1);
                searchQuery += ")&";
            }
        }
        if (!year.isEmpty()) {
            // Split the years by "|" to List<String>
            List<String> yearsList = Arrays.asList(year.split("\\|"));
            if(yearsList.size() == 1) {
                searchQuery += "YEAR:" + year + "&";
            } else {
                searchQuery += "(";
                for (String yearStr : yearsList) {
                    searchQuery += "YEAR:" + yearStr + "|";
                }
                // Remove the last "|"
                searchQuery = searchQuery.substring(0, searchQuery.length() - 1);
                searchQuery += ")&";
            }
        }
        // Remove the last "&"
        if (!searchQuery.isEmpty() && searchQuery.charAt(searchQuery.length() - 1) == '&') {
            searchQuery = searchQuery.substring(0, searchQuery.length() - 1);
        }

        queryString.setText(searchQuery);
    }

    /**
     * Clear the input fields
     */
    private void clearInputs() {
        isbnInput.setText("");
        titleInput.setText("");
        authorsInput.setText("");
        categoriesInput.setText("");
        yearInput.setText("");
    }

    /**
     * Return the search query to the calling activity
     * @param searchQuery The search query to return
     */
    private void returnResult(String searchQuery) {
        Intent resultIntent = new Intent();

        // Add the result data to the Intent
        resultIntent.putExtra("searchQuery", searchQuery);
        setResult(Activity.RESULT_OK, resultIntent);

        finish();
    }

    /**
     * Show a dialog to select published years
     */
    private void showPublishedYearDialog() {
        // Get the list of published years
        Set<Integer> yearsSet = books.getPublishedYears();
        List<String> years = yearsSet.stream().map(String::valueOf).sorted().collect(Collectors.toList());

        // Create a boolean array containing all options, initial value is false
        boolean[] checkedItems = new boolean[years.size()];

        // Check each year, if it's in currentYearsList, mark it as checked
        String yearsString = yearInput.getText().toString();
        List<String> currentYearsList = Arrays.asList(yearsString.split("\\|"));
        for (int i = 0; i < years.size(); i++) {
            if (currentYearsList.contains(years.get(i))) {
                checkedItems[i] = true;
            }
        }

        // Create an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Published Year");

        // Set multiple choices
        builder.setMultiChoiceItems(years.toArray(new String[0]), checkedItems,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        // Update the selected items
                        checkedItems[which] = isChecked;
                    }
                });

        // Set the OK button
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle the selected items
                String selectedYears = "";
                for (int i = 0; i < checkedItems.length; i++) {
                    if (checkedItems[i]) {
                        // years.get(i) is selected
                        selectedYears += years.get(i) + "|";
                    }
                }
                // Remove the last "|"
                if (!selectedYears.isEmpty()
                        && selectedYears.charAt(selectedYears.length() - 1) == '|') {
                    selectedYears = selectedYears.substring(0, selectedYears.length() - 1);
                }
                yearInput.setText(selectedYears);
            }
        });

        // Add a "Clear" button
        builder.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Clear all selected items
                Arrays.fill(checkedItems, false);
                yearInput.setText("");
            }
        });

        // Show the dialog
        builder.show();
    }

    /**
     * Show a dialog to select book categories
     * The user can select multiple categories
     */
    private void showCategoriesDialog() {
        // Get book categories
        List<String> categories = new ArrayList<>(books.getCategories());
        Collections.sort(categories);

        // Create a boolean array containing all options, initial value is false
        boolean[] checkedItems = new boolean[categories.size()];

        // Check each category, if it's in currentCategoriesList, mark it as checked
        String categoriesString = categoriesInput.getText().toString();
        List<String> currentCategoriesList = Arrays.asList(categoriesString.split("\\|"));
        for (int i = 0; i < categories.size(); i++) {
            if (currentCategoriesList.contains(categories.get(i))) {
                checkedItems[i] = true;
            }
        }

        // Create an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Categories");

        // Set multiple choices
        builder.setMultiChoiceItems(categories.toArray(new String[0]), checkedItems,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        // Update the selected items
                        checkedItems[which] = isChecked;
                    }
                });

        // Set the OK button
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle the selected items
                String selectedCategories = "";
                for (int i = 0; i < checkedItems.length; i++) {
                    if (checkedItems[i]) {
                        // categories.get(i) is selected
                        selectedCategories += categories.get(i) + "|";
                    }
                }
                // Remove the last "|"
                if (!selectedCategories.isEmpty()
                        && selectedCategories.charAt(selectedCategories.length() - 1) == '|') {
                    selectedCategories = selectedCategories.substring(0, selectedCategories.length() - 1);
                }
                categoriesInput.setText(selectedCategories);
            }
        });

        // Add a "Clear" button
        builder.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Clear all selected items
                Arrays.fill(checkedItems, false);
                categoriesInput.setText("");
            }
        });

        // Show the dialog
        builder.show();
    }

}
