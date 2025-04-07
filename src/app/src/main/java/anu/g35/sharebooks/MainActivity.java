package anu.g35.sharebooks;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import anu.g35.sharebooks.data.datasource.BookDataSource;
import anu.g35.sharebooks.data.session.UserSession;
import anu.g35.sharebooks.databinding.ActivityMainBinding;

/**
 * The main activity of the app.
 * This activity is responsible for setting up the navigation bar and the navigation graph.
 * It also initializes the data source and the user session
 *
 * @author u7703248 Chuang Ma
 * @since 2024-04-19
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Pre Initialize the data source
        BookDataSource.getInstance();
        // tmp login
        // UserSession.getInstance().login("test@t.t", "123456");

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard,
                R.id.navigation_profile, R.id.navigation_search)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


    }

}