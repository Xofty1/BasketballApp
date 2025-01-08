package com.basketballapp.domain.model;

import java.util.HashMap;
import java.util.Map;

public class Game {
    private String date;
    private String homeTeam;
    private String awayTeam;
    private String score;
    private String homeTeamImage;
    private String awayTeamImage;


    public Game(String date, String homeTeam, String awayTeam, String score, String homeTeamImage, String awayTeamImage) {
        this.date = date;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.score = score;
        this.homeTeamImage = homeTeamImage;
        this.awayTeamImage = awayTeamImage;
    }

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

    @Override
    public String toString() {
        return date + ": " + homeTeam + " vs " + awayTeam + " (" + score + ")";
    }

    public Integer getYear() {
        String[] parts = date.split(", ");

        return Integer.valueOf(parts[2]);
    }

    public Integer getMonth() {
        // Мапа для преобразования названия месяца в номер месяца
        Map<String, Integer> monthMap = new HashMap<>();
        monthMap.put("January", 1);
        monthMap.put("February", 2);
        monthMap.put("March", 3);
        monthMap.put("April", 4);
        monthMap.put("May", 5);
        monthMap.put("June", 6);
        monthMap.put("July", 7);
        monthMap.put("August", 8);
        monthMap.put("September", 9);
        monthMap.put("October", 10);
        monthMap.put("November", 11);
        monthMap.put("December", 12);

        // Разделяем строку на части по запятой и пробелу
        String[] parts = date.split(", ");

        // Извлекаем месяц
        String monthName = parts[1].split(" ")[0];  // May

        // Возвращаем номер месяца из мапы
        return monthMap.getOrDefault(monthName, -1);  // -1, если месяц не найден
    }

    public String getAwayTeamImage() {
        return awayTeamImage;
    }

    public String getHomeTeamImage() {
        return homeTeamImage;
    }
}
