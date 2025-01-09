package com.basketballapp.datasource.network;

import com.basketballapp.domain.model.TeamStats;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class TeamScraper {

    public TeamStats fetchTeamDetails(String teamAbbreviation, int year) {
        TeamStats team = new TeamStats();

        try {
            String url = "https://www.basketball-reference.com/teams/" + teamAbbreviation + "/" + year + ".html";
            Document document = Jsoup.connect(url).get();

            // Парсим ключевые данные (например, Record, Coach, Arena)
            String record = document.selectFirst("p:contains(Record)").text();
            String coach = document.selectFirst("p:contains(Coach)").text().replace("Coach: ", "");
            String[] arena = document.selectFirst("p:contains(Arena)").text().replace("Arena: ", "").split("Attendance: ");
            String executive = document.selectFirst("p:contains(Executive)").text().replace("Executive: ", "");
            String[] pts = document.selectFirst("p:contains(PTS/G)").text().replace("PTS/G: ", "").split("Opp ");
            String[] data = document.selectFirst("p:contains(SRS)").text().replace("SRS: ", "").split("Pace: ");


            team.setRecord(record);
            team.setCoach(coach);
            team.setArena(arena[0]);
            team.setAttendance(arena[1]);
            team.setPointsPerGame(pts[0]);
            team.setOpponentPointsPerGame(pts[1]);
            team.setSrs(data[0]);
            team.setPace(data[1]);
            team.setExecutive(executive);

            // Парсим таблицу с расширенной статистикой
//            Element statsTable = document.selectFirst("table[id=team_and_opponent]");
//            if (statsTable != null) {
//                Elements rows = statsTable.select("tbody > tr");
//
//                for (Element row : rows) {
//                    String statCategory = row.select("th").text();
//                    String value = row.select("td").text();
//
//                    if (statCategory.equals("PTS/G")) {
//                        team.setPointsPerGame(value);
//                    } else if (statCategory.equals("Opp PTS/G")) {
//                        team.setOpponentPointsPerGame(value);
//                    } else if (statCategory.equals("SRS")) {
//                        team.setSrs(value);
//                    }
//                }
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return team;
    }

//    public static void main(String[] args) {
//        TeamScraper scraper = new TeamScraper();
//        String teamAbbreviation = "LAL"; // Аббревиатура команды
//        int year = 2024; // Год сезона
//
//        Team team = scraper.fetchTeamDetails(teamAbbreviation, year);
//        System.out.println(team);
//    }
}

