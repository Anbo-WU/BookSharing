package anu.g35.sharebooks.data.session;

import anu.g35.sharebooks.data.datasource.BookDataSource;
import anu.g35.sharebooks.data.datasource.UserDataSource;
import anu.g35.sharebooks.data.model.User;


/**
 * The abstract class for the state of the user session
 *
 * @author u7703248 Chuang Ma
 * @since 2024-04-25
 */
public abstract class UserState {
    protected UserSession userSession;
    protected UserDataSource userDataSource;

    protected BookDataSource bookDataSource;
    public UserState(UserSession userSession) {
        userDataSource = UserDataSource.getInstance();
        bookDataSource = BookDataSource.getInstance();
        this.userSession = userSession;
    }
    public abstract User login(String username, String password);
    public abstract boolean likeBook(long isbn, boolean isLike);
    public abstract boolean likeBook(long isbn, boolean isLike, String fromUserId);
    public abstract boolean followUser(String userId, boolean isFollow);
    public abstract boolean followUser(String userId, boolean isFollow, String fromUserId);
    public abstract boolean borrowBook(long isbn);
    public abstract boolean borrowBook(long isbn, String fromUserId);
    public abstract boolean returnBook(long isbn);
    public abstract boolean returnBook(long isbn, String fromUserId);
    public abstract boolean logout();
}
