package anu.g35.sharebooks.ui.search;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import anu.g35.sharebooks.R;
import anu.g35.sharebooks.data.model.Book;
import anu.g35.sharebooks.ui.CommTools;
import anu.g35.sharebooks.ui.book.BookDetailActivity;

/**
 * Adapter for the RecyclerView that displays the search results
 *
 * @author Junfeng_Gao, u7615533
 * @since 2024-04-23
 */
/**
 * Modified existing code to add pagination functionality.
 * Users use buttons to switch pages.
 * To align with Android user habits, enable page switching through swiping, supporting both vertical and horizontal gestures.
 *
 * @author Huizhe_Ruan, u7723366
 * @since 2024-05-07
 */
public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {

    private List<Book> books;

    public BooksAdapter(List<Book> books) {
        this.books = books;
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return
     */
    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * @param holder   The ViewHolder which should be updated to represent the contents of the item
     *                 at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);
        holder.titleTextView.setText(book.getTitle());
        holder.categoryTextView.setText(book.getCategory());
        holder.authorsTextView.setText(book.getAuthors());
        holder.publishedYearTextView.setText(String.valueOf(book.getPublishedYear()));

        // Load the thumbnail image using Glide
        CommTools.loadImage(holder.thumbnailImageView, book.getThumbnail(), R.drawable.baseline_manage_search_24);

        // Set click listener
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), BookDetailActivity.class);
            intent.putExtra("Book", book);
            v.getContext().startActivity(intent);
        });
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return books.size();
    }

    /**
     * ViewHolder for the Book items in the RecyclerView
     */
    static class BookViewHolder extends RecyclerView.ViewHolder {

        ImageView thumbnailImageView;
        TextView titleTextView;
        TextView categoryTextView;
        TextView authorsTextView;
        TextView publishedYearTextView;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnailImageView = itemView.findViewById(R.id.book_thumbnail);
            titleTextView = itemView.findViewById(R.id.book_title);
            categoryTextView = itemView.findViewById(R.id.book_category);
            authorsTextView = itemView.findViewById(R.id.book_authors);
            publishedYearTextView = itemView.findViewById(R.id.book_published_year);
        }
    }

    /**
     * Update the data in the adapter
     * @param newBooks The new list of books to display
     */
    public void updateData(List<Book> newBooks) {
        this.books.clear();
        this.books.addAll(newBooks);
        notifyDataSetChanged();
    }

}
