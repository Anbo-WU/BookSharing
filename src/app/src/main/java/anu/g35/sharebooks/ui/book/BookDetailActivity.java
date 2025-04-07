package anu.g35.sharebooks.ui.book;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import anu.g35.sharebooks.R;
import anu.g35.sharebooks.data.datasource.BookDataSource;
import anu.g35.sharebooks.data.model.Book;
import anu.g35.sharebooks.data.session.UserSession;
import anu.g35.sharebooks.ui.CommTools;

/**
 * This activity displays the details of a book like title, authors, category, etc.
 * It also allows the user to like/dislike the book, borrow/return the book, and view the book owner's profile.
 *
 * @Author u7706346 Anbo Wu
 * @since 2024-04-28
 */
public class BookDetailActivity extends AppCompatActivity {
    BookDataSource bookDataSource = BookDataSource.getInstance();
    UserSession userSession = UserSession.getInstance();

    LinearLayout layoutBorrow;
    LinearLayout layoutReturn;
    LinearLayout layoutLiked;
    LinearLayout layoutDisliked;
    TextView likeCountTextView;

    Book book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get the book from the intent
        book = (Book) getIntent().getSerializableExtra("Book");

        // Get the book from the data source
        book = bookDataSource.getBookByISBN(book.getIsbn());

        setTitle("Book Detail");

        // Fill the views with the book's properties
        ImageView thumbnailImageView = findViewById(R.id.book_thumbnail);
        TextView titleTextView = findViewById(R.id.book_title);
        TextView bookIsbn = findViewById(R.id.book_isbn);
        TextView authorsTextView = findViewById(R.id.book_authors);
        TextView categoryTextView = findViewById(R.id.book_category);
        TextView descriptionTextView = findViewById(R.id.book_description);
        TextView publishedYearTextView = findViewById(R.id.book_publishedYear);
        TextView averageRatingTextView = findViewById(R.id.book_averageRating);
        TextView numPagesTextView = findViewById(R.id.book_numPages);
        TextView ratingsCountTextView = findViewById(R.id.book_ratingsCount);
        likeCountTextView = findViewById(R.id.liked_count);

        CommTools.loadImage(thumbnailImageView, book.getThumbnail(),
                R.drawable.baseline_manage_search_24);
        titleTextView.setText(book.getTitle());
        bookIsbn.setText(book.getIsbn().toString());
        authorsTextView.setText(book.getAuthors());
        categoryTextView.setText(book.getCategory());
        descriptionTextView.setText(book.getDescription());
        publishedYearTextView.setText(String.valueOf(book.getPublishedYear()));
        averageRatingTextView.setText(String.valueOf(book.getAverageRating()));
        numPagesTextView.setText(String.valueOf(book.getNumPages()));
        ratingsCountTextView.setText(String.valueOf(book.getRatingsCount()));


        // Set the click listeners for the like/dislike, borrow/return buttons

        layoutLiked = findViewById(R.id.layout_like);
        layoutLiked.setOnClickListener(v -> {
            userSession.likeBook(book.getIsbn(), true);
            setBook();
        });

        layoutDisliked = findViewById(R.id.layout_dislike);
        layoutDisliked.setOnClickListener(v -> {
            userSession.likeBook(book.getIsbn(), false);
            setBook();
        });

        layoutBorrow = findViewById(R.id.layout_borrow);
        layoutBorrow.setOnClickListener(v -> {
            userSession.borrowBook(book.getIsbn());
            setBook();
            showMessage("The book has been borrowed successfully. ");
        });

        layoutReturn = findViewById(R.id.layout_return);
        layoutReturn.setOnClickListener(v -> {
            userSession.returnBook(book.getIsbn());
            setBook();
            showMessage("The book has been returned successfully. ");
        });

        setBook();
    }

    /**
     * This method sets the visibility of the borrow/return buttons and the like/dislike buttons
     * based on the book's properties.
     */
    void setBook() {
        // Refresh the book from the data source
        book = bookDataSource.getBookByISBN(book.getIsbn());

        likeCountTextView.setText(String.valueOf(book.getLikedCount()));

        String borrowerId = book.getBorrower();
        if (borrowerId == null || borrowerId.equals("")) {
            layoutBorrow.setVisibility(LinearLayout.VISIBLE);
            layoutReturn.setVisibility(LinearLayout.GONE);
        } else if (borrowerId.equals(userSession.getUser().getId())) {
            layoutBorrow.setVisibility(LinearLayout.GONE);
            layoutReturn.setVisibility(LinearLayout.VISIBLE);
        } else {
            layoutBorrow.setVisibility(LinearLayout.GONE);
            layoutReturn.setVisibility(LinearLayout.GONE);
        }

        if (userSession.getUser().getLikeBooks().contains(book.getIsbn())) {
            layoutLiked.setVisibility(LinearLayout.GONE);
            layoutDisliked.setVisibility(LinearLayout.VISIBLE);
        } else {
            layoutLiked.setVisibility(LinearLayout.VISIBLE);
            layoutDisliked.setVisibility(LinearLayout.GONE);
        }

    }

    /**
     * This method shows a message to the user.
     *
     * @param message The message to be shown
     */
    void showMessage(String message) {
        message = message + "Please contact the owner of the book to complete the physical book handover.";
        new AlertDialog.Builder(this)
                .setTitle("Info")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}