package com.basketballapp.presentation.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.basketballapp.R;
import com.basketballapp.databinding.ActivityMainBinding;
import com.basketballapp.presentation.main.fragment.GameFragment;
import com.basketballapp.presentation.main.fragment.ProfileFragment;
import com.basketballapp.presentation.main.fragment.TeamFragment;

public class MainActivity extends AppCompatActivity {
    Fragment selectedFragment;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState != null) {
            // Восстанавливаем выбранный фрагмент
            selectedFragment = getSupportFragmentManager().getFragment(savedInstanceState, "selectedFragment");
        } else {
            // По умолчанию устанавливаем TeamFragment
            selectedFragment = new TeamFragment();
        }

        // Восстанавливаем фрагменты из бэкстека
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            // Если есть фрагменты в бэкстеке, восстанавливаем последний
            selectedFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, selectedFragment)
                .commit();

        // Обработчик изменения пунктов в нижнем меню
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            // Проверяем, какой фрагмент нужно заменить
            if (id == R.id.nav_games && selectedFragment.getClass() != GameFragment.class) {
                selectedFragment = new GameFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, selectedFragment)
                        .addToBackStack(null)
                        .commit();
            } else if (id == R.id.nav_teams && selectedFragment.getClass() != TeamFragment.class) {
                selectedFragment = new TeamFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, selectedFragment)
                        .addToBackStack(null)
                        .commit();
            } else if (id == R.id.nav_profile && selectedFragment.getClass() != ProfileFragment.class) {
                selectedFragment = new ProfileFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, selectedFragment)
                        .addToBackStack(null)
                        .commit();
            }

            return true;
        });

        // Обработка возвращения назад (по бэкстеку)
        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
            if (currentFragment instanceof GameFragment) {
                binding.bottomNavigationView.setSelectedItemId(R.id.nav_games);
            } else if (currentFragment instanceof TeamFragment) {
                binding.bottomNavigationView.setSelectedItemId(R.id.nav_teams);
            } else if (currentFragment instanceof ProfileFragment) {
                binding.bottomNavigationView.setSelectedItemId(R.id.nav_profile);
            } else {
                binding.bottomNavigationView.setSelectedItemId(-1);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (currentFragment != null) {
            getSupportFragmentManager().putFragment(outState, "selectedFragment", currentFragment);
        }
    }
}
