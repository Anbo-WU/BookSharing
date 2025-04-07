package anu.g35.sharebooks.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import anu.g35.sharebooks.R;
import anu.g35.sharebooks.data.datasource.BookDataSource;
import anu.g35.sharebooks.data.datasource.UserDataSource;
import anu.g35.sharebooks.data.model.Book;
import anu.g35.sharebooks.data.model.User;
import anu.g35.sharebooks.data.search.Books;
import anu.g35.sharebooks.ui.CommTools;
import anu.g35.sharebooks.ui.book.BookDetailActivity;


    /**
     * This activity is used to display the list of users
     * The user can click on the user to view the user profile
     *
     * @Author u7615533, Junfeng Gao
     * @since 2024-05-05
     */
    public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

        private List<String> userIdList;
        private UserDataSource userDataSource;
        private Books books;

        public UserAdapter(List<String> userIdList) {
            userDataSource = UserDataSource.getInstance();
            books = Books.getInstance();
            this.userIdList = userIdList;
        }

        @NonNull
        @Override
        public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
            return new UserViewHolder(view);
        }

        /**
         * Bind the data to the view holder
         * @param holder The view holder to bind the data to
         * @param position The position of the data in the list
         */
        @Override
        public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
            User user = userDataSource.getUser(userIdList.get(position));
            holder.userName.setText(user.getName());
            CommTools.loadImage(holder.userAvatar, user.getAvatar(), R.drawable.user_avator_default);
            holder.fansCount.setText(String.valueOf(user.getFans().size()));
            holder.followingCount.setText(String.valueOf(user.getFollowing().size()));
            holder.likeCount.setText(String.valueOf(user.getLikeBooks().size()));
            holder.borrowCount.setText(String.valueOf(books.getISBNSetByBorrower(user.getId()).size()));

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), UserDetailActivity.class);
                intent.putExtra("userId", user.getId());
                v.getContext().startActivity(intent);

            });
        }

        @Override
        public int getItemCount() {
            return userIdList.size();
        }

        static class UserViewHolder extends RecyclerView.ViewHolder {

            ImageView userAvatar;
            TextView userName;
            TextView fansCount;
            TextView followingCount;
            TextView likeCount;
            TextView borrowCount;

            public UserViewHolder(@NonNull View itemView) {
                super(itemView);
                userName = itemView.findViewById(R.id.user_name);
                userAvatar = itemView.findViewById(R.id.user_avatar);
                fansCount = itemView.findViewById(R.id.fans_count);
                followingCount = itemView.findViewById(R.id.following_count);
                likeCount = itemView.findViewById(R.id.like_count);
                borrowCount = itemView.findViewById(R.id.borrow_count);
            }

        }

        /**
         * Update the data in the adapter
         * @param userIdList The new list of users to display
         */
        public void updateData(List<String> userIdList) {
            this.userIdList.clear();
            this.userIdList.addAll(userIdList);
            notifyDataSetChanged();
        }
    }

