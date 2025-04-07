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
import java.util.HashSet;
import java.util.List;

import anu.g35.sharebooks.R;
import anu.g35.sharebooks.data.datasource.UserDataSource;
import anu.g35.sharebooks.data.model.Book;
import anu.g35.sharebooks.data.model.User;
import anu.g35.sharebooks.data.search.Books;
import anu.g35.sharebooks.data.session.UserSession;
import anu.g35.sharebooks.ui.search.BooksAdapter;

    /**
     * This activity is used to display the list of books that the user liked or borrowed
     * The user can click on the book to view the book details
     * The user can also click on the user to view the user profile
     *
     * @Author u7615533, Junfeng Gao
     * @since 2024-05-05
     */
    public class BookListActivity extends AppCompatActivity {
        String userId;
        String group;

        BooksAdapter booksAdapter;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_book_list);
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });


            // Get the user id and the group from the intent
            setContentView(R.layout.activity_book_list);
            String param = getIntent().getStringExtra("Group:UserId");
            String[] parts = param.split(":");
            group = parts[0];
            userId = parts[1];

            RecyclerView recyclerViewUsers = findViewById(R.id.recycler_view_books);
            recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));
            booksAdapter = new BooksAdapter(getBookList());
            recyclerViewUsers.setAdapter(booksAdapter);

        }

        /**
         * This is used to update the user list when the user follows/unfollows another user
         */
        @Override
        protected void onResume() {
            super.onResume();
            booksAdapter.updateData(getBookList());
        }

        /**
         * This method is used to get the list of books that the user liked or borrowed
         * @return List<Book> - the list of books
         */
        private List<Book> getBookList() {
            UserDataSource userDataSource = UserDataSource.getInstance();
            Books books = Books.getInstance();
            User user = userDataSource.getUser(userId);
            List<Long>  isbnList = null;
            String title = "";
            UserSession userSession = UserSession.getInstance();
            User currentUser = userSession.getUser();

            if (currentUser.getId().equals(userId)) {
                title = "My ";
            } else {
                title = user.getName() + "'s ";
            }

            if (group.toUpperCase().equals("LIKED")) {
                isbnList = user.getLikeBooks();
                title += " Liked";
            } else if (group.toUpperCase().equals("BORROWED")) {
                isbnList = new ArrayList<>(books.getISBNSetByBorrower(userId));
                title += " Borrowed";
            }
            setTitle(title + " Book List");

            return books.searchByISBNSet(new HashSet<>(isbnList));
        }


    }

