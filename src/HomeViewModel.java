package anu.g35.sharebooks.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import anu.g35.sharebooks.data.model.UserAction;
import anu.g35.sharebooks.data.simulate.Observer;
import anu.g35.sharebooks.data.simulate.SimulateSubject;

public class HomeViewModel extends ViewModel implements Observer {

    private final MutableLiveData<String> mText;

    public HomeViewModel() {

        // Register this observer // tmp testing
        SimulateSubject.getInstance().registerObserver(this);

        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    @Override
    public void receiveAction(UserAction action) {

        // tmp testing
        String actionString = action.toString();
        mText.postValue(actionString);
    }
}