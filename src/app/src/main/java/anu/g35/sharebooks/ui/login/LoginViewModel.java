package anu.g35.sharebooks.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import anu.g35.sharebooks.data.model.Result;
import anu.g35.sharebooks.R;
import anu.g35.sharebooks.data.model.User;
import anu.g35.sharebooks.data.session.UserSession;

/**
 * ViewModel for the login screen
 *
 * @author u7703248 Chuang Ma
 * @since 2024-04-19
 */
public class LoginViewModel extends ViewModel {

    private MutableLiveData<Result> loginResult = new MutableLiveData<>();
    private UserSession userSession = UserSession.getInstance();


    LiveData<Result> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        Result<User> result = userSession.login(username, password);
        loginResult.setValue(result);
    }

}