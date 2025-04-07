package anu.g35.sharebooks.ui.profile;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import anu.g35.sharebooks.R;
import anu.g35.sharebooks.data.datasource.UserDataSource;

/**
 * This activity is used to display the user's detail
 * The user can click on the user to view the user profile
 *
 * @Author u7615533, Junfeng Gao
 * @since 2024-05-05
 */
public class UserDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        if (savedInstanceState == null) {
            String userId = getIntent().getStringExtra("userId");
            UserDataSource userDataSource = UserDataSource.getInstance();
            setTitle( userDataSource.getUser(userId).getName() + "'s Detail");

            ProfileFragment profileFragment = new ProfileFragment();
            Bundle bundle = new Bundle();
            bundle.putString("userId", userId);
            profileFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, profileFragment)
                    .commit();
        }
    }
}