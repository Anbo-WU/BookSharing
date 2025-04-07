package anu.g35.sharebooks.data.simulate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import anu.g35.sharebooks.data.datasource.UserActionDataSource;
import anu.g35.sharebooks.data.model.UserAction;

/**
 * Use the Observer design pattern
 * The SimulateSubject class implements the Subject interface.
 * Observers can register themselves to this Subject and will be notified when the Subject's state changes.
 * <p>
 * It also uses the Singleton design pattern to ensure that
 * there is only one instance of SimulateSubject throughout the application.
 *
 * @author u7703248 Chuang Ma
 * @since 2024-04-19
 */
public class SimulateSubject implements Subject {
    private List<Observer> observers = new ArrayList<>();

    private static SimulateSubject instance = null;

    private static List<UserAction> userActions;
    private Timer timer;

    // The period of time between each notification
    private final int PERIOD = 5000;

    // The index of the current user action
    private int actionIndex = 0;

    /**
     * Private constructor that can only be accessed within the class.
     * This is a part of the Singleton design pattern which ensures
     * that only one instance of this class can be created.
     * <p>
     * It initializes the list of user actions and a timer that notifies all observers every 3 seconds.
     */
    private SimulateSubject() {
        userActions = UserActionDataSource.getInstance().getUserActions();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                UserAction userAction = makeAction();
                notifyObservers(userAction);
            }
        }, 0, PERIOD);
    }

    private UserAction makeAction() {

        UserAction userAction = userActions.get(actionIndex);
        userAction.setTimestamp(LocalDateTime.now());

        actionIndex++;
        if (actionIndex >= userActions.size()) {
            actionIndex = 0;
        }

        return userAction;
    }

    /**
     * This is a part of the Singleton design pattern which ensures
     * that only one instance of this class can be created.
     *
     * @return instance
     */
    public static SimulateSubject getInstance() {
        if (instance == null) {
            instance = new SimulateSubject();
        }
        return instance;
    }

    /**
     * Register an observer to the list of observers.
     *
     * @param observer the observer to be registered
     */
    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Remove an observer from the list of observers.
     *
     * @param observer the observer to be removed
     */
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Method to notify all observers, sending the current user action to all observers.
     */
    @Override
    public void notifyObservers(UserAction userAction) {

        for (Observer observer : observers) {
            observer.receiveAction(userAction, this);
        }
    }

}
