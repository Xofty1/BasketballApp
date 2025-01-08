package com.basketballapp.presentation.auth.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.basketballapp.R;
import com.basketballapp.databinding.FragmentLoginBinding;
import com.basketballapp.presentation.auth.AuthActivity;
import com.basketballapp.presentation.main.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FragmentLoginBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mAuth = FirebaseAuth.getInstance();
        binding = FragmentLoginBinding.inflate(inflater);

        binding.loginButton.setOnClickListener(v -> {
            String email = binding.loginEmail.getText().toString().trim();
            String password = binding.loginPassword.getText().toString().trim();

            if (!email.isEmpty() && !password.isEmpty()) {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "Вход выполнен", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Ошибка входа: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(getActivity(), "Введите все данные", Toast.LENGTH_SHORT).show();
            }
        });

        binding.registerLink.setOnClickListener(v -> {
            if (getActivity() instanceof AuthActivity) {
                ((AuthActivity) getActivity()).switchToRegisterFragment();
            }
        });

        binding.guestLink.setOnClickListener(v ->
        {
            startActivity(new Intent(getActivity(), MainActivity.class));
            requireActivity().finish();
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
