package anu.g35.sharebooks.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import anu.g35.sharebooks.R;
import anu.g35.sharebooks.data.session.UserSession;
import anu.g35.sharebooks.databinding.FragmentProfileBinding;
import anu.g35.sharebooks.ui.CommTools;
import anu.g35.sharebooks.ui.login.LoginActivity;

/**
 * A fragment representing a user's profile.
 * If no arguments are provided, the current user's profile is shown.
 * If an argument is provided, the profile of the user with the given ID is shown.
 *
 * @Author u7615533, Junfeng Gao
 * @since 2024-05-05
 */
public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    ProfileViewModel profileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView idTextView = binding.userId;
        profileViewModel.getID().observe(getViewLifecycleOwner(), idTextView::setText);

        final TextView nameTextView = binding.name;
        profileViewModel.getName().observe(getViewLifecycleOwner(), nameTextView::setText);

        final TextView addressTextView = binding.address;
        profileViewModel.getAddress().observe(getViewLifecycleOwner(), addressTextView::setText);

        final TextView biographyTextView = binding.biography;
        profileViewModel.getBiography().observe(getViewLifecycleOwner(), biographyTextView::setText);

        final ImageView avatarImageView = binding.avatar;
        profileViewModel.getAvatar().observe(getViewLifecycleOwner(), url -> {
            CommTools.loadImage(avatarImageView, url, R.drawable.user_avator_default);
        });

        final TextView fansCountTextView = binding.fansCount;
        profileViewModel.getFansCount().observe(getViewLifecycleOwner(), fansCountTextView::setText);

        final TextView followingCountTextView = binding.followingCount;
        profileViewModel.getFollowingCount().observe(getViewLifecycleOwner(), followingCountTextView::setText);

        final TextView likedBooksCountTextView = binding.likedBooksCount;
        profileViewModel.getLikedBooksCount().observe(getViewLifecycleOwner(), likedBooksCountTextView::setText);

        final TextView borrowBooksCountTextView = binding.borrowBooksCount;
        profileViewModel.getBorrowBooksCount().observe(getViewLifecycleOwner(), borrowBooksCountTextView::setText);


        final LinearLayout followMe = binding.followMe;
        final LinearLayout unfollow = binding.unfollow;
        // Show the follow button if the user is not the current user
        profileViewModel.getIsFollowed().observe(getViewLifecycleOwner(), isFollowed -> {
            if (isFollowed) {
                followMe.setVisibility(View.GONE);
                unfollow.setVisibility(View.VISIBLE);
            } else {
                followMe.setVisibility(View.VISIBLE);
                unfollow.setVisibility(View.GONE);
            }
        });

        // When the user clicks the logoutButton, the user is logged out and redirected to the LoginActivity
        Button logoutButton = binding.logoutButton;
        logoutButton.setOnClickListener(v -> {
            UserSession.getInstance().logout();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });


        // Get user ID from arguments
        // If no arguments, show the current user's profile
        // Otherwise, show the profile of the user with the given ID
        if (getArguments() != null) {
            String userId = getArguments().getString("userId");
            profileViewModel.setUser(userId);
            logoutButton.setVisibility(View.GONE);
        } else {
            profileViewModel.setSession();
            logoutButton.setVisibility(View.VISIBLE);
        }


        followMe.setOnClickListener(v -> {
            profileViewModel.follow();
        });

        unfollow.setOnClickListener(v -> {
            profileViewModel.unfollow();
        });

        // Set the onClickListener for the layoutMyFans, layoutMyFollowing, layoutMyLikedBooks and layoutMyBorrowedBooks
        final LinearLayout layoutMyFans = binding.layoutMyFans;
        layoutMyFans.setOnClickListener(v -> {
            if (profileViewModel.getID().getValue() == null || profileViewModel.getFansCount().getValue().equals("0")) {
                return;
            }
            Intent intent = new Intent(getActivity(), UserListActivity.class);
            intent.putExtra("Group:UserId", "Fans" + ":" + profileViewModel.getID().getValue());
            startActivity(intent);
        });

        final LinearLayout layoutMyFollowing = binding.layoutMyFollowing;
        layoutMyFollowing.setOnClickListener(v -> {
            if (profileViewModel.getID().getValue() == null || profileViewModel.getFansCount().getValue().equals("0")) {
                return;
            }
            Intent intent = new Intent(getActivity(), UserListActivity.class);
            intent.putExtra("Group:UserId", "Following" + ":" + profileViewModel.getID().getValue());
            startActivity(intent);
        });

        final LinearLayout layoutMyLikedBooks = binding.layoutMyLike;
        layoutMyLikedBooks.setOnClickListener(v -> {
            if (profileViewModel.getID().getValue() == null || profileViewModel.getLikedBooksCount().getValue().equals("0")) {
                return;
            }
            Intent intent = new Intent(getActivity(), BookListActivity.class);
            intent.putExtra("Group:UserId", "Liked" + ":" + profileViewModel.getID().getValue());
            startActivity(intent);
        });

        final LinearLayout layoutMyBorrowedBooks = binding.layoutMyBorrow;
        layoutMyBorrowedBooks.setOnClickListener(v -> {
            if (profileViewModel.getID().getValue() == null || profileViewModel.getBorrowBooksCount().getValue().equals("0")) {
                return;
            }
            Intent intent = new Intent(getActivity(), BookListActivity.class);
            intent.putExtra("Group:UserId", "Borrowed" + ":" + profileViewModel.getID().getValue());
            startActivity(intent);
        });

        return root;
    }

    /**
     * Set the user to show the profile of
     * update the count of fans, following, liked books and borrowed books
     */
    @Override
    public void onResume() {
        super.onResume();
        profileViewModel.setUserInfo();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}