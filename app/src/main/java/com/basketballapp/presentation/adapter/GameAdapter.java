package com.basketballapp.presentation.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.basketballapp.R;
import com.basketballapp.domain.model.Game;
import com.basketballapp.presentation.main.fragment.GameStatsFragment;
import com.bumptech.glide.Glide;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {

    private final List<Game> gameList;
    private final FragmentManager fragmentManager;

    public GameAdapter(List<Game> gameList, FragmentManager fragmentManager) {
        this.gameList = gameList;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_game, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        Game game = gameList.get(position);

        holder.tvDate.setText(game.getDate());
        holder.tvHomeTeam.setText(game.getHomeTeam());
        holder.tvAwayTeam.setText(game.getAwayTeam());
        holder.tvScore.setText(game.getScore());
        Log.d("TAG52", game.getHomeTeamImage());
        // Загрузка изображений логотипов команд
        Glide.with(holder.itemView.getContext())
                .load(game.getHomeTeamImage())
                .into(holder.ivHomeTeamLogo);

        Glide.with(holder.itemView.getContext())
                .load(game.getAwayTeamImage())
                .into(holder.ivAwayTeamLogo);
        holder.itemView.setOnClickListener(v -> {
            GameStatsFragment gameStatsFragment = GameStatsFragment.newInstance(game);

            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, gameStatsFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    public static class GameViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvHomeTeam, tvScore, tvAwayTeam;
        ImageView ivHomeTeamLogo, ivAwayTeamLogo;

        public GameViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tvDate);
            tvHomeTeam = itemView.findViewById(R.id.tvHomeTeam);
            tvAwayTeam = itemView.findViewById(R.id.tvAwayTeam);
            tvScore = itemView.findViewById(R.id.tvScore);
            ivHomeTeamLogo = itemView.findViewById(R.id.ivHomeTeamLogo);
            ivAwayTeamLogo = itemView.findViewById(R.id.ivAwayTeamLogo);
        }
    }
}
