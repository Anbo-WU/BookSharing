package anu.g35.sharebooks.data.simulate;

import anu.g35.sharebooks.data.model.UserAction;

/**
 * Defines what a subject must have
 *
 * @author u7703248 Chuang Ma
 * @since 2024-04-19
 */
public interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(UserAction action);
}
