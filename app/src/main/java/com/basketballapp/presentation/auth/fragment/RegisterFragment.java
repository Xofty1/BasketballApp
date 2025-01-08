package com.basketballapp.presentation.auth.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.basketballapp.databinding.FragmentRegisterBinding;
import com.basketballapp.presentation.auth.AuthActivity;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FragmentRegisterBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        mAuth = FirebaseAuth.getInstance();

        // Установка слушателей для кнопок и полей ввода
        binding.registerButton.setOnClickListener(v -> {
            String email = binding.registerEmail.getText().toString().trim();
            String password = binding.registerPassword.getText().toString().trim();
            String confirmPassword = binding.confirmPassword.getText().toString().trim();
            if (!password.equals(confirmPassword)) {
                Toast.makeText(getActivity(), "Пароли должны совпадать", Toast.LENGTH_SHORT).show();
            } else {
                if (!email.isEmpty() && !password.isEmpty()) {
                    if (password.length() >= 6) {
                        mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getActivity(), "Регистрация успешна", Toast.LENGTH_SHORT).show();
                                        if (getActivity() instanceof AuthActivity) {
                                            ((AuthActivity) getActivity()).switchToLoginFragment();
                                        }
                                    } else {
                                        Toast.makeText(getActivity(), "Ошибка регистрации: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(getActivity(), "Пароль должен быть не менее 6 символов", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Введите все данные", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.loginLink.setOnClickListener(v -> {
            if (getActivity() instanceof AuthActivity) {
                ((AuthActivity) getActivity()).switchToLoginFragment();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
