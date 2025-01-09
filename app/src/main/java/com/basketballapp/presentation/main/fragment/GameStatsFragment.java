package com.basketballapp.presentation.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.basketballapp.R;
import com.basketballapp.databinding.FragmentGameStatsBinding;
import com.basketballapp.domain.model.Game;
import com.bumptech.glide.Glide;

public class GameStatsFragment extends Fragment {

    private static final String ARG_GAME = "game";

    private Game game;

    FragmentGameStatsBinding binding;

    public static GameStatsFragment newInstance(Game game) {
        GameStatsFragment fragment = new GameStatsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_GAME, game);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            game = getArguments().getParcelable(ARG_GAME);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentGameStatsBinding.inflate(inflater);

        if (game != null) {
            binding.tvDate.setText(game.getDate());
            binding.tvHomeTeam.setText(game.getHomeTeam());
            binding.tvAwayTeam.setText(game.getAwayTeam());
            binding.tvScore.setText(game.getScore());

            binding.tvGameStartTime.setText(game.getGameStartTime());
            binding.tvAttendance.setText(game.getAttendance());
            binding.tvGameDuration.setText(game.getGameDuration());
            binding.tvArenaName.setText(game.getArenaName());

            Glide.with(requireContext())
                    .load(game.getHomeTeamImage())
                    .into(binding.ivHomeTeamLogo);

            Glide.with(requireContext())
                    .load(game.getAwayTeamImage())
                    .into(binding.ivAwayTeamLogo);


            binding.homeTeam.setOnClickListener(v -> {
                TeamStatsFragment teamStatsFragment = TeamStatsFragment.newInstance(game.getHomeTeam(), Integer.parseInt((game.getDate().split(", ")[2])), game.getHomeTeamImage());
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, teamStatsFragment)
                        .addToBackStack(null)
                        .commit();
            });

            binding.awayTeam.setOnClickListener(v -> {
                TeamStatsFragment teamStatsFragment = TeamStatsFragment.newInstance(game.getAwayTeam(), Integer.parseInt((game.getDate().split(", ")[2])), game.getAwayTeamImage());
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, teamStatsFragment)
                        .addToBackStack(null)
                        .commit();
            });
        }

        return binding.getRoot();
    }
}
