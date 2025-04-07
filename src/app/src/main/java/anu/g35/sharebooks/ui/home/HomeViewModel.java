package anu.g35.sharebooks.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import anu.g35.sharebooks.data.model.UserAction;
import anu.g35.sharebooks.data.session.UserSession;
import anu.g35.sharebooks.data.session.UserState;
import anu.g35.sharebooks.data.simulate.Observer;
import anu.g35.sharebooks.data.simulate.SimulateSubject;
import anu.g35.sharebooks.data.simulate.Subject;


/**
 * ViewModel for the chat room
 *
 * @Author Huizhe_Ruan, u7723366
 * @since 2024-04-28
 */
public class HomeViewModel extends ViewModel implements Observer {

    private final ArrayList<UserAction> userActions = new ArrayList<>();;
    private final MutableLiveData<List<UserAction>> liveUserActions = new MutableLiveData<>();
    private final UserSession userSession = UserSession.getInstance();

    /**
     * Constructor
     * Register this observer
     */
    public HomeViewModel() {

        // Register this observer
        SimulateSubject.getInstance().registerObserver(this);
        UserSession.getInstance().registerObserver(this);

    }

    /**
     * Get the live data of user actions
     *
     * @return the live data of user actions
     */
    public LiveData<List<UserAction>> getLiveUserActions() {
        return liveUserActions;
    }

    /**
     * Get the user actions
     *
     * @return the user actions
     */
    public ArrayList<UserAction> getUserActions() {
        return userActions;
    }

    /**
     * Receive the action
     *
     * @param action the action
     * @param from   the subject
     */
    @Override
    public void receiveAction(UserAction action, Subject from) {
        if (userSession.getUser() == null) {
            return;
        }
        if (from instanceof SimulateSubject) {
            if (action.getUserId().equals(userSession.getUser().getId())) {
                // ignore myself action from simulate subject
                return;
            }
            // Update the data source, If the action is from the simulate subject
            updateDataSource(action);
        }
        // update the live data
        userActions.add(action);
        liveUserActions.postValue(userActions);
    }

    /**
     * Send the message
     *
     * @param message the message
     */
    public void receiveMyMessage(String message) {
        UserAction newAction = new UserAction();
        newAction.setTimestamp(LocalDateTime.now());
        newAction.setUserId(UserSession.getInstance().getUser().getId());
        newAction.setActionType(UserAction.Type.SAY);
        newAction.setContent(message);

        userActions.add(newAction);
        liveUserActions.postValue(userActions);
    }

    /**
     * Update the data source, If the action is from the simulate subject
     *
     * @param action the action
     */
    private void updateDataSource(UserAction action) {
        if(action.getUserId().equals(userSession.getUser().getId())){
            return;
        }

        UserState userState = userSession.getUserState();
        switch (action.getActionType()) {

            case LIKE:
                userState.likeBook(action.getAtBookISBN(), true, action.getUserId());
            case DISLIKE:
                userState.likeBook(action.getAtBookISBN(), false, action.getUserId());
                break;
            case FOLLOW:
                userState.followUser(action.getAtUserId(), true, action.getUserId());
                break;
            case UNFOLLOW:
                userState.followUser(action.getAtUserId(), false, action.getUserId());
                break;
            case BORROW:
                userState.borrowBook(action.getAtBookISBN(), action.getUserId());
                break;
            case RETURN:
                userState.returnBook(action.getAtBookISBN(), action.getUserId());
                break;
            default:
                break;
        }

    }
}