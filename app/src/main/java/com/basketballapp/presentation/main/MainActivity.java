package com.basketballapp.presentation.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.basketballapp.R;
import com.basketballapp.presentation.main.fragment.GameFragment;
import com.basketballapp.presentation.main.fragment.ProfileFragment;
import com.basketballapp.presentation.main.fragment.TeamFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MatchListActivity";
    Fragment selectedFragment = new TeamFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Устанавливаем начальный фрагмент
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, selectedFragment)
                .commit();

        // Слушатель для выбора элементов в меню
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home && selectedFragment.getClass() != GameFragment.class) {
                selectedFragment = new GameFragment();
            } else if (id == R.id.nav_teams && selectedFragment.getClass() != TeamFragment.class) {
                selectedFragment = new TeamFragment();
            } else if (id == R.id.nav_profile && selectedFragment.getClass() != ProfileFragment.class) {
                selectedFragment = new ProfileFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, selectedFragment)
                        .commit();
            }
            return true;
        });
    }

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}