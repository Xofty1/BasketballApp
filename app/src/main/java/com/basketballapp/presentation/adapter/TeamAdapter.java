package com.basketballapp.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.basketballapp.R;
import com.basketballapp.domain.model.Team;

import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {

    private final List<Team> teamList;
    private final boolean isPortrait;

    public TeamAdapter(List<Team> teamList, boolean isPortrait) {
        this.teamList = teamList;
        this.isPortrait = isPortrait;
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_team, parent, false);
        return new TeamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {
        Team team = teamList.get(position);

        holder.teamName.setText(team.getName());
        holder.teamWins.setText(team.getWins());
        holder.teamLosses.setText(team.getLosses());
        holder.teamPtsPerGame.setText(team.getPtsPerGame());
        holder.teamSrs.setText(team.getSrs());

        if (!isPortrait) {
            // Если ориентация горизонтальная, показываем дополнительные поля
            holder.teamWinLossPct.setVisibility(View.VISIBLE);
            holder.teamGb.setVisibility(View.VISIBLE);
            holder.teamOppPtsPerGame.setVisibility(View.VISIBLE);

            holder.teamWinLossPct.setText(team.getWinLossPct());
            holder.teamGb.setText(team.getGb());
            holder.teamOppPtsPerGame.setText(team.getOppPtsPerGame());
        } else {
            // Если ориентация вертикальная, скрываем эти поля
            holder.teamWinLossPct.setVisibility(View.GONE);
            holder.teamGb.setVisibility(View.GONE);
            holder.teamOppPtsPerGame.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return teamList.size();
    }

    public static class TeamViewHolder extends RecyclerView.ViewHolder {
        TextView teamName, teamWins, teamLosses, teamWinLossPct, teamGb, teamPtsPerGame, teamOppPtsPerGame, teamSrs;

        public TeamViewHolder(@NonNull View itemView) {
            super(itemView);
            teamName = itemView.findViewById(R.id.teamName);
            teamWins = itemView.findViewById(R.id.teamWins);
            teamLosses = itemView.findViewById(R.id.teamLosses);
            teamWinLossPct = itemView.findViewById(R.id.teamWinLossPct);
            teamGb = itemView.findViewById(R.id.teamGb);
            teamPtsPerGame = itemView.findViewById(R.id.teamPtsPerGame);
            teamOppPtsPerGame = itemView.findViewById(R.id.teamOppPtsPerGame);
            teamSrs = itemView.findViewById(R.id.teamSrs);
        }
    }
}

