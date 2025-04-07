package anu.g35.sharebooks.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import anu.g35.sharebooks.data.datasource.BookDataSource;
import anu.g35.sharebooks.data.datasource.UserDataSource;
import anu.g35.sharebooks.data.model.Book;
import anu.g35.sharebooks.data.model.User;
import anu.g35.sharebooks.data.search.Books;
import anu.g35.sharebooks.data.session.UserSession;


/**
 * ViewModel for the ProfileFragment
 * This class is used to manage the data for the ProfileFragment
 *
 * @Author u7615533, Junfeng Gao
 * @since 2024-05-05
 */

public class ProfileViewModel extends ViewModel {

    UserDataSource userDataSource;
    Books books;
    private UserSession userSession;
    private  User user;
    private final MutableLiveData<String> mID;
    private final MutableLiveData<String> mAvatar;
    private final MutableLiveData<String> mName;
    private final MutableLiveData<String> mAddress;
    private final MutableLiveData<String> mBiography;
    private final MutableLiveData<String> mFansCount;
    private final MutableLiveData<String> mFollowingCount;
    private final MutableLiveData<String> mLikedBooksCount;
    private final MutableLiveData<String> mBorrowBooksCount;

    // Whether the current user is following the user whose profile is being viewed
    private final MutableLiveData<Boolean> isFollowed;

    private Boolean isCurrentUserProfile = true;

    public ProfileViewModel() {
        userDataSource = UserDataSource.getInstance();
        userSession = UserSession.getInstance();
        books = Books.getInstance();
        mID = new MutableLiveData<>();
        mName = new MutableLiveData<>();
        mAddress = new MutableLiveData<>();
        mBiography = new MutableLiveData<>();
        mAvatar = new MutableLiveData<>();
        mFansCount = new MutableLiveData<>();
        mFollowingCount = new MutableLiveData<>();
        mLikedBooksCount = new MutableLiveData<>();
        mBorrowBooksCount = new MutableLiveData<>();
        isFollowed = new MutableLiveData<>();
    }

    /**
     * Set the user whose profile is being viewed
     *
     * @param userId The ID of the user whose profile is being viewed
     */
    public void setUser(String userId) {
        isCurrentUserProfile = false;
        user = userDataSource.getUser(userId);
        setUserInfo();
    }

    /**
     * Set the current user's profile
     */
    public void setSession() {
        isCurrentUserProfile = true;
        user = userSession.getUser();
        setUserInfo();
    }

    /**
     * Set the user information
     */
    public void setUserInfo() {
        mID.setValue(user.getId());
        mName.setValue(user.getName());
        mAddress.setValue(user.getAddress());
        mBiography.setValue(user.getBiography());
        mAvatar.setValue(user.getAvatar());
        mFansCount.setValue(String.valueOf(user.getFans().size()));
        mFollowingCount.setValue(String.valueOf(user.getFollowing().size()));
        mLikedBooksCount.setValue(String.valueOf(user.getLikeBooks().size()));
        mBorrowBooksCount.setValue(String.valueOf(books.getISBNSetByBorrower(user.getId()).size()));

        if (!isCurrentUserProfile) {
            String currentUserId = userSession.getUser().getId();
            List<String> fans = user.getFans();
            isFollowed.setValue(fans.contains(currentUserId));
        }

    }


    public LiveData<String> getID() {
        return mID;
    }

    public LiveData<String> getName() {
        return mName;
    }

    public LiveData<String> getAddress() {
        return mAddress;
    }

    public LiveData<String> getBiography() {
        return mBiography;
    }

    public LiveData<String> getAvatar() {
        return mAvatar;
    }

    public LiveData<String> getFansCount() {
        return mFansCount;
    }

    public LiveData<String> getFollowingCount() {
        return mFollowingCount;
    }

    public LiveData<String> getLikedBooksCount() {
        return mLikedBooksCount;
    }

    public LiveData<String> getBorrowBooksCount() {
        return mBorrowBooksCount;
    }


    public LiveData<Boolean> getIsFollowed() {
        return isFollowed;
    }


    /**
     * Follow the user whose profile is being viewed
     */
    public void follow() {
        userSession.followUser(user.getId(), true);
        setUserInfo();
    }

    /**
     * Unfollow the user whose profile is being viewed
     */
    public void unfollow() {
        userSession.followUser(user.getId(), false);
        setUserInfo();
    }


}