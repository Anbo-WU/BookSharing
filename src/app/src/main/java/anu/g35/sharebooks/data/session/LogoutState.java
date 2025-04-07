package anu.g35.sharebooks.data.session;

import anu.g35.sharebooks.data.model.User;

/**
 * The state of the user session when the user is logged out
 *
 * @author u7703248 Chuang Ma
 * @since 2024-04-25
 */
public class LogoutState extends UserState {
    public LogoutState(UserSession userSession) {
        super(userSession);
    }

    /**
     * Authenticates the user
     *
     * @param username the username
     * @param password the password
     * @return the user
     */
    @Override
    public User login(String username, String password) {
        User user = userDataSource.login(username, password);
        if (user != null) {
            userSession.setUser(user);
            userSession.setUserState(new LoginState(userSession));
            return user;
        }
        return null;
    }


    @Override
    public boolean likeBook(long isbn, boolean isLike) {
        return false;
    }

    @Override
    public boolean likeBook(long isbn, boolean isLike, String fromUserId) {
        return false;
    }

    @Override
    public boolean followUser(String userId, boolean isFollow) {
        return false;
    }

    @Override
    public boolean followUser(String userId, boolean isFollow, String fromUserId) {
        return false;
    }

    public boolean borrowBook(long isbn) {
        return false;
    }

    public boolean borrowBook(long isbn, String fromUserId) {
        return false;
    }

    public boolean returnBook(long isbn) {
        return false;
    }

    public boolean returnBook(long isbn, String fromUserId) {
        return false;
    }
    @Override
    public boolean logout() {
        return false;
    }
}
