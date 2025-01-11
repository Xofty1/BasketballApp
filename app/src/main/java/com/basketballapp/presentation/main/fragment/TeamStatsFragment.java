package com.basketballapp.presentation.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.basketballapp.databinding.FragmentTeamStatsBinding;
import com.basketballapp.datasource.network.WebScraper;
import com.basketballapp.domain.model.TeamStats;
import com.bumptech.glide.Glide;

public class TeamStatsFragment extends Fragment {

    private static final String ARG_TEAM = "team";
    private static final String ARG_YEAR = "year";
    private static final String ARG_LOGO = "logo";
    private String teamName;
    private String teamLogo;

    private FragmentTeamStatsBinding binding;
    private int year;

    public static TeamStatsFragment newInstance(String teamName, int year, String teamLogo) {
        TeamStatsFragment fragment = new TeamStatsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TEAM, teamName);
        args.putInt(ARG_YEAR, year);
        args.putString(ARG_LOGO, teamLogo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            teamName = getArguments().getString(ARG_TEAM);
            year = getArguments().getInt(ARG_YEAR);
            teamLogo = getArguments().getString(ARG_LOGO);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTeamStatsBinding.inflate(inflater);


        fetchTeams(year);

        return binding.getRoot();
    }

    public void fetchTeams(int year) {
        // Вызов WebScraper для получения данных
        WebScraper webScraper = new WebScraper();
        webScraper.fetchTeamStats(year, WebScraper.getTeamCode(teamName), new WebScraper.TeamStatsCallback<>() {
            @Override
            public void onResult(TeamStats teamStats) {
                // Успешно загруженные данные
                updateUI(teamStats);
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }


    private void updateUI(TeamStats teamStats) {
        binding.tvSeason.setText("Сезон: " + (year - 1) + " - " + year);
        binding.tvTeamName.setText("Команда: " + teamName);
        Glide.with(requireContext())
                .load(teamLogo)
                .into(binding.ivTeamLogo);
        binding.tvRecord.setText(getReadyData("Рекорд: ", teamStats.getRecord()));
        binding.tvRecord.setText(getReadyData("Рекорд: ", teamStats.getRecord()));
        binding.tvCoach.setText(getReadyData("Тренер: ", teamStats.getCoach()));
        binding.tvExecutive.setText(getReadyData("Генеральный менеджер: ", teamStats.getExecutive()));
        binding.tvArena.setText(getReadyData("Арена: ", teamStats.getArena()));
        binding.tvAttendance.setText(getReadyData("Вместимость арены: ", teamStats.getAttendance()));
        binding.tvPointsPerGame.setText(getReadyData("Забитые очки за игру: ", teamStats.getPointsPerGame()));
        binding.tvOpponentPointsPerGame.setText(getReadyData("Пропущенные очки за игру: ", teamStats.getOpponentPointsPerGame()));
        binding.tvSrs.setText(getReadyData("Рейтинг: ", teamStats.getSrs()));
        binding.tvPace.setText(getReadyData("Владение мячом: ", teamStats.getPace()));
    }


    private String getReadyData(String dataType, String data) {
        if (data == null) return dataType + "Нет данных";
        return dataType + data;

    }
}