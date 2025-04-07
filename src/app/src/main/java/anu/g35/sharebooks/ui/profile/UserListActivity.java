package anu.g35.sharebooks.ui.profile;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import anu.g35.sharebooks.R;
import anu.g35.sharebooks.data.datasource.UserDataSource;
import anu.g35.sharebooks.data.model.User;
import anu.g35.sharebooks.data.session.UserSession;


    /** This activity is used to display the list of users
     * The user can click on the user to view the user profile
     *
     * @Author u7615533, Junfeng Gao
     * @since 2024-05-05
     */
    public class UserListActivity extends AppCompatActivity {

        UserAdapter userAdapter;
        String userId;
        String group;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_user_list);
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });

            setContentView(R.layout.activity_user_list);
            String param = getIntent().getStringExtra("Group:UserId");
            String[] parts = param.split(":");
            group = parts[0];
            userId = parts[1];

            RecyclerView recyclerViewUsers = findViewById(R.id.recycler_view_users);
            recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));
            userAdapter = new UserAdapter(getUserIdList());
            recyclerViewUsers.setAdapter(userAdapter);

        }

        /**
         * This is used to update the user list when the user follows/unfollows another user
         */
        @Override
        protected void onResume() {
            super.onResume();
            userAdapter.updateData(getUserIdList());
        }

        /**
         * Get the list of user ids based on the group
         * @return The list of user ids
         */
        private ArrayList<String> getUserIdList() {
            UserDataSource userDataSource = UserDataSource.getInstance();
            User user = userDataSource.getUser(userId);
            ArrayList<String> userIdList = new ArrayList<>();
            String title = "";
            UserSession userSession = UserSession.getInstance();
            User currentUser = userSession.getUser();

            if (currentUser.getId().equals(userId)) {
                title = "My ";
            } else {
                title = user.getName() + "'s ";
            }

            if (group.toUpperCase().equals("FANS")) {
                userIdList.addAll(user.getFans());
                title += " Fans";
            } else if (group.toUpperCase().equals("FOLLOWING")) {
                userIdList.addAll(user.getFollowing());
                title += " Following Users";
            }

            setTitle(title+ " List");

            return userIdList;
        }
    }

