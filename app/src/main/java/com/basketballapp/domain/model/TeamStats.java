package com.basketballapp.domain.model;

public class TeamStats {
    private String record;
    private String coach;
    private String arena;
    private String attendance;
    private String pointsPerGame;
    private String opponentPointsPerGame;
    private String srs;
    private String pace;
    private String executive;

    public TeamStats() {
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    public String getPace() {
        return pace;
    }

    public void setPace(String pace) {
        this.pace = pace;
    }

    public String getExecutive() {
        return executive;
    }

    public void setExecutive(String executive) {
        this.executive = executive;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public String getArena() {
        return arena;
    }

    public void setArena(String arena) {
        this.arena = arena;
    }

    public String getPointsPerGame() {
        return pointsPerGame;
    }

    public void setPointsPerGame(String pointsPerGame) {
        this.pointsPerGame = pointsPerGame;
    }

    public String getOpponentPointsPerGame() {
        return opponentPointsPerGame;
    }

    public void setOpponentPointsPerGame(String opponentPointsPerGame) {
        this.opponentPointsPerGame = opponentPointsPerGame;
    }

    public String getSrs() {
        return srs;
    }

    public void setSrs(String srs) {
        this.srs = srs;
    }

    @Override
    public String toString() {
        return "Team{" +
                "record='" + record + '\'' +
                ", coach='" + coach + '\'' +
                ", arena='" + arena + '\'' +
                ", pointsPerGame='" + pointsPerGame + '\'' +
                ", opponentPointsPerGame='" + opponentPointsPerGame + '\'' +
                ", srs='" + srs + '\'' +
                '}';
    }
}
