package anu.g35.sharebooks.data.session;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import anu.g35.sharebooks.data.model.Result;
import anu.g35.sharebooks.data.model.User;
import anu.g35.sharebooks.data.model.UserAction;
import anu.g35.sharebooks.data.simulate.Observer;
import anu.g35.sharebooks.data.simulate.Subject;
import anu.g35.sharebooks.exceptions.UserNotFoundException;

/**
 * The user session is a singleton
 *
 * @author u7703248 Chuang Ma
 * @since 2024-04-25
 */
public class UserSession implements Subject {
    private static UserSession instance = null;

    private final List<Observer> observers = new ArrayList<>();

    // current state of the session
    private UserState userState;
    // user on session
    private User user;


    /**
     * Constructor
     * The initial state is logout
     */
    private UserSession() {
        // initial state is logout
        userState = new LogoutState(this);
    }


    private UserAction makeAction() {
        UserAction userAction = new UserAction();
        userAction.setUserId(user.getId());
        userAction.setTimestamp(LocalDateTime.now());
        return userAction;
    }

    /**
     * Returns the instance of the UserSession
     * @return the instance of the UserSession
     */
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    /**
     * Authenticates the user
     * @param userId the user id
     * @param password the password
     * @return the user
     */
    public Result<User> login(String userId, String password) {
        user = userState.login(userId, password);
        if (user != null) {
            return new Result.Success<>(user);
        }
        return new Result.Error<>(new UserNotFoundException("Oops! Invalid username or password :("));
    }

    /**
     * Logs out the user
     * @return true if the user is logged out successfully
     */
    public boolean logout() {
        boolean logoutSuccess = userState.logout();
        if (logoutSuccess) {
            user = null;
        }
        return logoutSuccess;
    }

    /**
     * Current session user likes a book
     * @param isbn the isbn of the book to like
     * @param isLike true if the book is to be liked, false if the book is to be unliked
     * @return true if the book is liked successfully
     */
    public boolean likeBook(long isbn, boolean isLike) {
        boolean result = userState.likeBook(isbn, isLike);
        if (result) {
            UserAction userAction = makeAction();
            if (isLike) {
                userAction.setActionType(UserAction.Type.LIKE);
            } else {
                userAction.setActionType(UserAction.Type.DISLIKE);
            }
            userAction.setAtBookISBN(isbn);
            notifyObservers(userAction);
        }

        return result;
    }

    /**
     * Current session user follows another user
     * @param userId  the id of the user to follow
     * @param isFollow true if the user is to be followed, false if the user is to be unfollowed
     * @return true if the user is followed successfully
     */
    public boolean followUser(String userId, boolean isFollow) {
        boolean result = userState.followUser(userId, isFollow);
        if (result) {
            UserAction userAction = makeAction();
            if (isFollow) {
                userAction.setActionType(UserAction.Type.FOLLOW);
            } else {
                userAction.setActionType(UserAction.Type.UNFOLLOW);
            }
            userAction.setAtUserId(userId);
            notifyObservers(userAction);
        }
        return result;
    }

    /**
     * Current session user borrows a book
     * @param isbn the isbn of the book to borrow
     * @return true if the book is borrowed successfully
     */
    public boolean borrowBook(long isbn) {
        boolean result = userState.borrowBook(isbn);
        if (result) {
            UserAction userAction = makeAction();
            userAction.setActionType(UserAction.Type.BORROW);
            userAction.setAtBookISBN(isbn);
            notifyObservers(userAction);
        }
        return result;
    }

    /**
     * Current session user returns a book
     * @param isbn the isbn of the book to return
     * @return true if the book is returned successfully
     */
    public boolean returnBook(long isbn) {
        boolean result = userState.returnBook(isbn);
        if (result) {
            UserAction userAction = makeAction();
            userAction.setActionType(UserAction.Type.RETURN);
            userAction.setAtBookISBN(isbn);
            notifyObservers(userAction);
        }
        return result;
    }

    /**
     * Sets the user state
     * @param userState the user state
     */
    public void setUserState(UserState userState) {
        this.userState = userState;
    }

    /**
     * Returns the user
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Returns the user state
     * @return the user state
     */
    public UserState getUserState() {
        return userState;
    }

    /**
     * Returns true if the user is logged in
     * @return true if the user is logged in
     */
    public boolean isLogin() {
        return userState instanceof LoginState;
    }


    /**
     * Registers an observer
     * @param observer the observer
     */
    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer
     * @param observer the observer
     */
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all observers
     */
    @Override
    public void notifyObservers(UserAction userAction) {
        for (Observer observer : observers) {
            observer.receiveAction(userAction, this);
        }
    }

}
