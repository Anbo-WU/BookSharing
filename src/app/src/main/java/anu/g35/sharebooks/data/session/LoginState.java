package anu.g35.sharebooks.data.session;

import anu.g35.sharebooks.data.model.Book;
import anu.g35.sharebooks.data.model.User;

/**
 * The state of the user session when the user is logged in
 * The user can like a book, follow another user, borrow a book, return a book, and log out
 *
 * @author u7703248 Chuang Ma
 * @since 2024-04-25
 */
public class LoginState extends UserState{
    public LoginState(UserSession userSession) {
        super(userSession);
    }

    @Override
    public User login(String userId, String password) {
        return null;
    }

    /**
     * Current session user likes a book
     *
     * @param isbn the isbn of the book to like
     * @param isLike true if the book is to be liked, false if the book is to be unliked
     * @return true
     */
    @Override
    public boolean likeBook(long isbn, boolean isLike) {
        String currentUserId = userSession.getUser().getId();
        return likeBook(isbn, isLike, currentUserId);

    }

    /**
     * User likes a book
     *
     * @param isbn the isbn of the book to like
     * @param isLike true if the book is to be liked, false if the book is to be unliked
     * @param fromUserId the id of the user who likes the book
     * @return true
     */
    @Override
    public boolean likeBook(long isbn, boolean isLike, String fromUserId) {
        User fromUser = userDataSource.getUser(fromUserId);
        Book book = bookDataSource.getBookByISBN(isbn);

        if (fromUser != null && book != null) {
            if (isLike) {
                if (fromUser.getLikeBooks().contains(isbn)) {
                    return false;
                }
                fromUser.addLikeBook(isbn);
                book.setLikedCount(book.getLikedCount() + 1);

            } else {
                if (!fromUser.getLikeBooks().contains(isbn)) {
                    return false;
                }
                fromUser.removeLikeBook(isbn);
                book.setLikedCount(book.getLikedCount() - 1);
            }

            // Update the data sources
            userDataSource.updateUser(fromUser);
            bookDataSource.setLikeCount(isbn, book.getLikedCount());
        }
        return true;
    }

    /**
     * Current session user follows another user
     *
     * @param userId the id of the user to follow
     * @param isFollow true if the user is to be followed, false if the user is to be unfollowed
     * @return true
     */
    @Override
    public boolean followUser(String userId, boolean isFollow) {
        String currentUserId = userSession.getUser().getId();
        return followUser(userId, isFollow, currentUserId);

    }

    /**
     * User follows another user
     *
     * @param userId the id of the user to follow
     * @param isFollow true if the user is to be followed, false if the user is to be unfollowed
     * @param fromUserId the id of the user who follows another user
     * @return true
     */
    @Override
    public boolean followUser(String userId, boolean isFollow, String fromUserId) {
        User fromUser = userDataSource.getUser(fromUserId);
        User user = userDataSource.getUser(userId);
        if (fromUser != null && user != null) {
            if (isFollow) {
                if (fromUser.getFollowing().contains(userId)) {
                    return false;
                }
                fromUser.addFollow(userId);
                user.addFan(fromUserId);
            } else {
                if (!fromUser.getFollowing().contains(userId)) {
                    return false;
                }
                fromUser.removeFollow(userId);
                user.removeFan(fromUserId);
            }
            // Update the user data source
            userDataSource.updateUser(fromUser);
            userDataSource.updateUser(user);
        }
        return true;
    }


    /**
     * Current session user borrows a book
     *
     * @param isbn the isbn of the book to borrow
     * @return true
     */
    public boolean borrowBook(long isbn) {
        User currentUser = userSession.getUser();
        return borrowBook(isbn, currentUser.getId());
    }

    /**
     * User borrows a book
     *
     * @param isbn the isbn of the book to borrow
     * @param fromUserId the id of the user who borrows the book
     * @return true
     */
    public boolean borrowBook(long isbn, String fromUserId) {
        return bookDataSource.borrowBook(isbn, fromUserId);
    }


    /**
     * Current session user returns a book
     *
     * @param isbn the isbn of the book to return
     * @return true
     */
    public boolean returnBook(long isbn) {
        User currentUser = userSession.getUser();
        return returnBook(isbn, currentUser.getId());
    }

    /**
     * User returns a book
     *
     * @param isbn the isbn of the book to return
     * @param fromUserId the id of the user who returns the book
     * @return true
     */
    public boolean returnBook(long isbn, String fromUserId) {
        return bookDataSource.returnBook(isbn, fromUserId);
    }

    /**
     * Current session user logs out
     *
     * @return true
     */
    @Override
    public boolean logout() {
        userSession.setUserState(new LogoutState(userSession));
        return true;
    }
}
