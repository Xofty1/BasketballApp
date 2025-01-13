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

import com.basketballapp.R;
import com.basketballapp.databinding.FragmentProfileBinding;
import com.basketballapp.presentation.auth.AuthActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FragmentProfileBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            binding.tvUsername.setText(currentUser.getEmail());
            binding.btnLogout.setVisibility(View.VISIBLE);
            binding.btnLogout.setOnClickListener(v -> {
                mAuth.signOut(); // Выход из аккаунта
                Toast.makeText(getActivity(), "Вы вышли из профиля", Toast.LENGTH_SHORT).show();

                // Переход на экран авторизации
                Intent intent = new Intent(getActivity(), AuthActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();
            });
        } else {
            binding.tvUsername.setText("Пользователь не зарегистрирован");
            binding.btnLogout.setVisibility(View.GONE);
        }
        binding.btnAboutApp.setOnClickListener(v -> {
            // Переход к информации о приложении
            showAboutApp();
        });

        binding.btnAboutCreator.setOnClickListener(v -> {
            // Переход к информации о создателе
            showAboutCreator();

        });


        return binding.getRoot();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showAboutApp() {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new AboutAppFragment())
                .addToBackStack(null)
                .commit();
    }

    private void showAboutCreator() {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new AboutCreatorFragment())
                .addToBackStack(null)
                .commit();
    }
}
