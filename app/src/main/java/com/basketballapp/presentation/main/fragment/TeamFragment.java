package com.basketballapp.presentation.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.basketballapp.databinding.FragmentTeamBinding;
import com.basketballapp.domain.model.Team;
import com.basketballapp.domain.repository.GameRepository;
import com.basketballapp.domain.repository.callback.RepositoryCallback;
import com.basketballapp.presentation.adapter.TeamAdapter;
import com.basketballapp.presentation.adapter.TimeAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeamFragment extends Fragment {
    private List<Team> teamList = new ArrayList<>();
    private GameRepository gameRepository;
    private TeamAdapter adapter;
    private FragmentTeamBinding binding;
    String TAG = "TAG52";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameRepository = new GameRepository();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTeamBinding.inflate(inflater);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new TeamAdapter(teamList);
        binding.recyclerView.setAdapter(adapter);

        gameRepository = new GameRepository();
        List<Integer> years = Arrays.asList(2020, 2021, 2022, 2023, 2024);

        // Устанавливаем адаптер для списка годов
        TimeAdapter yearAdapter = new TimeAdapter(years, this::fetchTeams);
        binding.yearsRecyclerView.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        );

        binding.yearsRecyclerView.setAdapter(yearAdapter);
        return binding.getRoot();
    }


    private void fetchTeams(int year) {
        gameRepository.fetchTeams(year, new RepositoryCallback<>() {
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
}