package com.basketballapp.presentation.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.basketballapp.R;
import com.basketballapp.presentation.auth.fragment.LoginFragment;
import com.basketballapp.presentation.auth.fragment.RegisterFragment;
import com.basketballapp.presentation.main.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class AuthActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        mAuth = FirebaseAuth.getInstance();

        // Слушатель состояния авторизации
        mAuthListener = firebaseAuth -> {
            if (firebaseAuth.getCurrentUser() != null) {
                // Пользователь уже авторизован, перенаправляем на главный экран
                startActivity(new Intent(AuthActivity.this, MainActivity.class));
                finish();
            }
        };

        // Загружаем LoginFragment по умолчанию
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new LoginFragment()).addToBackStack(null)
                    .commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void switchToRegisterFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new RegisterFragment())
                .addToBackStack(null)
                .commit();
    }

    public void switchToLoginFragment() {
        getSupportFragmentManager().popBackStack();
    }
}
