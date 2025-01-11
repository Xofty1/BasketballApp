package com.basketballapp.presentation.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.basketballapp.databinding.FragmentGameBinding;
import com.basketballapp.domain.model.Game;
import com.basketballapp.domain.repository.GameRepository;
import com.basketballapp.domain.repository.callback.RepositoryCallback;
import com.basketballapp.presentation.adapter.GameAdapter;
import com.basketballapp.presentation.auth.AuthActivity;
import com.basketballapp.presentation.main.dialog.AuthDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GameFragment extends Fragment {
    private List<Game> gameList = new ArrayList<>();
    private GameRepository gameRepository;
    private GameAdapter adapter;
    private FragmentGameBinding binding;
    String TAG = "TAG52";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameRepository = new GameRepository();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentGameBinding.inflate(inflater);

        binding.recyclerViewGame.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new GameAdapter(gameList, getParentFragmentManager());
        binding.recyclerViewGame.setAdapter(adapter);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            binding.recyclerViewGame.setVisibility(View.GONE);
            AuthDialogFragment dialogFragment = new AuthDialogFragment();
            dialogFragment.show(requireActivity().getSupportFragmentManager(), "AuthDialog");
            binding.tvStatus.setVisibility(View.VISIBLE);
            binding.btnLogin.setVisibility(View.VISIBLE);
            binding.btnLogin.setOnClickListener(v -> {
                Intent intent = new Intent(requireContext(), AuthActivity.class);
                startActivity(intent);
                requireActivity().finish();
            });
        } else {
            binding.tvStatus.setVisibility(View.GONE);
            binding.recyclerViewGame.setVisibility(View.VISIBLE);
            fetchGames(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH) + 1);
        }


        setupSpinners();


        return binding.getRoot();
    }

    private void setupSpinners() {
        // Настройка Spinner для года
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        List<String> years = new ArrayList<>();
        for (int i = currentYear; i >= currentYear - 5; i--) {
            years.add((i - 1) + " - " + i);
        }
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, years);
        binding.spinnerYear.setAdapter(yearAdapter);
        // Настройка Spinner для месяца
        String[] months = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, months);
        binding.spinnerMonth.setAdapter(monthAdapter);

        // Обработка выбора года и месяца
        binding.spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void updateData() {
        String year = ((String) binding.spinnerYear.getSelectedItem()).split(" - ")[1];
        int selectedYear = Integer.parseInt(year);
        int selectedMonth = binding.spinnerMonth.getSelectedItemPosition() + 1;

        fetchGames(selectedYear, selectedMonth);
    }

    private void fetchGames(int year, int month) {
        gameRepository.fetchGames(year, month, new RepositoryCallback<>() {
            @Override
            public void onSuccess(List<Game> games) {


                // Фильтруем игры по году и месяцу
                gameList.clear();
                gameList.addAll(games);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String errorMessage) {
                // Обработка ошибки
            }
        });
    }
}