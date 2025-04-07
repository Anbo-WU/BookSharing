package anu.g35.sharebooks.data.simulate;

import anu.g35.sharebooks.data.model.UserAction;

/**
 * Defines what an observer must have
 *
 * @author u7703248 Chuang Ma
 * @since 2024-04-19
 */
public interface Observer {
    public void receiveAction(UserAction action, Subject from);
}
