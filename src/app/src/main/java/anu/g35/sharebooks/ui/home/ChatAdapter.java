package anu.g35.sharebooks.ui.home;

import static anu.g35.sharebooks.ui.CommTools.loadImage;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import anu.g35.sharebooks.R;
import anu.g35.sharebooks.data.datasource.BookDataSource;
import anu.g35.sharebooks.data.datasource.UserDataSource;
import anu.g35.sharebooks.data.model.Book;
import anu.g35.sharebooks.data.model.User;
import anu.g35.sharebooks.data.model.UserAction;
import anu.g35.sharebooks.data.session.UserSession;
import anu.g35.sharebooks.ui.book.BookDetailActivity;
import anu.g35.sharebooks.ui.profile.UserDetailActivity;

/**
 * Adapter for the chat room
 *
 * @Author Huizhe_Ruan, u7723366
 * @since 2024-05-05
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    private final ArrayList<UserAction> userActions;
    private final UserDataSource userDataSource;
    private final BookDataSource bookDataSource;
    private final UserSession userSession;

    public ChatAdapter(ArrayList<UserAction> userActions) {
        this.userActions = userActions;

        this.bookDataSource = BookDataSource.getInstance();
        this.userDataSource = UserDataSource.getInstance();
        this.userSession = UserSession.getInstance();
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        return new ChatViewHolder(view);
    }

    /**
     * Bind the data to the ViewHolder
     * @param holder The ViewHolder to bind the data to
     * @param position The position of the data in the adapter
     */
    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        UserAction action = userActions.get(position);

        // Set the timestamp
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        holder.timestampTextView.setText(action.getTimestamp().format(formatter));

        if(!action.getUserId().equals(userSession.getUser().getId())){
            holder.othersContentView.setVisibility(View.VISIBLE);
            holder.myContentView.setVisibility(View.GONE);
            setOtherUserActionView(holder, action);
        } else {
            holder.othersContentView.setVisibility(View.GONE);
            holder.myContentView.setVisibility(View.VISIBLE);
            setMyActionView(holder, action);
        }

    }

    /**
     * Set the view for the current user's action
     * @param holder The ViewHolder to set the view in
     * @param action The action to display
     */
    private void setMyActionView(ChatViewHolder holder, UserAction action) {
        // Set the user's name
        User user = userDataSource.getUser(action.getUserId());
        loadImage(holder.myIconImageView, user.getAvatar(), R.drawable.user_avator_default);


        // If the action is a SAY action, display the message
        if (action.getActionType() == UserAction.Type.SAY) {
            holder.myMessageTextView.setVisibility(View.VISIBLE);
            holder.myMultimediaView.setVisibility(View.GONE);
            holder.myMessageTextView.setText(action.getContent());
        } else {
            // If the action is a FOLLOW or UNFOLLOW action, display the user's name
            holder.myMessageTextView.setVisibility(View.GONE);
            holder.myMultimediaView.setVisibility(View.VISIBLE);
            holder.myActionTypeTextView.setText(action.getActionType().getValue());
            if (action.getActionType() == UserAction.Type.FOLLOW ||
                    action.getActionType() == UserAction.Type.UNFOLLOW ){
                User atUser = userDataSource.getUser(action.getAtUserId());
                loadImage(holder.myActionImageView, atUser.getAvatar(), R.drawable.user_avator_default);

                holder.myActionTitleTextView.setText(atUser.getName());
                // Set click listener
                holder.myMultimediaView.setOnClickListener(v -> {
                    Intent intent = new Intent(v.getContext(), UserDetailActivity.class);
                    intent.putExtra("userId", atUser.getId());
                    v.getContext().startActivity(intent);
                });
            } else {
                // If the action is a LIKE\UNLIKE\BORROW\RETURN action, display the book's title
                Book atBook = bookDataSource.getBookByISBN(action.getAtBookISBN());
                holder.myActionTitleTextView.setText(atBook.getTitle());
                loadImage(holder.myActionImageView, atBook.getThumbnail(), R.drawable.baseline_manage_search_24);

                // Set click listener
                holder.myMultimediaView.setOnClickListener(v -> {
                    Intent intent = new Intent(v.getContext(), BookDetailActivity.class);
                    intent.putExtra("Book", atBook);
                    v.getContext().startActivity(intent);
                });
            }
        }


    }

    /**
     * Set the view for the other user's action
     * @param holder The ViewHolder to set the view in
     * @param action The action to display
     */
    private void setOtherUserActionView(ChatViewHolder holder, UserAction action) {

        // Set the other user's name
        User user = userDataSource.getUser(action.getUserId());
        holder.otherNameTextView.setText(user.getName());

        // Load the other user's avatar
        loadImage(holder.otherIconImageView, user.getAvatar(), R.drawable.user_avator_default);

        // Set click listener
        holder.otherIconImageView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), UserDetailActivity.class);
            intent.putExtra("userId", user.getId());
            v.getContext().startActivity(intent);
        });

        // Set the other user's action
        // If the action is a SAY action, display the message
        if (action.getActionType() == UserAction.Type.SAY) {
            holder.otherMessageTextView.setVisibility(View.VISIBLE);
            holder.otherMultimediaView.setVisibility(View.GONE);
            holder.otherMessageTextView.setText(action.getContent());
        } else {
            // If the action is a FOLLOW or UNFOLLOW action, display the user's name
            holder.otherMessageTextView.setVisibility(View.GONE);
            holder.otherMultimediaView.setVisibility(View.VISIBLE);
            holder.otherActionTypeTextView.setText(action.getActionType().getValue());
            if (action.getActionType() == UserAction.Type.FOLLOW ||
                    action.getActionType() == UserAction.Type.UNFOLLOW) {
                User atUser = userDataSource.getUser(action.getAtUserId());
                holder.otherActionTitleTextView.setText(atUser.getName());
                loadImage(holder.otherActionImageView, atUser.getAvatar(), R.drawable.user_avator_default);

                // The action is the current user, not set click listener
                if(action.getAtUserId().equals(userSession.getUser().getId())) {
                    holder.otherActionTitleTextView.setText("Me");
                    holder.otherMultimediaView.setOnClickListener(v -> {});
                } else {
                    // Set click listener
                    holder.otherMultimediaView.setOnClickListener(v -> {
                        Intent intent = new Intent(v.getContext(), UserDetailActivity.class);
                        intent.putExtra("userId", atUser.getId());
                        v.getContext().startActivity(intent);
                    });
                }


            } else {
                // If the action is a LIKE\UNLIKE\BORROW\RETURN action, display the book's title
                Book atBook = bookDataSource.getBookByISBN(action.getAtBookISBN());
                holder.otherActionTitleTextView.setText(atBook.getTitle());
                loadImage(holder.otherActionImageView, atBook.getThumbnail(), R.drawable.baseline_manage_search_24);

                // Set click listener
                holder.otherMultimediaView.setOnClickListener(v -> {
                    Intent intent = new Intent(v.getContext(), BookDetailActivity.class);
                    intent.putExtra("Book", atBook);
                    v.getContext().startActivity(intent);
                });
            }
        }
    }

    /**
     * Get the number of items in the adapter
     * @return The number of items in the adapter
     */
    @Override
    public int getItemCount() {
        return userActions.size();
    }


    /**
     * ViewHolder for the chat item Class
     */
    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView timestampTextView;
        LinearLayout othersContentView;
        TextView otherNameTextView;
        ImageView otherIconImageView;
        TextView otherMessageTextView;
        LinearLayout otherMultimediaView;
        TextView otherActionTypeTextView;
        ImageView otherActionImageView;
        TextView otherActionTitleTextView;

        LinearLayout myContentView;
        TextView myMessageTextView;
        LinearLayout myMultimediaView;
        TextView myActionTypeTextView;
        ImageView myActionImageView;
        TextView myActionTitleTextView;
        ImageView myIconImageView;


        /**
         * Constructor
         * @param itemView The view of the chat item
         */
        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            timestampTextView = itemView.findViewById(R.id.timestampTextView);
            othersContentView = itemView.findViewById(R.id.othersContentView);
            otherIconImageView = itemView.findViewById(R.id.otherIconImageView);
            otherNameTextView = itemView.findViewById(R.id.otherNameTextView);
            otherMessageTextView = itemView.findViewById(R.id.otherMessageTextView);
            otherMultimediaView = itemView.findViewById(R.id.otherMultimediaView);
            otherActionTypeTextView = itemView.findViewById(R.id.otherActionTypeTextView);
            otherActionImageView = itemView.findViewById(R.id.otherActionImageView);
            otherActionTitleTextView = itemView.findViewById(R.id.otherActionTitleTextView);

            myContentView = itemView.findViewById(R.id.myContentView);
            myMessageTextView = itemView.findViewById(R.id.myMessageTextView);
            myMultimediaView = itemView.findViewById(R.id.myMultimediaView);
            myActionTypeTextView = itemView.findViewById(R.id.myActionTypeTextView);
            myActionImageView = itemView.findViewById(R.id.myActionImageView);
            myActionTitleTextView = itemView.findViewById(R.id.myActionTitleTextView);
            myIconImageView = itemView.findViewById(R.id.myIconImageView);
        }
    }


}