package anu.g35.sharebooks.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import anu.g35.sharebooks.MainActivity;
import anu.g35.sharebooks.R;
import anu.g35.sharebooks.data.model.Result;
import anu.g35.sharebooks.data.model.User;
import anu.g35.sharebooks.databinding.ActivityLoginBinding;

/**
 * LoginActivity is the activity for user login
 *
 * @author u7703248 Chuang Ma
 * @since 2024-04-19
 */
public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;

        loginViewModel.getLoginResult().observe(this, new Observer<Result>() {
            @Override
            public void onChanged(@Nullable Result loginResult) {
                if (loginResult == null) {
                    return;
                }
                if (loginResult instanceof Result.Error) {
                    Exception error = ((Result.Error) loginResult).getError();
                    showLoginFailed(error.getMessage());
                }
                if (loginResult instanceof Result.Success) {
                    User user = ((Result.Success<User>) loginResult).getData();
                    updateUi(user);
                    finish();
                }
                setResult(Activity.RESULT_OK);
            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
    }

    private void updateUi(User user) {
        String welcome = getString(R.string.welcome) + user.getName();

        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();

        // Create an Intent to start MainActivity
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void showLoginFailed(String errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}