package com.basketballapp.domain.model;

public class Team {
    private String name;
    private String wins;
    private String losses;

    private String win_loss_pct;
    private String gb;
    private String pts_per_game;
    private String opp_pts_per_game;
    private String srs;

    public String getGb() {
        return gb;
    }

    public String getWinLossPct() {
        return win_loss_pct;
    }

    public String getPtsPerGame() {
        return pts_per_game;
    }

    public String getOppPtsPerGame() {
        return opp_pts_per_game;
    }

    public String getSrs() {
        return srs;
    }

    public Team(String name, String wins, String losses, String win_loss_pct, String gb,
                String pts_per_game, String opp_pts_per_game, String srs) {
        this.name = name;
        this.wins = wins;
        this.losses = losses;
        this.win_loss_pct = win_loss_pct;
        this.gb = gb;
        this.pts_per_game = pts_per_game;
        this.opp_pts_per_game = opp_pts_per_game;
        this.srs = srs;
    }

    public String getName() {
        return name;
    }

    public String getWins() {
        return wins;
    }

    public String getLosses() {
        return losses;
    }
}
