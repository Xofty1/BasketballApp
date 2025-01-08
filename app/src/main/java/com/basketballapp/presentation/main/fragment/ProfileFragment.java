package com.basketballapp.presentation.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.basketballapp.databinding.FragmentProfileBinding;
import com.basketballapp.presentation.auth.AuthActivity;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FragmentProfileBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        mAuth = FirebaseAuth.getInstance();

        // Настраиваем кнопку выхода
        binding.logoutButton.setOnClickListener(v -> {
            mAuth.signOut(); // Выход из аккаунта
            Toast.makeText(getActivity(), "Вы вышли из профиля", Toast.LENGTH_SHORT).show();

            // Переход на экран авторизации
            Intent intent = new Intent(getActivity(), AuthActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            getActivity().finish();
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
