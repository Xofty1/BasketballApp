package com.basketballapp.presentation.main.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.basketballapp.R;
import com.basketballapp.databinding.FragmentTeamBinding;
import com.basketballapp.domain.model.Team;
import com.basketballapp.domain.repository.GameRepository;
import com.basketballapp.domain.repository.callback.RepositoryCallback;
import com.basketballapp.presentation.adapter.TeamAdapter;
import com.basketballapp.presentation.adapter.TimeAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TeamFragment extends Fragment {
    private List<Team> teamList = new ArrayList<>();
    private GameRepository gameRepository;
    private TeamAdapter adapter;
    private FragmentTeamBinding binding;
    private boolean isE = true;
    private int selectedYear; // Текущий выбранный год

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameRepository = new GameRepository();
        selectedYear = Calendar.getInstance().get(Calendar.YEAR); // Устанавливаем текущий год по умолчанию
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTeamBinding.inflate(inflater);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Определяем текущую ориентацию
        boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        // Передаём ориентацию в адаптер
        adapter = new TeamAdapter(teamList, isPortrait);
        binding.recyclerView.setAdapter(adapter);

        // Устанавливаем видимость полей в зависимости от ориентации
        if (!isPortrait) {
            binding.teamWinLossPct.setVisibility(View.VISIBLE);
            binding.teamGb.setVisibility(View.VISIBLE);
            binding.teamOppPtsPerGame.setVisibility(View.VISIBLE);
        } else {
            binding.teamWinLossPct.setVisibility(View.GONE);
            binding.teamGb.setVisibility(View.GONE);
            binding.teamOppPtsPerGame.setVisibility(View.GONE);
        }

        // Обработчики кнопок конференций
        binding.standingE.setOnClickListener(v -> {
            isE = true; // Устанавливаем текущую конференцию
            updateButtonStates(); // Обновляем фоны кнопок
            fetchTeams(selectedYear); // Загружаем данные
        });

        binding.standingW.setOnClickListener(v -> {
            isE = false; // Устанавливаем текущую конференцию
            updateButtonStates(); // Обновляем фоны кнопок
            fetchTeams(selectedYear); // Загружаем данные
        });

// Метод для обновления состояния кнопок


        // Инициализация данных
        gameRepository = new GameRepository();
        List<Integer> years = new ArrayList<>();
        for (int i = 2025; i > 2015; i--) {
            years.add(i);
        }
        fetchTeams(selectedYear);

        // Адаптер для списка годов
        TimeAdapter yearAdapter = new TimeAdapter(years, year -> {
            selectedYear = year; // Обновляем выбранный год
            fetchTeams(selectedYear); // Загружаем данные для нового года
        });
        binding.yearsRecyclerView.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        );

        binding.yearsRecyclerView.setAdapter(yearAdapter);
        return binding.getRoot();
    }

    private void fetchTeams(int year) {
        gameRepository.fetchTeams(year, isE, new RepositoryCallback<>() {
            @Override
            public void onSuccess(List<Team> games) {
                teamList.clear();
                teamList.addAll(games);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String errorMessage) {
                // Обработка ошибки
            }
        });
    }

    private void updateButtonStates() {
        if (isE) {
            binding.standingE.setBackgroundResource(R.drawable.time_background_selected);
            binding.standingW.setBackgroundResource(R.drawable.time_background);
        } else {
            binding.standingE.setBackgroundResource(R.drawable.time_background);
            binding.standingW.setBackgroundResource(R.drawable.time_background_selected);
        }
    }
}

