package com.basketballapp.domain.model;

import android.os.Parcel;
import android.os.Parcelable;


public class Game implements Parcelable {
    private String date;
    private String homeTeam;
    private String awayTeam;
    private String score;
    private String homeTeamImage;
    private String awayTeamImage;

    private String gameStartTime;
    private String attendance;
    private String gameDuration;
    private String arenaName;

    // Конструктор
    public Game(String date, String homeTeam, String awayTeam, String score, String homeTeamImage, String awayTeamImage,
                String gameStartTime, String attendance, String gameDuration, String arenaName) {
        this.date = date;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.score = score;
        this.homeTeamImage = homeTeamImage;
        this.awayTeamImage = awayTeamImage;
        this.gameStartTime = gameStartTime;
        this.attendance = attendance;
        this.gameDuration = gameDuration;
        this.arenaName = arenaName;
    }

    // Геттеры и сеттеры
    public String getDate() {
        return date;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public String getScore() {
        return score;
    }

    public String getHomeTeamImage() {
        return homeTeamImage;
    }

    public String getAwayTeamImage() {
        return awayTeamImage;
    }

    public String getGameStartTime() {
        return gameStartTime;
    }

    public String getAttendance() {
        return attendance;
    }

    public String getGameDuration() {
        return gameDuration;
    }

    public String getArenaName() {
        return arenaName;
    }

    // Реализация Parcelable
    protected Game(Parcel in) {
        date = in.readString();
        homeTeam = in.readString();
        awayTeam = in.readString();
        score = in.readString();
        homeTeamImage = in.readString();
        awayTeamImage = in.readString();
        gameStartTime = in.readString();
        attendance = in.readString();
        gameDuration = in.readString();
        arenaName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(homeTeam);
        dest.writeString(awayTeam);
        dest.writeString(score);
        dest.writeString(homeTeamImage);
        dest.writeString(awayTeamImage);

        dest.writeString(gameStartTime);
        dest.writeString(attendance);
        dest.writeString(gameDuration);
        dest.writeString(arenaName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Game> CREATOR = new Creator<>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };
}
